/**
* represents one quardaric equasion
**/
public class QuadPoly
{
  public double x2c;
  public double xc;
  public double consta;

  /**
  * creates a new instance of the object with the given values
  **/
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
  * compairs two QuadPoly's  returns true if equal
  **/
  public boolean equals(QuadPoly a)
  {
    return x2c == a.x2c && xc == a.xc && consta == a.consta;
  } 

  /**
  * returns true if less than the given poly
  **/
  public boolean lessThan(QuadPoly a)
  {
    return x2c < a.x2c 
      || (x2c == a.x2c && xc < a.xc)
      || (x2c == a.x2c && xc == a.xc && consta < a.consta);
  }

  /**
  * to string returns a string repersentaion of the QuadPoly
  **/
  public String toString()
  {
    return this.x2c + "x^2 + " + this.xc + "x + " + this.consta;
  }
}
