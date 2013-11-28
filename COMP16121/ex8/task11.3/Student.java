/**
 * represents a student with a phone 
 * @author James Peach
 */
public class Student
{
  private final String name;
  private Phone phone;

  /**
   * creates a student with the given name
   * @param  name the name of the student
   * @return the student
   */
  public Student(String name)
  {
    this.name = name;
  }

  /**
   * the student purchases the given phone replaceing the current phone
   * @param phone the phone to purchase
   */
  public void purchasePhone(Phone phone)
  {
    this.phone = phone;
  }

  /**
   * tops up the students phone with the given amount if they have them
   * @param  pounds
   * @return returns the success of the topup true if there was a phone false otherwise
   */
  public boolean topUpPhone(int pounds)
  {
    if(phone!=null)
    {
      phone.topUp(pounds);
      return true;
    }else{
      return false;
    }
  }

  /**
   * makes a call on the students phone for the requested time
   * @param  length the time in seconds of the call
   * @return the actual lenght of the call
   */
  public int makeCall(int length)
  {
    if(phone!=null)
      return phone.makeCall(length);
    else
      return 0;
  }

  /**
   * returns a string representation of the object
   * @return the string
   */
  public String toString()
  {
    return "Student("+name+","+phone+")";
  }
}
