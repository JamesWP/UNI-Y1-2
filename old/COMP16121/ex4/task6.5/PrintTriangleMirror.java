public class PrintTriangleMirror
{
  public static void main(String[] args)
  {
    int size = Integer.parseInt(args[0]);
    
    // from top to bottom...
    for (int y = 0;y<size;y++)
    {
      // from left to right
      for(int x = size;x>0;x--)
      { 
        // determine if to draw cell
        System.out.print(x+y<=size?"[_]":"   ");
      }
      System.out.println();
    }
  }
}
