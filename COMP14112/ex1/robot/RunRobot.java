/* Last change to package:   21.1.07, 31.12.07, 2.1.08, 26.1.09, 9.2.09 */

// set classpath to directory above this. To compile goto directory
// containing this file and type javac RunRobot.java . To run, go to
// directory above this and type java robot.RunRobot .

package robot;


import java.util.*;
import java.lang.Math.*;


public class RunRobot{

    // Settings

    Boolean probActions=false;

  // Declare contants
  public static final int SIZE= 100;   // Basic dimensions of all matrices 

  public static void main(String[] args){


    System.out.println("Initializing world map.");

    // Make map
    WorldMap m= new WorldMap();

    // Make new objects and add them to map

    WorldObject o1= new WorldObject(30, 20, 35, 30); 
    WorldObject o2= new WorldObject(50, 5, 60, 30); 
    WorldObject o3= new WorldObject(10, 70, 40, 80); 
    WorldObject o4= new WorldObject(70, 75, 90, 95); 

    m.addObject(o1);
    m.addObject(o2);
    m.addObject(o3);
    m.addObject(o4);

    // Make ProbActionToggler
    ProbActionToggler probActionToggler= new ProbActionToggler();

    // Make robot's mind using the map
    System.out.println("Initializing robot's mind... .");
    // RobotBeliefState r= new SolutionRobotBeliefState(m,probActionToggler);
    RobotBeliefState r= new RobotBeliefState(m,probActionToggler);

    // Make new pose for robot
    Pose p= new Pose(50, 50, 0); 

    // Make scernario using Map map and Pose pose
    Scenario s= new Scenario(m, p, probActionToggler);

    Pose tempPose= new Pose();;    // Work variable

    // Open new display window showing beliefs about position
    RobotPositionGraph pg= new RobotPositionGraph(r,m);

    // Open new display window showing beliefs aout orientation
    RobotOrientationGraph og= new RobotOrientationGraph(r);

    // Open new display window showing scenario
    RobotPlan pl= new RobotPlan(s,r,pg,og,probActionToggler);

    System.out.println("Initialization complete.");
    pl.repaint();
    og.repaint();
    pg.repaint();

  }
}

