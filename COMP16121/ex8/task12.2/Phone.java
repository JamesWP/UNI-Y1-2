/**
 * phone represents a phone with a name and account and keeps track
 * of total seconds in calls
 * @author James Peach
 */
public class Phone
{
  private final String name;
  private final Account account;
  private int totalSeconds = 0;
  /**
   * the default constructor
   * @param  name the name of the phone
   * @param  account the account to link the phone to
   * @return the new phone
   */ 
  public Phone(String name,Account account)
  {
    this.name = name;
    this.account = account;
  }

  /**
   * tops up the phone with the given amount
   * @param pounds amount to top up by in pounds
   */
  public void topUp(int pounds)
  {
    account.topUp(pounds);
  }

  /**
   * attempts to make a call of the given length
   * @param  length the length to attempt
   * @return the actual length of the call
   */
  public int makeCall(int length)
  {
    // get the actual length of the call from the account
    int actualLength = account.callRequest(length);
    // keep track of the total
    totalSeconds += actualLength;
    return actualLength;
  }

  /**
   * returns a string reperesentation of the phone
   * @return the representation
   */
  public String toString()
  {
    return "Phone("+name+","+account+","+totalSeconds+")";
  }
}
