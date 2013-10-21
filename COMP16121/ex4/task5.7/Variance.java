public class Variance
{
  public static void main(String[] args)
  {
    int sum = Integer.parseInt(args[0]);
    for(int argIndex = 1;argIndex<args.length;argIndex++)
      sum += Integer.parseInt(args[argIndex]);
    double mean = sum / (double) args.length;
  
    double variance = 0;
    for(int argIndex = 0;argIndex<args.length;argIndex++)
    {
      int value = Integer.parseInt(args[argIndex]);
      variance += (mean - value) * (mean - value);
    }
    variance /= args.length;
    System.out.println(variance + " " + mean);
  }
}
