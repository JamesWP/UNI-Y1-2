/**
 * TestMinMaxArray, takes a given list of integers from args and calculates
 */
public class TestMinMaxArray
{
  public static void main(String[] args)
  {
    /*
    this reads all the arguments from args into there reference type Integer
    to pass to the getMinMax method to calculate the min/max number

    if there are no arguments passed or they are invalid then a message is
    printed to the user explaining this
     */
    try
    {
      if (args.length < 1)
        throw new IllegalArgumentException("you must provide at lease one " + "argument");
      Integer[] testInput = new Integer[args.length];
      for (int index = 0; index < args.length; index++)
        testInput[index] = Integer.parseInt(args[index]);

      System.out.println(MinMaxArray.getMinMax(testInput));
    } catch (NumberFormatException nfException)
    {
      System.out.println("Invalid " + "number");
    } catch (IllegalArgumentException iaException)
    {
      System.out.println("Illegal argument supplied: " + iaException.getMessage());
    }
  }
}
