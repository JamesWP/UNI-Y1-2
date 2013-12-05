/**
 * A repository of some generally useful static Mathematical methods.
 *
 * It contains only greatestCommonDivisor at the moment!
 *
 * @author John Latham
 */
public class MyMath
{
  /**
   * Compute the greatest common divisor of two numbers.
   * The numbers must be positive.
   *
   * @param multiple1OfGCD One of the numbers.
   * @param multiple2OfGCD The other number.
   *
   * @return The GCD of multiple1OfGCD and multiple2OfGCD.
   */
  public static int greatestCommonDivisor(int multiple1OfGCD,
                                          int multiple2OfGCD)
  {
    // Both multiple1OfGCD and multiple2OfGCD must be non-negative
    while (multiple1OfGCD != multiple2OfGCD)
      if (multiple1OfGCD > multiple2OfGCD)
        multiple1OfGCD -= multiple2OfGCD;
      else
        multiple2OfGCD -= multiple1OfGCD;
    return multiple1OfGCD;
  } // greatestCommonDivisor

} // class MyMath
