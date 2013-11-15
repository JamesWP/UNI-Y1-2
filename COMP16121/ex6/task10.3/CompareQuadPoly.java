/**
* compairs two polys and prints the comparison
**/
public class CompareQuadPoly
{
  public static void main(String[] args)
  {
    double ax2c = Double.parseDouble(args[0]);
    double axc = Double.parseDouble(args[1]);
    double aconst = Double.parseDouble(args[2]);
    QuadPoly apolly = new QuadPoly(ax2c,axc,aconst);

    double bx2c = Double.parseDouble(args[3]);
    double bxc = Double.parseDouble(args[4]);
    double bconst = Double.parseDouble(args[5]);
    QuadPoly bpolly = new QuadPoly(bx2c,bxc,bconst);


    System.out.println("The pollynomial:\t" + pollyToString(apolly));
    if(apolly.equals(bpolly))
    {
      System.out.println("is equal to \t\t" + pollyToString(apolly));
    } else if(apolly.lessThan(bpolly))
    {
      System.out.println("is less than:\t\t" + pollyToString(apolly));
    } else
    {
      System.out.println("is greater than:\t" + pollyToString(apolly));
    }
  }

  /**
  * returns a string representation of the poly object passed in
  **/
  public static String pollyToString(QuadPoly polly)
  {
    return polly.x2c + "x^2 + " + polly.xc + "x + " + polly.consta;
  }
}
