public class AddQuadPoly
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

    QuadPoly sum = QuadPoly.add(apolly,bpolly);
    System.out.println("Pollynomial : " + apolly);
    System.out.println("added to    : " + bpolly);
    System.out.println("results in  : " + sum);
  }
}
