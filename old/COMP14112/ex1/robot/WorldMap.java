package robot;

import java.util.*;
import java.lang.Math.*;


public class WorldMap{

  /**
     The main class for defining a robot world

  */

  public LinkedList<WorldObject> objects;
  private boolean[][] occupancyMatrix;
  public double[][][] distanceMatrix;

  public WorldMap(){

    boolean temp; // Work variable

    objects= new LinkedList<WorldObject>();
    occupancyMatrix= new boolean[RunRobot.SIZE][RunRobot.SIZE];
    distanceMatrix= new double[RunRobot.SIZE][RunRobot.SIZE][RunRobot.SIZE];

    // Initialize occupancy matrix 
    for(int i= 0; i< RunRobot.SIZE; i++)
      for(int j= 0; j< RunRobot.SIZE; j++)
	occupancyMatrix[i][j]=  false;

    // Initialize distance matrix (this could be optimized, but ...)
    for(int i= 0; i< RunRobot.SIZE; i++)
      for(int j= 0; j< RunRobot.SIZE; j++)
	for(int k= 0; k< RunRobot.SIZE; k++){
	  if(getMinDistanceToBorder(i,j,k) < 0){
	    System.out.println("Problem: neg distance"+getMinDistanceToBorder(i,j,k));
	    System.out.println("i: "+i);
	    System.out.println("j: "+j);
	    System.out.println("k: "+k);

	  }

	  distanceMatrix[i][j][k]=  getMinDistanceToBorder(i,j,k);
	}
  }

  /**
     Returns true if the square (x,y) is occupied by an object in this WorldMap.
     An error is generated if x and y are not in the range between 0 and
     RunRobot.SIZE-1 (inclusive).
  */
  public boolean isOccupied(int x, int y){

    return occupancyMatrix[x][y];
  }
  
  /**
     Add a WorldObject o to this WorldMap.
  */
  public void addObject(WorldObject o){

    double d;

    objects.add(o);                               // add object to list
    for(int i= 0; i< RunRobot.SIZE; i++)          // update occupancy matrix
      for(int j= 0; j< RunRobot.SIZE; j++)
	occupancyMatrix[i][j]=  (occupancyMatrix[i][j]|| o.isIn(i,j));

    for(int i= 0; i< RunRobot.SIZE; i++)          // update distance matrix
      for(int j= 0; j< RunRobot.SIZE; j++)
	for(int k= 0; k< RunRobot.SIZE; k++){
	  d= o.getInterceptDistance(i,j,k);
	  if(d >= 0 && d < distanceMatrix[i][j][k])  
	    distanceMatrix[i][j][k]= d; 
	  // negative distance means that o not intercepted
	}
  }

  /**
     Given a robot in pose (x,y,t), return the distance to the
     border of the arena in the forward-facing direction.
  */
  public double getMinDistanceToBorder(int x, int y, int t){

    double xIntercept;
    double yIntercept;

    double xD= (double) x;
    double yD= (double) y;
    int size=  RunRobot.SIZE-1;
    double sizeD= (double) size;

    // integer-angle to angle conversion
    double psi= 2*Math.PI*((double) t)/((double) RunRobot.SIZE);

    // Trap out special (axis-oriented) cases
    if(t == 0)
      return sizeD-xD;
    if(t == RunRobot.SIZE/4)
      return yD;
    if(t == RunRobot.SIZE/2)
      return xD;
    if(t == 3*RunRobot.SIZE/4)
      return sizeD-yD;

    // Trap out special on-border cases
    if(x == 0 && t > RunRobot.SIZE/4 && t < 3*RunRobot.SIZE/4)
      return 0;  // on left border
    if(y == 0 && t > RunRobot.SIZE/4 && t < RunRobot.SIZE/2)
      return 0;  // on bottom border
    if((x == RunRobot.SIZE-1) && (t < RunRobot.SIZE/4 || t > 3*RunRobot.SIZE/4))
      return 0;  // on right border
    if((y == RunRobot.SIZE-1) && t > RunRobot.SIZE/2)
      return 0; // on top border

    // Compute intercept with right-hand limit
    if(t < RunRobot.SIZE/4 || t > 3*RunRobot.SIZE/4){
      yIntercept=  yD - (sizeD-xD)*Math.tan(psi);
      if(0 <= yIntercept && yIntercept <= sizeD)
	return (sizeD-xD)/Math.cos(psi);
    }
    // Compute intercept with botom limit
    if(t > 0 && t < RunRobot.SIZE/2){
      xIntercept= xD + yD/Math.tan(psi);
      if(0 <= xIntercept && xIntercept <= sizeD)
	return yD/Math.sin(psi);
    }
    // Compute intercept with left-hand limit
    if(t > RunRobot.SIZE/4 && t < 3*RunRobot.SIZE/4){
      yIntercept= yD + xD*Math.tan(psi);
      if(0 <= yIntercept && yIntercept <= sizeD)
	return -xD/Math.cos(psi);
    }
    // Compute intercept with top limit
    if(t > RunRobot.SIZE/2){
      xIntercept= xD - (sizeD - yD)/Math.tan(psi);
      if(0 <= xIntercept && xIntercept <= RunRobot.SIZE)  
	return -(sizeD-yD)/Math.sin(psi);
    }
    System.out.println("System Error: Consult Ian Pratt-Hartmann");
    return 0;
  }

