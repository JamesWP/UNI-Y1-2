package robot;

import java.util.*;
import java.lang.Math.*;

public class Scenario{

  /**
     The main class for defining a robot world

  */

  public WorldMap map;
  public Pose robotPose;
    private ProbActionToggler probActionToggler;

    public Scenario(WorldMap map1, Pose robotPose1, 
		    ProbActionToggler probActionToggler1){

      map= map1;
      robotPose= robotPose1;
      probActionToggler= probActionToggler1;
  }


  public void executeAction(Action intendedAction){

    /**
       Perform intended action intendedAction, updating the pose. The
       parameter of the action  is first randomized, with mean 
       intendedAction.parameter and s.d. 1/3 of that, and then truncated
       in case of collision. This gives a
       real action realAction, which is used to update the pose.
    */
      Pose newPose= new Pose();
      Action notionalAction= new Action();
      // Type of notionalAction is as intended. If probActions flag is false,
      // parameter of notionalAcion is also as intended. But if 
      // probActions flag is true, parameter is subject to normal
      // variation. There is no need to truncate in case of collision, because
      // this is already handled by WorldMap.fillPoseOnAction(). Thus, 
      // notionalAction is not what really occurs: it is simply what really 
      // occurs if nothing is in the way.
      notionalAction.type= intendedAction.type;
      if (probActionToggler.probActions())
	  notionalAction.parameter= 
	      WorldMap.pollRandom(intendedAction.parameter,
				  intendedAction.parameter/3);
      else
	  notionalAction.parameter= intendedAction.parameter;
      map.fillPoseOnAction(newPose,robotPose.x,robotPose.y,robotPose.theta,
			   notionalAction); // Calculate new pose (with truncation)
      robotPose= newPose;                //Actually update pose

      // Testing only
      // System.out.println("Intended parameter: "+ intendedAction.parameter);
      // System.out.println("Notional parameter: "+ notionalAction.parameter);

  }

  public Observation pollSensor(SensorType sType){

    /** 
	Get a reading from Sensor sType with correct distribution.
    */
    Observation result= new Observation(sType,0);  // 0 is a dummy for the constructor

    int t; // Angle in which beam is pointing

    // Get direction of beam correspoinding to observation.sensor
    if(sType == SensorType.FORWARD_SENSOR)
      t= robotPose.theta;
    else if(sType == SensorType.RIGHT_SENSOR)
      t= (robotPose.theta+RunRobot.SIZE/4)%RunRobot.SIZE;   // normalize to [0,2PI)
    else // (sType == SensorType.LEFT_SENSOR)
      t= (robotPose.theta+3*RunRobot.SIZE/4)%RunRobot.SIZE; // normalize to [0,2PI)
    int trueReading= (int) (map.distanceMatrix[robotPose.x][robotPose.y][t]);
    result.reading= WorldMap.pollRandom(trueReading,trueReading/3);
    return result;
  }


}
