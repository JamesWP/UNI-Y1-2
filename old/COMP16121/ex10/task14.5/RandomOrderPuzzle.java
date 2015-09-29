import java.util.Scanner;

import java.io.File;

public class RandomOrderPuzzle
{
  public String[] origonalOrderLines;
  public String[] displayOrderLines;
  public RandomOrderPuzzle(Scanner fileInput){
    int noOfElements = 0;
    // initialise the array lenght to 2 so that i can double it later of needed
    String[] lines = new String[2];
    
    // add every line from the file to the array
    while(fileInput.hasNext())
      lines = addToArray(lines,noOfElements++,fileInput.nextLine());

    // make a correctly sized array for the elements in the origonal order
    origonalOrderLines = new String[noOfElements];
    for(int index = 0;index < noOfElements; index++)
      origonalOrderLines[index] = lines[index];

    // make a copy of the array to jumble up
    displayOrderLines = new String[noOfElements];
    for(int index = 0;index < noOfElements; index++)
      displayOrderLines[index] = lines[index];

    // mix up the diplay order list
    randomizeStringArrayOrder(displayOrderLines);
  }

  /**
   * returns true if the lines are sorted or false if not
   * @return sorted
   */
  public boolean isSorted()
  {
    for(int index = 0;index<origonalOrderLines.length;index++)
      if(displayOrderLines[index]!=origonalOrderLines[index])
        return false;
    return true;
  }

  /**
   * swaps the given line index with the last line
   * @param line the line index to swap
   */
  public void swapLine(int line)
  {
    int lastIndex = displayOrderLines.length-1;
    String temp = displayOrderLines[line];
    displayOrderLines[line] = displayOrderLines[lastIndex];
    displayOrderLines[lastIndex] = temp;
  }

  /**
   * adds the given item to the end of the sparse array and returns the 
   * reference to the new array
   */
  private String[] addToArray(String[] array,int noOfElements, String element)
  {
    if(noOfElements == array.length)
    {
      String[] newArray = new String[array.length*2];
      for(int index = 0; index < array.length; index++)
        newArray[index] = array[index];
      array = newArray;
    }
    array[noOfElements] = element;
    return array;
  }

  public static void main(String[] args) throws Exception
  {
    Scanner fileScanner = new Scanner( new File(args[0]));
    RandomOrderPuzzle puzzle = new RandomOrderPuzzle(fileScanner);
    Scanner inputScanner = new Scanner(System.in);
    System.out.println(puzzle);
    int moveCount = 0;
    while (! puzzle.isSorted())
    {
      System.out.print("Enter a line number to swap with the last one: ");
      puzzle.swapLine(inputScanner.nextInt());
      System.out.println(puzzle);
      moveCount++;
    }
    System.out.println("Game over in " + moveCount + " moves.");
  }
  private void randomizeStringArrayOrder(String[] anArray)
  {
    for ( int itemsRemaining = anArray.length;
      itemsRemaining > 0; itemsRemaining--)
    {
      int anIndex = ( int ) (Math.random() * itemsRemaining);
      String itemAtAnIndex = anArray[anIndex];
      anArray[anIndex] = anArray[anArray.length - 1];
      anArray[anArray.length - 1] = itemAtAnIndex;
    }
  }

  /**
   * prints the display lines array with line numbers
   * @return the string
   */
  public String toString()
  {
    String output = "";
    for(int lineNo = 0; lineNo<displayOrderLines.length;lineNo++)
      output += String.format("%2d\t%s%n",lineNo,displayOrderLines[lineNo]);
    return output;
  }
}
