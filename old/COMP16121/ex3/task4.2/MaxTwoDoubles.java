public class MaxTwoDoubles
{
  public static void main(String[] args)
  {
    double number1 = Double.parseDouble(args[0]);
    double number2 = Double.parseDouble(args[1]);

    System.out.println("Number 1 :" + number1);
    System.out.println("Number 2 :" + number2);
    if(number1>number2)
    {
      System.out.println("Number 1 is the largest");
    } else {
      System.out.println("Number 2 is the largest");
    }
  }
}
