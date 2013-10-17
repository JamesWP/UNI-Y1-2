public class YearsBeforeRetirement
{
  public static void main(String[] args)
  {
    int myAge = Integer.parseInt(args[0]);
    int retirementAge = 68;
    int yearsBeforeRetirement = retirementAge - myAge;
    System.out.println("My age now is " + myAge);
    System.out.println("I will retire at age " + retirementAge);
    System.out.println("Years left working is " + yearsBeforeRetirement);
  }
}