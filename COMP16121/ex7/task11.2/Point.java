public class Point
{
  private double x;
  private double y;
  public Point (double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  public double getX()
  {
    return x;
  }
  
  public double getY()
  {
    return y;
  }

  public Point shift(double xshift,double yshift)
  {
    return new Point(x+xshift,y+yshift);
  }

  public double distanceTo(Point other)
  {
    return Math.sqrt((x-other.x)*(x-other.x)
                     +(y-other.y)*(y-other.y));
  }

  public String toString()
  {
    return "("+x+","+y+")";
  }
}
