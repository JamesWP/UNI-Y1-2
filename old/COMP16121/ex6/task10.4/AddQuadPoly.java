/**
*   adds two pollys and prints the result
**/
public class AddQuadPoly
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

    // calculates the sum and prints it
    QuadPoly sum = QuadPoly.add(apolly,bpolly);
    System.out.println("Pollynomial : " + apolly);
    System.out.println("added to    : " + bpolly);
    System.out.println("results in  : " + sum);
  }
}
