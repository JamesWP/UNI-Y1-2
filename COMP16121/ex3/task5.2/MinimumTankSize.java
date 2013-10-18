public class MinimumTankSize
{
  public static void main(String[] args)
  {
    double requiredVolume = Double.parseDouble(args[0]);    

    double sideLength = 0.0;

    while(sideLength * sideLength * sideLength < requiredVolume)
      sideLength += 0.1;

    System.out.println("For a tank to hold " + requiredVolume 
      + " litres, you need a tank side length " + sideLength);
  }
}
