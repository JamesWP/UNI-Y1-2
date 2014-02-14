import java.awt.Color;

/*
 * A kind of moody person.
 */

public class Worker extends MoodyPerson
{
  // Constructor is given the person's name.
  public Worker(String name)
  {
    super(name);
  }  // Worker


  // Returns the name of the type of Person.
  public String getPersonType()
  {
    return "Worker";
  } // getPersonType


  // Returns the Person's colour.
  public Color getColour()
  {
    return Color.lightGray;
  } // getColour


  // Returns the Person's current saying.
  public String getCurrentSaying()
  {
    if (isHappy())
      return "Time for tea, I think";
    else
      return "Puff, pant, puff, pant";
  } // getCurrentSaying


  // Returns a newly created Ball with the given number and colour.
  public Ball makeNewBall(int number, Color colour)
  {
    return new Ball(number, colour);
  } // makeNewBall


  // Makes this Worker fill the given machine of the given Game.
  // The Balls are created as they are inserted into the Machine.
  public void fillMachine(Game game)
  {
    // Colours of balls are evenly spread between these colours,
    // in ascending order.
    Color [] colourGroupColours
      = new Color [] {Color.red, Color.orange, Color.yellow,
                      Color.green, Color.blue, Color.pink,
                      Color.magenta};
    // This happiness change will show up when the GUI is added.
    setHappy(false);
    speak();
    Color ballColour;
    int noOfBalls = game.getMachineSize();
    for (int count = 1; count <= noOfBalls; count++)
    {
      // The colour group is a number from 0
      // to the number of colour groups - 1.
      // For the nth ball, we take the fraction
      // (n - 1) divided by the number of balls
      // and multiply that by the number of groups.
      int colourGroup = (int) ((count - 1.0) / (double) noOfBalls
                               * (double) colourGroupColours.length);
      ballColour = colourGroupColours[colourGroup];
      game.machineAddBall(makeNewBall(count, ballColour));
    } // for
    setHappy(true);
    speak();
  } // fillMachine

} // class Worker
