public class DivideCake3
{
  public static void main(String[] args)
  {
    int numberOne   = Integer.parseInt(args[0]);
    int numberTwo   = Integer.parseInt(args[1]);
    int numberThree = Integer.parseInt(args[2]);
    int numberOneTwoGCD = gcd(numberOne,numberTwo);
    int theGcd = gcd(numberOneTwoGCD,numberThree);
    
    System.out.println("The greatest common divisor of " + numberOne + 
    		" and " + numberTwo + " and " + numberThree + " is " + theGcd);
  }
   /**
  * finds the greatest common denominator of the two arguments and
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
