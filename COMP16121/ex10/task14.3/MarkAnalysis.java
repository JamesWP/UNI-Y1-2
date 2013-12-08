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
    // gather marks from the user
    for (int markIndex = 0;markIndex<numberOfMarks;markIndex++)
    {
      System.out.println("Please enter mark #" + (markIndex+1) + ": ");
      int inputMark = input.nextInt();
      total += inputMark;
      marks[markIndex] = inputMark; 
      System.out.println();
    }
    // sort array
    sort(marks);
    int minimumMark = marks[0];
    int maximumMark = marks[numberOfMarks-1];
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

  /**
  * sorts the given array of Students by there mark
  * @param array [description]
  */
  public static void sort(int[] array)
  {
    int len = array.length;
    for (int loopNo=0; loopNo<len+0; loopNo++)
      for (int elementIndex=loopNo; elementIndex>0 
           && array[elementIndex-1]>array[elementIndex]; elementIndex--)
        swap(array, elementIndex, elementIndex-1);
  }

  /**
   * swaps two items in an array by there indexes
   * @param array  the array
   * @param index1 the first item to swap
   * @param index2 the second item to swap
   */
  private static void swap(int[] array, int index1, int index2)
  {
    int temp = array[index1];
    array[index1] = array[index2];
    array[index2] = temp;
  }
}
