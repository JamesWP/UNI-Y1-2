package robot;

import java.util.*;
import java.lang.Math.*;

public class RobotBeliefState{

  /**
     This is the class representing the robot's belief state. Some of
     the methods contain dummy code, however. Functioning versions of
     these methods can be found in the final subclass
     SolutionRobotBeliefState

  */


  protected double[][][] beliefMatrix;
  protected double[][] positionBeliefMatrix;
  protected double[] orientationBeliefMatrix;

  protected double maxPositionProbability;
  protected double maxOrientationProbability;

  protected double[][][] workMatrix; 

  protected String statusString;    

  public WorldMap map;             // Accurate map of the world

  protected ProbActionToggler probActionToggler; 


  // Set up constants

    public RobotBeliefState(WorldMap m, ProbActionToggler probActionToggler1){

    // Set map
    map= m;
    statusString= "Student's code"; // string telling the demonstrator code is ok
    initializeMatrices();
    probActionToggler= probActionToggler1;
  }

  public void initializeMatrices(){

    // Initialize matrices    
    beliefMatrix= 
      new double[RunRobot.SIZE][RunRobot.SIZE][RunRobot.SIZE];
    workMatrix= 
      new double[RunRobot.SIZE][RunRobot.SIZE][RunRobot.SIZE];
    positionBeliefMatrix=
      new double[RunRobot.SIZE][RunRobot.SIZE];
    orientationBeliefMatrix=
      new double[RunRobot.SIZE];

    /*
	        ************** Dummy code follows **************

    // The following code does not work. In its current state, it initializes
       the probability of any given pose to 1.

       The method should actually set the probability distribution
       stored in beliefMatrix[][][] to a flat distribution over poses
       corresponding to *unoccupied* squares.  (If (i,j) is an
       occupied square, then beliefMatrix[i][j][k] should be zero for all k.)

    */

    int totalBlocked = 0;
    for(int i= 0;i < RunRobot.SIZE; i++)            
      for(int j= 0;j < RunRobot.SIZE; j++)
	      totalBlocked += map.isOccupied(i,j)?1:0;

    double probability = 1.0/((RunRobot.SIZE*RunRobot.SIZE-totalBlocked)*RunRobot.SIZE);
    for(int i= 0;i < RunRobot.SIZE; i++)            
      for(int j= 0;j < RunRobot.SIZE; j++)
	      for(int k= 0;k < RunRobot.SIZE; k++)
	        beliefMatrix[i][j][k]=map.isOccupied(i,j)?0:probability;

    /* End of dummy code */

    updateMaxProbabilities(); // Update member variables used by public access 
                              // functions. (Do not change this line.)
  }


  public double getPoseProbability(Pose pose){
    /** 
	Return the probability that the robot is currently in Pose pose
    */
    return beliefMatrix[pose.x][pose.y][pose.theta];
  }

  public double getPositionProbability(int x, int y){
    /** 
	Return the probability that the robot is currently in position (x,y)
    */

    return positionBeliefMatrix[x][y];   
  }

  public double getOrientationProbability(int t){
    /** 
	Return the probability that the robot currently has orientation theta
    */
    return orientationBeliefMatrix[t];
  }

  protected void fixWorkMatrix(Observation o){

  }

  public void updateProbabilityOnObservation(Observation o){

    /** 
	Revise beliefMatrix by conditionalizing on Observation o
    */

    /*

	        ************** Dummy code follows **************

    // The following code does not work. In its current state, it sets
       the probability of any given pose to 1.


      The method should actually revise the probability distribution
      stored in beliefMatrix[][][] by conditionalizing on the observation o.

    */
    Pose curPose = new Pose();
    for(int x= 0;x < RunRobot.SIZE; x++)
      for(int y= 0;y < RunRobot.SIZE; y++)
        for(int t= 0;t < RunRobot.SIZE; t++)
        {
          curPose.x = x;
          curPose.y = y;
          curPose.theta = t;
	        beliefMatrix[x][y][t]= beliefMatrix[x][y][t] * map.getObservationProbability(curPose,o);
        }

    /* End of dummy code */

    updateMaxProbabilities();  // Update member variables used by public access 
                               // functions. (Do not change this line.)
  }


  public void updateProbabilityOnAction(Action a){
    for(int x= 0;x < RunRobot.SIZE; x++)
      for(int y= 0;y < RunRobot.SIZE; y++)
        for(int t= 0;t < RunRobot.SIZE; t++)
          workMatrix[x][y][t]= 0;
    
    Pose newP = new Pose();
    if(!probActionToggler.probActions())
    {
      for(int x= 0;x < RunRobot.SIZE; x++)
        for(int y= 0;y < RunRobot.SIZE; y++)
          for(int t= 0;t < RunRobot.SIZE; t++)
          {
            map.fillPoseOnAction(newP,x,y,t,a);
            workMatrix[newP.x][newP.y][newP.theta] += beliefMatrix[x][y][t];
          }
    }else{
      Action newAction = new Action();
      newAction.type = a.type;
      for(int x= 0;x < RunRobot.SIZE; x++)
        for(int y= 0;y < RunRobot.SIZE; y++)
          for(int t= 0;t < RunRobot.SIZE; t++)
            for(int u = 0;u<=20;u++)
            {
              newAction.parameter = u;
              map.fillPoseOnAction(newP,x,y,t,newAction);
              workMatrix[newP.x][newP.y][newP.theta] += 
                WorldMap.probabilify(a.parameter,newAction.parameter) 
                * beliefMatrix[x][y][t];
            }
    }

    for(int x= 0;x < RunRobot.SIZE; x++)
      for(int y= 0;y < RunRobot.SIZE; y++)
      	for(int t= 0;t < RunRobot.SIZE; t++)
	        beliefMatrix[x][y][t] = workMatrix[x][y][t];

    updateMaxProbabilities();  // Update member variables used by public access 
                               // functions. (Do not change this line.)
  }

  public double getMaxPositionProbability(){
    return maxPositionProbability;
  }

  public double getMaxOrientationProbability(){
    return maxOrientationProbability;
  }

  protected void updateMaxProbabilities(){

    double temp;
    maxPositionProbability= 0;
    for(int x= 0; x< RunRobot.SIZE; x++)
      for(int y= 0; y< RunRobot.SIZE; y++){
	temp= 0;
	for(int k= 0; k< RunRobot.SIZE; k++)
	  temp+=beliefMatrix[x][y][k];
	positionBeliefMatrix[x][y]= temp;
	if(positionBeliefMatrix[x][y]>maxPositionProbability)
	  maxPositionProbability= positionBeliefMatrix[x][y];
      }

    maxOrientationProbability= 0;
    for(int t= 0; t< RunRobot.SIZE; t++){
	temp= 0;
	for(int i= 0; i< RunRobot.SIZE; i++)
	  for(int j= 0; j< RunRobot.SIZE; j++)
	    temp+=beliefMatrix[i][j][t];
	orientationBeliefMatrix[t]= temp;
	if(orientationBeliefMatrix[t]>maxOrientationProbability)
	  maxOrientationProbability= orientationBeliefMatrix[t];
    }
  }

}
