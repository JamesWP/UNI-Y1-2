public class AgeHistoryPart1 {

    static int pDay = 23; //or whatever is today's date
    static int pMonth = 11; //or whatever is this month
    static int pYear = 2005; //or whatever is this year

    private static void printAgeHistory (int bDay, int bMonth, int bYear) {
	int year = bYear + 1;
	int age = 1;

	System.out.println("This person was born on "
			   + bDay + "/" + bMonth + "/" + bYear);
	while (year < pYear) {
	    System.out.println("This person was " + age
		+ " on " + bDay + "/" + bMonth + "/" + year);
	    year++;
	    age++;
	}
	if (bMonth == pMonth)
	    System.out.println("This person is "
		+ age + " today!");
	else
	    System.out.println("This person will be " + age
		+ " on " + bDay + "/" + bMonth + "/" + year);
    }

    public static void main(String [] args) {
	printAgeHistory(pDay, pMonth, 2000);
	System.out.println("Another person");
	printAgeHistory(13, 11, 2000);

    }

}