  /**
     Given a robot in pose (x,y,t), return the distance to the
     nearest target (either the border of the arena or a WorldObject)
     in the forward-facing direction.
  */
  public double getMinDistanceToTarget(int x, int y, int t){

    /*
      Method: initialize result to distance to boundary; then cycle through
      all objects and update if distance to that object is less than current result.
    */

    double result;
    double d;                         // Work variable: distance to next object

    result= getMinDistanceToBorder(x,y,t);
    for(WorldObject o : objects){
      d= o.getInterceptDistance(x,y,t);
      if(d >= 0 && d < result)  // negative distance means that o is not intercepted
	result= d;
    }
    return result;
  }


  /** 
      Modify the Pose pose so that it is the robot pose that results from
      performing Action a assuming that robot currently has pose (x,y,theta)
      In case of a collision (when a.type is GO_FORWARD), the robot goes
      as far as it can and then stops.
  */
  public void fillPoseOnAction(Pose pose, int x, int y, int theta, Action a){

    double psi;
    double forwardStep;

    pose.x= x; // Instantiate pose to (x,y,theta)
    pose.y= y;
    pose.theta= theta;
    if(a.type == ActionType.TURN_RIGHT)
      pose.theta= (pose.theta+a.parameter)%RunRobot.SIZE;
    else if(a.type == ActionType.TURN_LEFT)
      pose.theta= (pose.theta+RunRobot.SIZE-a.parameter)%RunRobot.SIZE;
    else{ // (a.type == ActionType.GO_FORWARD)
      psi= 2*Math.PI*((double) theta)/((double) RunRobot.SIZE);
      forwardStep= Math.min((double) (a.parameter),distanceMatrix[x][y][theta]);
      pose.x+= (int) (forwardStep*Math.cos(psi)); // Get displacement
      pose.y-= (int) (forwardStep*Math.sin(psi)); // (DeltaY is negated)

      if(pose.x < 0 || pose.x >= RunRobot.SIZE ||pose.y < 0 || pose.y >= RunRobot.SIZE){
	System.out.println("pose.x: "+ pose.x);
	System.out.println("pose.y: "+ pose.y);
	System.out.println("x: "+ x);
	System.out.println("y: "+ y);
	System.out.println("theta: "+ theta);
	System.out.println("distanceMatrix[x][y][theta]"+distanceMatrix[x][y][theta]);
	System.out.println("forwardStep: "+ forwardStep);
      }
    }

  }


  public static int pollRandom(int mean, int sd){

    /* 
       Return an integer approximately normally distributed with mean
       mean and standard deviation sd.
    */
    double R= Math.random()/2;             // Random number between 0 and 0.5
    int error= 0;
    double cumulativeProb= normalDistribution(0,sd,0);
    // The probability of being somewhere between 0 and 1 in a normal dist
    // with mean 0 and standard deviation sd
    
    // Move error from 0 to (at most 3sd) until intergral under N(0,sd) from
    // 0 to error is at least R
    while((cumulativeProb < R)&&(error < 3*sd)){
      error++;
      // Increment error 
      cumulativeProb+= normalDistribution(0,sd,error);
      // The probability of being somewhere between 0 and error+1 in a normal dist
      // with mean 0 and standard deviation sd
    }
    // At this poinbt, we have 0 <= error <= 3sd 
    // Now decide randomly whether the error will be positive or negative
    if(Math.random() > 0.5)
      return mean+ error;
    else
      return mean- error;
  }


  /**
     Given a correct value correctValue return the approximate probability 
     that the actual value will be actualValue. Assume a Gaussian noise model 
     with standard deviation equal to one third of the correct value. Note 
     that the standard deviation is rounded down to next integer.

     In the case of a sensor reading, correctValue is what the reading should be,
     and actualValue what it is observed to be. In the case of an action parameter,
     correctValue is the intended parameter of an action, and actualValue the 
     imagined real parameter before collision with obstacles is taken into account. 

  */

    public static double probabilify(int correctValue, int actualValue){

    return normalDistribution(correctValue, correctValue/3, actualValue);
  }


  /**
     Return the approximate probability p(x < X < x+1) for a normally
     distributed random variable X with mean mean and standard devioation
     standardD.
  */
  public static double normalDistribution(int mean, int standardD, int x){

    /*  Return exp(-1/2((x+0.5-mean)/standardD)^2)/sqrt(2PI*standardD^2) */
    /*  (note that "x+0.5" is the midpoint between x and x+1) */

    // Deal with degenerate case of zero standard deviation
    if(standardD==0)
      if(x==mean)
	return 1;
      else
	return 0;

    double temp= (((double) (x - mean)) +0.5)/((double) standardD);
    return 
      Math.exp(-0.5*temp*temp)/(Math.sqrt(2*Math.PI)*((double) standardD)); 
  }



  /** 
      Get the probability that Observation observation will occur
      assuming that the robot is in Pose pose.
  */
  public double getObservationProbability(Pose pose, Observation observation){
    
    int t; // Angle in which beam is pointing

    // Get direction of beam correspoinding to observation.sensor
    if(observation.sensor == SensorType.FORWARD_SENSOR)
      t= pose.theta;
    else if(observation.sensor == SensorType.RIGHT_SENSOR)
      t= (pose.theta+RunRobot.SIZE/4)%RunRobot.SIZE; // normalize to range [0,2PI)
    else // (observation.sensor == SensorType.LEFT_SENSOR)
      t= (pose.theta+3*RunRobot.SIZE/4)%RunRobot.SIZE; // normalize to range [0,2PI)
    // Return probability of getting observed reading given the true reading
    return probabilify((int) (distanceMatrix[pose.x][pose.y][t]),observation.reading);
  }
}
