public class SinTable
{
  public static void main(String[] args)
  {
    int start = Integer.parseInt(args[0]);
    int increment = Integer.parseInt(args[1]);
    int end = Integer.parseInt(args[2]);

    System.out.println("------------------------------------------");
    System.out.println("| Sin table from " + start + " to " + end + " in "
	+ "steps of " + increment);
    System.out.println("------------------------------------------");
    for(int deg = start;deg<=end;deg += increment)
      System.out.println("| sin("+deg+") = " + Math.sin(Math.toRadians(deg)));
    System.out.println("------------------------------------------");    
  }
}
