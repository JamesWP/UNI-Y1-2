package robot;

public class Scenario
{
    public WorldMap map;
    public Pose robotPose;
    private ProbActionToggler probActionToggler;
    
    public Scenario(final WorldMap map, final Pose robotPose, final ProbActionToggler probActionToggler) {
        super();
        this.map = map;
        this.robotPose = robotPose;
        this.probActionToggler = probActionToggler;
    }
    
    public void executeAction(final Action action) {
        final Pose robotPose = new Pose();
        final Action action2 = new Action();
        action2.type = action.type;
        if (this.probActionToggler.probActions()) {
            action2.parameter = WorldMap.pollRandom(action.parameter, action.parameter / 3);
        }
        else {
            action2.parameter = action.parameter;
        }
        this.map.fillPoseOnAction(robotPose, this.robotPose.x, this.robotPose.y, this.robotPose.theta, action2);
        this.robotPose = robotPose;
    }
    
    public Observation pollSensor(final SensorType sensorType) {
        final Observation observation = new Observation(sensorType, 0);
        int theta;
        if (sensorType == SensorType.FORWARD_SENSOR) {
            theta = this.robotPose.theta;
        }
        else if (sensorType == SensorType.RIGHT_SENSOR) {
            theta = (this.robotPose.theta + 25) % 100;
        }
        else {
            theta = (this.robotPose.theta + 75) % 100;
        }
        final int n = (int)this.map.distanceMatrix[this.robotPose.x][this.robotPose.y][theta];
        observation.reading = WorldMap.pollRandom(n, n / 3);
        return observation;
    }
}
