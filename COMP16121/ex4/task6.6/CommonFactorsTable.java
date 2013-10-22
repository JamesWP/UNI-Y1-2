public class CommonFactorsTable
{
  private static final int SIZE = 19;
  public static void main(String[] args)
  {
    //top line
    horizLine();
  
    System.out.print("|     |");
    for(int col = 2;col<=SIZE+1;col++)
      printLen(col);
    System.out.println(" |");

    //head and body seperator
    horizLine();

    for(int row = 2;row<=SIZE+1;row++)
    {
      System.out.print("| ");
      printLen(row);
      System.out.print(" |");
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
  *  prints the first argument to stdout as three characters space padded left
  *
  **/
  public static void printLen(int no)
  {
    if(no>=100)
      System.out.print(no);
    else if(no>=10)
      System.out.print(" "+no);
    else 
      System.out.print("  "+no);
    
  }
  /**
  *	prints a horizontal line
  *
  *//
  public static void horizLine()
  {
    System.out.print("|-----|");
    for(int cols=SIZE;cols>0;cols--)
        System.out.print("---");
    System.out.println("-|");
  }
}
