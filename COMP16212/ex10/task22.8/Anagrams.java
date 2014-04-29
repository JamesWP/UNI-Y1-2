/**
 * This program takes the first parameter and prints all permutations of the 
 * given characters using recustion
 *
 **/

public class Anagrams
{ private static char[] givenString;
  private static boolean[] used;
  private static char[] permutation;
 
  /**
   * this first convers the string to a char array with the instance method
   * toCharArray()
   **/
  public static void main(String args[])
  { if(args.length!=1)
    { System.out.println("You must supply exactly one parameter");
      return;
    }
    givenString = args[0].toCharArray();
    used = new boolean[givenString.length];
    permutation = new char[givenString.length];
    // start the recusive method
    printPermutations(0);
  }
  
  /**
   * prints all the permutations for the given string by manipilating all
   * characters past the curentIndex 
   * @param curentIndex the index to start at when printing
   */
  public static void printPermutations(int currentIndex)
  { 
    // base case for recursion
    if (currentIndex >= permutation.length)
      System.out.println(permutation);
    else
    {
      // print all other permutations one for each character in the given
      // string if it hasent been used in the string so far
      for (int index = 0;index < givenString.length; index++)
      {
        if (!used[index])
        {
          used[index] = true;
          permutation[currentIndex] = givenString[index];
          printPermutations(currentIndex + 1);
          used[index] = false;
        }
      } 
    }
  }
}
