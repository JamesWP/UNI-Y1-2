public class Account
{
  public final String name;
  public int balance = 0;
  public Account(String name)
  {
    this.name = name;
  }

  public void topUp(int pounds)
  {
    // add to balance
    balance+=100*pounds;
  }

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

  public String toString()
  {
    return "Account("+name+","+balance+")";
  }
}
