package robot;

  /**
     Observation that robot can make. E.g. forward sensor reads target at 3 units

  */

public class Observation{
  /**
     One of the constants LEFT_SENSOR, FORWARD_SENSOR or RIGHT_SENSOR
  */
  public SensorType sensor;
  /**
     Number of units to target returned by the sensor given in this.sensor
  */ 
  public int reading;

  public Observation(SensorType sensor1, int reading1){

    sensor= sensor1;
    reading= reading1;
  }

}
