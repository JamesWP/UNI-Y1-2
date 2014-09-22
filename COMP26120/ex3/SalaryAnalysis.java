import java.util.Scanner;
import java.io.File;

/* This program analyses integer salaries entered by the user.
   It outputs each salary together with its difference from the
   mean of the salaries. There must be at least one salary.
*/
public class SalaryAnalysis
{
    // Bubble-Sort a given array of int into ascending order.
    private static void sort(int[] anArray, int length)
    {
	// Each pass of the sort reduces unsortedLength by one.
	int unsortedLength = length;
	boolean changedOnThisPass;
	do
	{
	    changedOnThisPass = false;
	    for (int pairLeftIndex = 0;
		 pairLeftIndex < unsortedLength - 1; pairLeftIndex++)
		if (anArray[pairLeftIndex] > anArray[pairLeftIndex + 1])
		{
		    int thatWasAtPairLeftIndex = anArray[pairLeftIndex];
		    anArray[pairLeftIndex] = anArray[pairLeftIndex + 1];
		    anArray[pairLeftIndex + 1] = thatWasAtPairLeftIndex;
		    changedOnThisPass = true;
		} // if
	    unsortedLength--;
	} while (changedOnThisPass);
    } // sort

    public static void main(String[] args) throws Exception
    {
	String filename = args[0];

	// A Scanner for getting data from the user.
	Scanner salariesScanner = new Scanner(new File(filename));

	// The number of Salaries.
	int numberOfSalaries;
	// The salaries: only indices 0 to numberOfSalaries - 1 are used.
	int[] salaries;

	// Initial size of the array.
	int INITIAL_SIZE = 2;
	// When the array is full, we extend it by this factor.
	int RESIZE_FACTOR = 2;

	// Read salary data from the salariesScanner,
	// count them using numberOfSalaries,
	// and store in array, extending as required.

	salaries = new int[INITIAL_SIZE];
	numberOfSalaries = 0;
	while (salariesScanner.hasNextLine())
	{
	    // Obtain the next salary
	    String data = salariesScanner.nextLine();
	    int currentsalary = Integer.parseInt(data);

	    // Extend the array if it is too small.
	    if (numberOfSalaries == salaries.length)
	    {
		int[] biggerArray
		    = new int[salaries.length * RESIZE_FACTOR];
		for (int index = 0; index < salaries.length; index++)
		    biggerArray[index] = salaries[index];
		salaries = biggerArray;
	    } // if
	    // Finally store the salaray and update numberOfSalaries.
	    salaries[numberOfSalaries] = currentsalary;
	    numberOfSalaries++;
	} // while

	// Now compute the sum of the salaries.
	int sumOfSalaries = 0;
	for (int index = 0; index < numberOfSalaries; index++)
	    sumOfSalaries += salaries[index];

	// Compute the mean, which is a double, not an integer.
	double meanSalary = sumOfSalaries / (double)numberOfSalaries;

	// But we also want to round it to simplify the results.
	int meanSalaryRounded = (int) Math.round(meanSalary);

	// Sort the salaries into ascending order.
	sort(salaries, numberOfSalaries);

	// Produce the results.
	System.out.println();
	System.out.println("The mean salary is:\t" + meanSalary);
	System.out.println("which rounds to:\t" + meanSalaryRounded);
	System.out.println();
	for (int index = 0; index < numberOfSalaries; index++)
	{
	    int differenceFromMean = salaries[index] - meanSalaryRounded;
	    String comparisonToMean = differenceFromMean == 0.0
		? "zero difference from"
		: (differenceFromMean < 0
		   ? "less than" : "greater than");
	    System.out.println (String.format //System.out.printf
	        ("Person %2d earns %5d, which is %5d %s the mean",
		(index + 1), salaries[index],
		Math.abs(differenceFromMean), comparisonToMean));
	} // for
    } // main

} // class SalaryAnalysis
