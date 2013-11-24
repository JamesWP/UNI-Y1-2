public class Triangle
{
  private Point a;
  private Point b;
  private Point c;
  public Triangle (Point a, Point b, Point c)
  {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public Triangle shift(double xshift,double yshift)
  {
    return new Triangle(a.shift(xshift,yshift),
                        b.shift(xshift,yshift),
                        c.shift(xshift,yshift));
  }

  public double area()
  {
    return Math.abs((a.getX()*(b.getY()-b.getY())
            + b.getX()*(c.getY()-a.getY())
            + c.getX()*(a.getY()-b.getY()))*0.5f);
  }
  public double perimeter()
  {
    return a.distanceTo(b)
           +b.distanceTo(c)
           +c.distanceTo(a);
  }

  public String toString()
  {
    return "Triangle(" + a + ","+b+","+c+")";
  }
}
