// Program to compute the volume of a fish tank.
// Dimensions are three command line arguments: width depth height.
public class FishTankVolume
{
  public static void main(String [] args)
  {
    try{
      int width = Integer.parseInt(args[0]);
      int depth = Integer.parseInt(args[1]);
      int height = Integer.parseInt(args[2]);
      int volume = width * depth * height;
      System.out.println("The volume of a tank with dimensions "
                         + "(" + width + "," + depth + "," + height + ") "
                         + "is " + volume);
    }
    catch (NullPointerException e)
    {
      System.out.println("You must provide an array of arguments");
    }
    catch (IndexOutOfBoundsException e)
    {
      System.out.println("You must provide at least three arguments"); 
    }
    catch (NumberFormatException e)
    {
      System.out.println("You must provide three integer arguments");
      System.out.println(e.getMessage());
    }
  } // main

} // class FishTankVolume
