import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by james on 01/04/2014.
 * <p/>
 * this program takes input from the stdin and prints the input in reverse
 * line by line
 */
public class ReverseLines
{
  public static void main(String[] args) throws Exception
  {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter output = new PrintWriter(System.out);

    // starts the recursion function to print the input in reverse to output
    printReverse(input, output);

    input.close();
    output.close();
  }

  /**
   * printReverse 
   * prints the input in reverse recursivley each run, the next line is printed
   * after all the other lines
   * if there are no more lines then the function returns without calling itself
   */ 
  public static void printReverse(BufferedReader input, PrintWriter output)
      throws Exception
  {
    String nextLine = input.readLine();
    if (nextLine == null) return;
    printReverse(input, output);
    output.println(nextLine);
  }
}
