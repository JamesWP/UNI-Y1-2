public class Polygon
{
  private Point one;
  private Point two;
  private Point three;
  private Point four;

  public Polygon(Point one, Point two, Point three, Point four)
  {
  	this.one = one;
    this.two = two;
    this.three = three;
    this.four = four;
  }

  public Polygon shift(double xshift,double yshift)
  {
  	return new Polygon(one.shift(xshift,yshift),
              two.shift(xshift,yshift),
              three.shift(xshift,yshift),
              four.shift(xshift,yshift));
  }

  public double area()
  {
  	return new Triangle(one,two,three).area()
            + new Triangle(one,three,four).area();
  }

  public double perimeter()
  {
  	return one.distanceTo(two)
            + two.distanceTo(three)
            + three.distanceTo(four)
            + four.distanceTo(one);
  }

  public String toString()
  {
    return "Polygon(" + one + "," + two + "," + three + "," + four + ")";
  }
}
