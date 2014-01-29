public class Game
{

// ----------------------------------------------------------------------
// Part a: the score message
  
  // this holds the massage to present
  private String scoreMessage;

  // accessor for score message
  public String getScoreMessage()
  {
    return scoreMessage;
  } // getScoreMessage


  // sets the score message 
  public void setScoreMessage(String message)
  {
    this.scoreMessage = message;
  } // setScoreMessage

  // returns the author 
  public String getAuthor()
  {
    return "James Peach";
  } // getAuthor


// ----------------------------------------------------------------------
// Part b: constructor and grid accessors

  // holds the state of the game including the size of the grid 
  public final Cell[][] grid;
  public final int gridSize;

  public Game(int requiredGridSize)
  {
    // sets the grid size 
    this.gridSize = requiredGridSize;
    // initialises the grid array to the correct size
    grid = new Cell[requiredGridSize][requiredGridSize];
    // loops through each element in the two dimentional array and sets the cell
    //  reference to a new blank one
    for (int index = 0;index<requiredGridSize*requiredGridSize;index++)
        grid[index/requiredGridSize][index%requiredGridSize] = new Cell();
  } // Game


  public int getGridSize()
  {
    return gridSize;
  } // getGridSize


  public Cell getGridCell(int x, int y)
  {
    return grid[x][y];
  } // getGridCell


// ----------------------------------------------------------------------
// Part c: initial game state

// Part c-1: setInitialGameState method

  public void setInitialGameState(int requiredTailX, int requiredTailY,
                                  int requiredLength, int requiredDirection)
  {
    setClear();
  } // setInitialGameState

  /**
   * sets the grid to clear all cells
   */
  private void setClear()
  {
    for (int index = 0;index<gridSize*gridSize;index++)
      grid[index/gridSize][index%gridSize].setClear();
  }
// ----------------------------------------------------------------------
// Part c-2 place food


// ----------------------------------------------------------------------
// Part c-3: place snake


// ----------------------------------------------------------------------
// Part d: set snake direction


  public void setSnakeDirection(int requiredDirection)
  {
  } // setSnakeDirection


// ----------------------------------------------------------------------
// Part e: snake movement

// Part e-1: move method


  public void move(int moveValue)
  {
  } // move


// ----------------------------------------------------------------------
// Part e-2: move the snake head


// ----------------------------------------------------------------------
// Part e-3: move the snake tail


// ----------------------------------------------------------------------
// Part e-4: check for and deal with crashes


// ----------------------------------------------------------------------
// Part e-5: eat the food


  public int getScore()
  {
    return 99999999;
  } // getScore


// ----------------------------------------------------------------------
// Part f: cheat


  public void cheat()
  {
  } // cheat


// ----------------------------------------------------------------------
// Part g: trees


  public void toggleTrees()
  {
  } // toggleTrees


// ----------------------------------------------------------------------
// Part h: crash countdown


// ----------------------------------------------------------------------
// Part i: optional extras


  public String optionalExtras()
  {
    return "  No optional extras defined\n";
  } // optionalExtras


  public void optionalExtraInterface(char c)
  {
    if (c > ' ' && c <= '~')
      setScoreMessage("Key " + new Character(c).toString()
                      + " is unrecognised (try h)");
  } // optionalExtraInterface

} // class Game
