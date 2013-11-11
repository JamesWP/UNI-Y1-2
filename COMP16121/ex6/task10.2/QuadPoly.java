/**
* represents one quardaric equasion
**/
public class QuadPoly
{
  public double x2c;
  public double xc;
  public double consta;
  public QuadPoly(double x2c, double xc, double consta)
  {
    this.x2c = x2c;
    this.xc = xc;
    this.consta = consta;
  }

  /**
  * adds the two instances and creats another with the sum
  **/
  public static QuadPoly add(QuadPoly a, QuadPoly b)
  {
    return new QuadPoly(a.x2c+b.x2c,a.xc+b.xc,a.consta+b.consta);
  }

  /**
  * to string returns a string repersentaion of the QuadPoly
  **/
  public String toString()
  {
    return this.x2c + "x^2 + " + this.xc + "x + " + this.consta;
  }
}
