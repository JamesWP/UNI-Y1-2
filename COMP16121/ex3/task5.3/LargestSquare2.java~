public class LargestSquare2
{
  public static void main(String[] args)
  {
    int number = Integer.parseInt(args[0]);
    
    System.out.println("finding the largest sn <= to: "
       + number);
    
    //int bestGuess = 0;
    int bestGuess = (int) Math.floor(Math.sqrt(number));

    while(bestGuess * bestGuess < number)
      bestGuess++;
  
    System.out.println("Its: " + bestGuess);
  }
}
