/** a class containing three objects collected in a triple */
public class Triple<E1,E2,E3>
{
  private E1 first;
  private E2 second;
  private E3 third;
  /** constructor for creating a triple of the given objects */
  public Triple(E1 first,E2 second,E3 third)
  {
    this.first = first;
    this.second = second;
    this.third = third;
  }
  /** the following methods return the given elements in the list*/
  public E1 getFirst()
  { return first;  }
  public E2 getSecond()
  { return second;  }
  public E3 getThird()
  { return third;  }
}
