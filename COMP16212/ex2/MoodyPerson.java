/*
 * A subclass of person, those who can change their happiness state.
 * It is abstract -- not intended for instantiation.
 */

public abstract class MoodyPerson extends Person
{
  // The state of the Person's happiness.
  private boolean isHappyNow;


  // Constructor is given the person's name and initial happiness.
  public MoodyPerson(String name, boolean initialHappiness)
  {
    super(name);
    isHappyNow = initialHappiness;
  }  // MoodyPerson


  // Alternative constructor is given the person's name
  // and initial happiness is assumed to be true.
  public MoodyPerson(String name)
  {
    this(name, true);
  }  // MoodyPerson


  // Returns the state of happiness of the person.
  public boolean isHappy()
  {
    return isHappyNow;
  } // isHappy


  // Sets the happiness of the person to the given state.
  public void setHappy(boolean newHappiness)
  {
    isHappyNow = newHappiness;
    getImage().update();
  } // setHappy
  
} // class MoodyPerson
