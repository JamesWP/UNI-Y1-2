package robot;

/**  
     Pose (position and orientation) of robot
*/
public class Pose{

  /** x-coordinate of position: 0 <= x < RunRobot.SIZE */
  public int x;         
  /** 
      y-coordinate of position: 0 <= x < RunRobot.SIZE */
  public int y;         
  /** 
      orientation in units of 2PI/RunRobot.SIZE , measured clockwise from x-axis: 
      0 <= theta < Run.Robot.SIZE. 
  */
  public int theta;     

  public Pose(int x1, int y1, int theta1){

    x= x1;
    y= y1;
    theta= theta1;
  }

  public Pose(){
    /**
       Default pose created is bottom left-hand corner facing right
    */
    x= 0;
    y= 0;
    theta= 0;
  }

}
