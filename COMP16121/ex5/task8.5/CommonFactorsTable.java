public class CommonFactorsTable
{
  private static final int SIZE = 19;
  public static void main(String[] args)
  {
    //top line
    horizLine();
  
    // Prints column headings
    System.out.print("|     |");
    for(int col = 2;col<=SIZE+1;col++)
      System.out.printf("%3d",col);
    System.out.println(" |");

    //head and body seperator
    horizLine();
    
    // prints each row
    for(int row = 2;row<=SIZE+1;row++)
    {
      // prints row heading
      System.out.print("| ");
      System.out.printf("%3d",row);
      System.out.print(" |");

      // prints intersecting lines
      for(int col = 2;col<=SIZE+1;col++)
          if(gcd(row,col)>1) 
            System.out.print("--#");
          else
            System.out.print("--|");
      System.out.println(" |");
    }

    //bottom line
    horizLine();
  }
  /**
  * finds the greatest common denominator of the two arguments and
  * returns it to the caller
  **/
  public static int gcd(int one,int two)
  {
    while(one!=two)
    {
      if(one<two)
        two -= one;
      else
        one -= two;
    }
    return one;
  }
  /**
  *	prints a horizontal line
  *
  */
  public static void horizLine()
  {
    System.out.print("|-----|");
    for(int cols=SIZE;cols>0;cols--)
        System.out.print("---");
    System.out.println("-|");
  }
}
