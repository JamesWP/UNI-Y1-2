package robot;

public class RunRobot
{
    Boolean probActions;
    public static final int SIZE = 100;
    
    public RunRobot() {
        super();
        this.probActions = false;
    }
    
    public static void main(final String[] array) {
        System.out.println("Initializing world map.");
        final WorldMap worldMap = new WorldMap();
        final WorldObject worldObject = new WorldObject(30, 20, 35, 30);
        final WorldObject worldObject2 = new WorldObject(50, 5, 60, 30);
        final WorldObject worldObject3 = new WorldObject(10, 70, 40, 80);
        final WorldObject worldObject4 = new WorldObject(70, 75, 90, 95);
        worldMap.addObject(worldObject);
        worldMap.addObject(worldObject2);
        worldMap.addObject(worldObject3);
        worldMap.addObject(worldObject4);
        final ProbActionToggler probActionToggler = new ProbActionToggler();
        System.out.println("Initializing robot's mind... .");
        final RobotBeliefState robotBeliefState = new RobotBeliefState(worldMap, probActionToggler);
        final Scenario scenario = new Scenario(worldMap, new Pose(50, 50, 0), probActionToggler);
        final Pose pose = new Pose();
        final RobotPositionGraph robotPositionGraph = new RobotPositionGraph(robotBeliefState, worldMap);
        final RobotOrientationGraph robotOrientationGraph = new RobotOrientationGraph(robotBeliefState);
        final RobotPlan robotPlan = new RobotPlan(scenario, robotBeliefState, robotPositionGraph, robotOrientationGraph, probActionToggler);
        System.out.println("Initialization complete.");
        robotPlan.repaint();
        robotOrientationGraph.repaint();
        robotPositionGraph.repaint();
    }
}
