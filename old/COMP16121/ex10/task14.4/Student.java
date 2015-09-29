import java.util.Scanner;
/**
 * represents a student with an associated mark
 */
public class Student implements Comparable<Student>
{
  private final String name;
  private final int mark;

  /**
   * constructs a Student from input from the console
   * @param  input  the scanner to read
   * @param  markNo the number of the student (used in prompt)
   * @return        the new student
   */
  public Student(Scanner input,int markNo)
  {
    System.out.println("Please enter name: ");
    input.nextLine();
    name = input.nextLine();
    System.out.println("Please enter mark #" + markNo + ": ");
    mark = input.nextInt();
    System.out.println();
  }
  private Student(String name, int mark)
  {
    this.name = name;
    this.mark = mark;
  }

  /**
   * returns the mark
   * @return mark
   */
  public int getMark()
  {
    return mark;
  }

  /**
   * returns the name
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * compairs two students used in sort
   * @param  other the other student to compare
   * @return       negative: less than, positive: greater than, 0: equal
   */
  public int compareTo( Student other)
  {
    return Integer.compare(mark,other.mark);
  }

  /**
   * returns a string representation of the student
   * @return the string
   */
  public String toString()
  {
    return name + " mark: " + mark;
  }
}