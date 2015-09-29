public class Rectangle
{
  private Point one;
  private Point two;
  public Rectangle(Point one, Point two)
  {
  	this.one = one;
  	this.two = two;
  }

  public Rectangle shift(double xshift,double yshift)
  {
  	return new Rectangle(one.shift(xshift,yshift),
  						two.shift(xshift,yshift));
  }

  public double area()
  {
  	return (one.getX()-two.getX())
  			*(one.getY()-two.getY());
  }

  public double perimeter()
  {
  	return 2 * (one.getX()-two.getX()+one.getY()-two.getY());
  }

  public String toString()
  {
    return "Rectangle(" + one + "," 
    	+ new Point(one.getX()+two.getX(),one.getY()) + "," + two + ","
    	+ new Point(one.getX(),one.getY()+two.getY())+")";
  }
}
