public class WorkFuture4
{
  public static final int retirementAge = 68;
  public static void main(String[] args)
  {
    int presentYear = Integer.parseInt(args[0]);
    int person1Year = Integer.parseInt(args[1]);
    int person2Year = Integer.parseInt(args[2]);
    int person3Year = Integer.parseInt(args[3]);
    int person4Year = Integer.parseInt(args[4]);

    printAgeHistory(1,person1Year,presentYear);
    printAgeHistory(2,person2Year,presentYear);
    printAgeHistory(3,person3Year,presentYear);
    printAgeHistory(4,person4Year,presentYear);
  }

  /*prints the age history for one person*/
  public static void printAgeHistory(int personNumber, int personBirthYear, int presentYear){
    System.out.println("Person " +personNumber + " has " 
	   + (personBirthYear + retirementAge - presentYear) + " years left to work");	

	  for (int curentYear = presentYear+1; curentYear < personBirthYear 
      + retirementAge; curentYear ++)
		  System.out.println("In "+ curentYear + " Person " +personNumber + " has " 
		   + (personBirthYear + retirementAge - curentYear) + " years left to work");

	  System.out.println("Person " +personNumber + " will retire on " 
       + (personBirthYear + retirementAge));
  }
}
