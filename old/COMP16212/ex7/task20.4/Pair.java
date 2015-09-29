/**
 * creates a pair of objects stored together of two types
 *
 * @param <TypeOne> the type of the first object
 * @param <TypeTwo> the type of the second object
 */
public class Pair<TypeOne, TypeTwo>
{
  private TypeOne elementOne;
  private TypeTwo elementTwo;

  /**
   * creates a pair with the supplied objects
   *
   * @param elementOne
   * @param elementTwo
   */
  public Pair(TypeOne elementOne, TypeTwo elementTwo)
  {
    this.elementOne = elementOne; this.elementTwo = elementTwo;
  }

  /**
   * returns the first object stored
   *
   * @return
   */
  public TypeOne getFirst()
  {
    return elementOne;
  }

  /**
   * retuns the second object stored
   *
   * @return
   */
  public TypeTwo getSecond()
  {
    return elementTwo;
  }

  /**
   * returns a sting representation of the objects stored using there toString
   *
   * @return
   */
  @Override
  public String toString()
  {
    return "Pair(" + getFirst() + "," +
                   "" + getSecond() + ")";
  }
}
