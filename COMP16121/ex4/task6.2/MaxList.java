public class MaxList
{
  public static void main(String[] args)
  {
    double curentMax = -999999;
    int maxIndex = -1;

    for(int argIndex = 0; argIndex < args.length; argIndex ++)
    {
      double value = Double.parseDouble(args[argIndex]);
      if(value>curentMax)
      {
	curentMax = value;
	maxIndex = argIndex;
      }
    }

    System.out.println("Max   :" + curentMax);
    System.out.println("Index :" + maxIndex);
    
  }
}
