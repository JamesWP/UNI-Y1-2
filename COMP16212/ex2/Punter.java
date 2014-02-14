import java.awt.Color;

/*
 * A kind of person.
 */

public class Punter extends Person
{
  // Constructor is given the person's name.
  public Punter(String name)
  {
    super(name);
  }  // Punter


  // Returns the name of the type of Person.
  public String getPersonType()
  {
    return "Punter";
  } // getPersonType


  // Returns the Person's colour.
  public Color getColour()
  {
    return Color.green;
  } // getColour


  // Returns the Person's current saying.
  public String getCurrentSaying()
  {
    return "Make me happy: give me lots of money";
  } // getCurrentSaying


  // Returns whether or not the Person is happy.
  public boolean isHappy()
  {
    return false;
  } // isHappy

} // class Punter
