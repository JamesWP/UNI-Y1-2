public class PrintHoledRectangle
{
  public static void main(String[] args)
  {
    int width = Integer.parseInt(args[0])/2*2+1;
    int height = Integer.parseInt(args[1])/2*2+1;
  
    
    System.out.println("Width:" + width + " Height:" + height );

    for (int y = 0;y<height;y++)
    {
      for(int x = 0;x<width;x++)
      {
	// this determines if the x and y are the center and if
	// they are in the middle then "   " otherwise "[_]"
	System.out.print(x==width/2&&y==height/2?"   ":"[_]");
      }
      System.out.println();
    }
    System.out.println();
  }
}
