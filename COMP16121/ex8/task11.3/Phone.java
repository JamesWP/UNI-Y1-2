public class Phone
{
  private final String name;
  private final Account account;
  private int totalSeconds = 0;
  public Phone(String name,Account account)
  {
    this.name = name;
    this.account = account;
  }

  public void topUp(int pounds)
  {
    account.topUp(pounds);
  }

  public int makeCall(int length)
  {
    // get the actual length of the call from the account
    int actualLength = account.callRequest(length);
    // keep track of the total
    totalSeconds += actualLength;
    return actualLength;
  }

  public String toString()
  {
    return "Phone("+name+","+account+","+totalSeconds+")";
  }
}
