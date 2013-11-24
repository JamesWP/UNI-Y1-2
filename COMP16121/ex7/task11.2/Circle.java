public class Circle
{
  private Point centre;
  private double radius;
  public Circle(Point centre,double radius)
  {
  	this.centre = centre;
  	this.radius = radius;
  }

  public Circle shift(double xshift, double yshift)
  {
  	return new Circle(centre.shift(xshift,yshift),radius);
  }

  public double area()
  {
  	return Math.PI * radius * radius;
  }

  public double perimeter()
  {
  	return 2 * Math.PI * radius;
  }

  public String toString()
  {
  	return "Circle(" + centre + ","+radius+")";
  }
}
