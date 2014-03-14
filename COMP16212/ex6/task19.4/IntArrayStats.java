public class IntArrayStats
{
  public static Triple<Integer,Integer,Double> getStats(int[] array)
  {
    /**
     * calculate the stats.
     * calculate mean by adding the numbers and dividing by length
     * calculate min max by taking first number then for each other number:
     * if the number is bigger or smaller than the curent min max respec. then 
     * change the min max respec
     **/
    int total = array[0];
    int min = array[0];
    int max = array[0];
    for(int index = 1;index<array.length;index++)
    {
      if(array[index]<min) min = array[index];
      if(array[index]>max) max = array[index];
      total += array[index];
    }
    /**
     * construct new triple for these states.
     * from max,min,mean
     */
    return new Triple<Integer,Integer,Double>(max,min
      ,(double)total / array.length);
  }
}
