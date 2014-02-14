import java.awt.Color;

/*
 * A kind of person.
 */

public class Teenager extends MoodyPerson
{
  // Constructor is given the person's name.
  public Teenager(String name)
  {
    super(name, false);
  } // Teenager


  // Returns the name of the type of Person.
  public String getPersonType()
  {
    return "Teenager";
  } // getPersonType


  // Returns the Person's colour.
  public Color getColour()
  {
    return Color.cyan;
  } // getColour


  // Returns the Person's current saying.
  public String getCurrentSaying()
  {
    if (isHappy())
      return "Isn't life wonderful?";
    else
      return "It's not fair!";
  } // getCurrentSaying

} // class Teenager
