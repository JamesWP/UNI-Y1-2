// prints a calendar with a highlited cell
public class CalendarHighlight
{
  public static void main(String[] args)
  {
    // get params
    int startIndex = Integer.parseInt(args[0])-1; // change 0 index
    int endDate = Integer.parseInt(args[1]);
    int selectedDate = Integer.parseInt(args[2]);
    printLine();
    // print col headings
    System.out.println("| Su  Mo  Tu  We  Th  Fr  Sa  |");
    // print each row in table
    for(int row = 0;row < 6;row ++)
    {
      System.out.print("|");
      // print each col
      for(int col = 1;col <=7 ;col ++)
      {
        //calculate date for "box"
        int date = row*7+col-startIndex;
        // if date is within 0 - end then print it
        if (date>0 && date <= endDate)
          // if selected print > and < otherwise just print normal
          System.out.printf(selectedDate==date?">%02d<":" %02d ",date);
        else
          // otherwise print space
          System.out.print("    ");
      }
      System.out.println(" |");
    }
  }
  /**
  * prints one seperator line
  **/
  public static void printLine()
  {
    System.out.println("-------------------------------");
  }
}
