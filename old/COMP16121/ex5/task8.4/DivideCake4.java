public class DivideCake4
{
  public static void main(String[] args)
  {
    int number1 = Integer.parseInt(args[0]);
    int number2 = Integer.parseInt(args[1]);
    int number3 = Integer.parseInt(args[2]);
    int number4 = Integer.parseInt(args[3]);

    int divisor = gcd(gcd(gcd(number1,number2),number3),number4);

    System.out.println("The greatest common divisor of "
     + number1 + ", " + number2 + ", " + number3 + ", " + number4 + " is " + divisor);
  }

  /**
  * finds the greatest common devisor of the two arguments and
  * returns it to the caller
  **/
  public static int gcd(int one,int two)
  {
    while(one!=two)
    {
      if(one<two)
        two -= one;
      else
        one -= two;
    }
    return one;
  }
}
