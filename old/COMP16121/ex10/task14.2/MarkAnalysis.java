import java.util.Scanner;
/**
 * gets a list of marks from the user and prints out a report based on
 * the input
 */
public class MarkAnalysis
{
  public static void main(String[] args)
  {
    Scanner input = new Scanner(System.in);
    System.out.println("Please enter total number of marks:");
    int numberOfMarks = input.nextInt();
    if(numberOfMarks<1)
    {
      System.out.println("Please enter at least one mark");
      return; 
    }
    System.out.println();
    int[] marks = new int[numberOfMarks];
    int total = 0;
    int minimumMark = 999;
    int maximumMark = 0;
    // gather marks from the user
    for (int markIndex = 0;markIndex<numberOfMarks;markIndex++)
    {
      System.out.println("Please enter mark #" + (markIndex+1) + ": ");
      int inputMark = input.nextInt();
      total += inputMark;
      minimumMark = inputMark<minimumMark?inputMark:minimumMark;
      maximumMark = inputMark>maximumMark?inputMark:maximumMark;
      marks[markIndex] = inputMark; 
      System.out.println();
    }
    double meanMark = total / (double)numberOfMarks;

    System.out.printf("Mean mark:    %-4.2f%n",meanMark);
    System.out.printf("Minimum mark: %-5d%n",minimumMark);
    System.out.printf("Maximum mark: %-5d%n%n",maximumMark);

    System.out.println("Person | Score |  Difference from the mean");
    int number = 1;
    for (int mark : marks)
    {
      System.out.printf("%6s | %5s | %6.2f%n" ,number++ ,mark
                        ,mark - meanMark);
    }
  }
}
