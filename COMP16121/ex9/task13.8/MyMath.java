public class MyMath
{
  public static int greatestCommonDivisor(int firstNo, int secondNo)
  {
    while(firstNo!=secondNo)
      if(firstNo>secondNo)
        firstNo-=secondNo;
      else
        secondNo-=firstNo;
    return firstNo;
  }

  public static int greatestCommonDivisor(int firstNo, int secondNo,
                                          int thirdNo)
  {
    return greatestCommonDivisor(firstNo,
                                 greatestCommonDivisor(secondNo,thirdNo));
  }
}
