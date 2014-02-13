package robot;

public class Observation
{
    public SensorType sensor;
    public int reading;
    
    public Observation(final SensorType sensor, final int reading) {
        super();
        this.sensor = sensor;
        this.reading = reading;
    }
}
