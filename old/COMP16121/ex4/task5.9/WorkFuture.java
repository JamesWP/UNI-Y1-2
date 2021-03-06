public class WorkFuture
{
  public static void main(String[] args)
  {
    int retirementAge = 68;
    int presentYear = Integer.parseInt(args[0]);
    int birthYear = Integer.parseInt(args[1]);
  
    // print yers left to work before retirement
    System.out.println("You have " + (birthYear + retirementAge - presentYear)
      + " years left to work");

    // loop from next year; untill retirement age; increasing year by one each loop
    for (int nowYear = presentYear+1;nowYear < birthYear + retirementAge; nowYear ++)
      System.out.println("In " + nowYear + " you will have "
	+ (birthYear + retirementAge - nowYear) + " years left to work");
    
    // print final retirement age
    System.out.println("You will retire in " + (birthYear + retirementAge));
  }
}
