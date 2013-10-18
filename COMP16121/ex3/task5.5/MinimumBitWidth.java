public class MinimumBitWidth
{
  public static void main(String[] args)
  {
    int noVals = Integer.parseInt(args[0]);
    
    //System.out.println("Number of values: " + noVals);
    
    int noOfBits = 1;
    // this will store the number of values "noOfBits" bits
    // can hold
    int valsCanBeStored = 2;

    while(valsCanBeStored < noVals)
    {
      noOfBits ++;
      valsCanBeStored *= 2;
    }  

    System.out.println("You need " + noOfBits + " bits to"      
      + " store " + noVals + " values");
  }
}
