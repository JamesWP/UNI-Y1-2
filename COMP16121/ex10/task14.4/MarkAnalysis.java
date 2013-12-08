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
    input.useDelimiter("\\s");
    System.out.println("Please enter total number of marks:");
    int numberOfMarks = input.nextInt();
    if(numberOfMarks<1)
    {
      System.out.println("Please enter at least one mark");
      return; 
    }
    System.out.println();
    Student[] students = new Student[numberOfMarks];
    int total = 0;
    // gather marks from the user
    for (int markIndex = 0;markIndex<numberOfMarks;markIndex++)
    {
      Student newStudent = students[markIndex] = new Student(input,markIndex+1);
      total += newStudent.getMark();
    }
    // sort array
    sort(students);
    Student minimumStudentMark = students[0];
    Student maximumStudentMark = students[numberOfMarks-1];
    double meanMark = total / (double)numberOfMarks;

    System.out.printf("Mean mark: %-4.2f%n",meanMark);
    System.out.printf("Minimum:   %s%n",minimumStudentMark);
    System.out.printf("Maximum:   %s%n%n",maximumStudentMark);

    System.out.println("Person | Score |  Difference from the mean");
    int number = 1;
    for (Student student : students)
    {
      System.out.printf("%6s | %5s | %6.2f%n"
                        ,student.getName()
                        ,student.getMark()
                        ,student.getMark() - meanMark);
    }
  }

  /**
   * sorts the given array of Students by there mark
   * @param array [description]
   */
  public static void sort(Student[] array)
  {
    int len = array.length;
    for (int loopNo=0; loopNo<len+0; loopNo++)
      for (int elementIndex=loopNo;
          elementIndex>0
          && array[elementIndex-1].compareTo(array[elementIndex])>0;
          elementIndex--)
        swap(array, elementIndex, elementIndex-1);
  }

  /**
   * swaps two items in an array by there indexes
   * @param array  the array
   * @param index1 the first item to swap
   * @param index2 the second item to swap
   */
  private static void swap(Object[] array, int index1, int index2)
  {
    Object temp = array[index1];
    array[index1] = array[index2];
    array[index2] = temp;
  }
}
