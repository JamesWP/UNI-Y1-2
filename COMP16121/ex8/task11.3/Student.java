public class Student
{
  private final String name;
  private Phone phone;
  public Student(String name)
  {
    this.name = name;
  }

  public void purchasePhone(Phone phone)
  {
    this.phone = phone;
  }

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

  public int makeCall(int length)
  {
    if(phone!=null)
      return phone.makeCall(length);
    else
      return 0;
  }

  public String toString()
  {
    return "Student("+name+","+phone+")";
  }
}
