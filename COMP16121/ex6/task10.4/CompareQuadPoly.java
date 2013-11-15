/**
* compairs two polys and prints the comparison
**/
public class CompareQuadPoly
{
  public static void main(String[] args)
  {
    // construct the first polly from args 0-2
    double ax2c = Double.parseDouble(args[0]);
    double axc = Double.parseDouble(args[1]);
    double aconst = Double.parseDouble(args[2]);
    QuadPoly apolly = new QuadPoly(ax2c,axc,aconst);

    // construc the other 3-5
    double bx2c = Double.parseDouble(args[3]);
    double bxc = Double.parseDouble(args[4]);
    double bconst = Double.parseDouble(args[5]);
    QuadPoly bpolly = new QuadPoly(bx2c,bxc,bconst);

    // print the first
    System.out.println("The pollynomial:\t" + apolly);
    if(apolly.equals(bpolly))
      // print equal
      System.out.println("is equal to \t\t" + apolly);
    else if(apolly.lessThan(bpolly))
      // print less than
      System.out.println("is less than:\t\t" + apolly);
    else
      // print greater than
      System.out.println("is greater than:\t" + apolly);
  }
}
