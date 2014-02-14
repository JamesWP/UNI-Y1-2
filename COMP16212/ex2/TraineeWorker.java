import java.awt.Color;

/*
 * A kind of worker.
 */

public class TraineeWorker extends Worker
{
  // The efficiency of the TraineeWorker.
  private final double efficiency;


  // Constructor is given the person's name
  // and the required efficiency.
  public TraineeWorker(String name, double requiredEfficiency)
  {
    super(name);
    efficiency = requiredEfficiency;
  }  // TraineeWorker


  // Returns the Person's name with the efficiency added in brackets.
  public String getPersonName()
  {
    return super.getPersonName() + " (" + efficiency + " efficiency)";
  } // getPersonName


  // Returns the name of the type of Person.
  public String getPersonType()
  {
    return "Trainee " + super.getPersonType();
  } // getPersonType


  // Returns a newly created Ball with the given number and colour.
  // The ball's number may be wrong depending on the efficiency.
  public Ball makeNewBall(int number, Color colour)
  {
    if (Math.random() >= efficiency)
      if (Math.random() < 0.5)
        number--;
      else
        number++;
    return new Ball(number, colour);
  } // newBall

} // class TraineeWorker
