/**
* outputs a truth table for three expressions p1-3
**/
public class TruthTable34
{
  public static void main(String[] args)
  {
    printLine();

    System.out.println("|   a   |   b   |   c   |   d   |  p1   |  p2   |  p3   |");

    printLine();

    // loop through combinations of the four inputs
    boolean a = true,b = true, c = true,d=true;
    for(int counta = 0;counta<2;counta++,a=!a)
      for(int countb = 0;countb<2;countb++,b=!b)
        for(int countc = 0;countc<2;countc++,c=!c)
          for(int countd = 0;countd<2;countd++,d=!d)
            System.out.println((a?"| true  ":"| false ") 
                              +(b?"| true  ":"| false ") 
                              +(c?"| true  ":"| false ")
                              +(d?"| true  ":"| false ")
                              +(p1(a,b,c,d)?"| true  ":"| false ")
                              +(p2(a,b,c,d)?"| true  ":"| false ")
                              +(p3(a,b,c,d)?"| true  |":"| false |"));
    
    printLine();
  }

  //
  // follows are the three expressions with the 4 variables passed as arguments
  //

  public static boolean p1(boolean a, boolean b, boolean c, boolean d)
  {
    return (((a||b)&&c)||((b||c)&&d))&&(a||d);
  }

  public static boolean p2(boolean a, boolean b, boolean c, boolean d)
  {
    return a && c || b && d || c && d;
  }

  public static boolean p3(boolean a, boolean b, boolean c, boolean d)
  {
    return (b||c)&&(c||d)&& (a||d);
  }

  /**
  * outputs one line for the table
  **/
  public static void printLine()
  {
    System.out.println("---------------------------------------------------"
                      +"------");
  }
}
