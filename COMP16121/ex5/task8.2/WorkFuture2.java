public class WorkFuture2
{
  public static final int retirementAge = 68;
  public static void main(String[] args)
  {
	int presentYear = Integer.parseInt(args[0]);
	int p1Birth = Integer.parseInt(args[1]);
	int p2Birth = Integer.parseInt(args[2]);
	
	System.out.println("Person 1 has " 
	 + (p1Birth + retirementAge - presentYear) + " years left to work");	

	for (int curentYear = presentYear+1; curentYear < p1Birth + retirementAge; curentYear ++)
		System.out.println("In "+ curentYear + " Person 1 has " 
		 + (p1Birth + retirementAge - curentYear) + " years left to work");

	System.out.println("Person 1 will retire on " 
     + (p1Birth + retirementAge));
	
	
	System.out.println("Person 2 has " 
	 + (p2Birth + retirementAge - presentYear) + " years left to work");	

	for (int curentYear = presentYear+1; curentYear < p2Birth + retirementAge; curentYear ++)
		System.out.println("In "+ curentYear + " Person 2 has " 
		 + (p2Birth + retirementAge - curentYear) + " years left to work");

	System.out.println("Person 2 will retire on " 
     + (p2Birth + retirementAge));

	 
  }
}
