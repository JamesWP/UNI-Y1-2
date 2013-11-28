/**
* Account 
* represents an account for a mobile phone
* @author James Peach
*/
public class Account
{
  private final String name;
  private int balance = 0;
  /**
  * creates an account with the given name
  * @praram name the name of the account
  */
  public Account(String name)
  {
    this.name = name;
  }

  /**
  * called to top up the account
  * @param pounds the amount of pounds to top up the account by
  */
  public void topUp(int pounds)
  {
    // add to balance
    balance+=100*pounds;
  }

  /**
  * called to request a call to be made with the given length
  * @param lenght the length in seconds
  */
  public int callRequest(int length)
  {
    if(balance>=length)
    {
      balance-=length;
    }else{
      length = balance;
      balance = 0;
    }
    return length;
  }

  /**
  * returns a string representation of the Account
  */
  public String toString()
  {
    return "Account("+name+","+balance+")";
  }
}
