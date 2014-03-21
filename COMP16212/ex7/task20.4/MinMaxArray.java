import java.util.Arrays;
import java.util.Iterator;

/**
 * this class provides a getMinMax method to return the minimum and maximum
 * object from the given array of comparable items passed in
 */
public class MinMaxArray
{
  public static <Type extends Comparable<Type>> Pair<Type, Type>
          getMinMax(Type[] list)
  {
    /*
    using the iterator from Collections to serch for the largest and
    smallest item in the array linearly, with references to the curent
    largest/smallest item so far
    this also will return null if there are no items passed in the array
     */
    Iterator<Type> iterator = Arrays.asList(list).iterator();
    if (iterator.hasNext())
    {
      Type min, max;
      min = max = iterator.next();
      while (iterator.hasNext())
      {
        Type next = iterator.next();
        min = min.compareTo(next) > 0 ? next : min;
        max = max.compareTo(next) < 0 ? next : max;
      }
      return new Pair<Type, Type>(min, max);
    } else
    {
      return null;
    }
  }
}
