import java.util.Random;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Iterator;

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

  // contructs the game state and initialises it
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

  // returns the grid size
  public int getGridSize()
  {
    return gridSize;
  } // getGridSize


  // returns the reference to the grid
  public Cell getGridCell(int x, int y)
  {
    return grid[x][y];
  } // getGridCell


// ----------------------------------------------------------------------
// Part c: initial game state

// Part c-1: setInitialGameState method

  // variable to store the current score
  private int score = 0;
  // variable to get random positions from also used for testing seeds
  private Random random = new Random();
  // variables to keep track of positions of items and length
  private int headX,headY,tailX,tailY,direction,length;


  // method to initialise the game state to initial 
  public void setInitialGameState(int requiredTailX, int requiredTailY,
                                  int requiredLength, int requiredDirection)
  {
    setClear();
    placeSnake(requiredTailX,requiredTailY,requiredLength,requiredDirection);
    placeFood();
    setScore(0);
    trees = 0;
    if(treesEnabled) placeTree();
  } // setInitialGameState

  // sets the score 
  private void setScore(int newScore)
  {
    this.score = newScore;
  }

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
// //places the food on the board in an empty cell

// stores the location of the food used in ai
  private int foodX,foodY;


  private void placeFood()
  {
    int randX = random.nextInt(gridSize);
    int randY = random.nextInt(gridSize);
    Cell randCell = getGridCell(randX,randY);
    if(randCell.getType()==Cell.CLEAR||randCell.getType()==Cell.OTHER)
    {
      randCell.setFood();
      foodX = randX;
      foodY = randY;
    }
    else
      placeFood();
  }

// ----------------------------------------------------------------------
// Part c-3: place snake
// 
  // places the snake in the given position
  private void placeSnake(int tailX, int tailY, int length, int direction)
  {
    this.tailX = tailX;
    this.tailY = tailY;
    this.length = length;

    // works out deltas for each seciton
    int xMul = Direction.xDelta(direction);
    int yMul = Direction.yDelta(direction);
    int oppositeDirection = Direction.opposite(direction);

    this.headX = tailX+(length-1)*xMul;
    this.headY = tailY+(length-1)*yMul;

    // sets the tail position
    getGridCell(tailX,tailY).setSnakeTail(oppositeDirection,direction);
    // sets the head position
    getGridCell(headX,headY).setSnakeHead(oppositeDirection,direction);

    //sets the snake body sections
    for (int segment = 1; segment < length-1;segment++)
      getGridCell(tailX+segment*xMul,tailY+segment*yMul)
                  .setSnakeBody(oppositeDirection,direction);
  }

// ----------------------------------------------------------------------
// Part d: set snake direction

  // called by gui to set the direction of the snake
  public void setSnakeDirection(int requiredDirection)
  {
    Cell snakeHead = getGridCell(headX,headY);
    if(snakeHead.getSnakeInDirection()==requiredDirection)
      setScoreMessage("You cant eat yourself!");
    else
      snakeHead.setSnakeOutDirection(requiredDirection);
  } // setSnakeDirection


// ----------------------------------------------------------------------
// Part e: snake movement

// Part e-1: move method

  // called to update game state for the next move
  public void move(int moveValue)
  {
    Cell snakeHead = getGridCell(headX,headY);
    if(snakeHead.isSnakeBloody()) return;

    // if enabled this block deals with the ai implementation
    if(aiEnabled)
    {
      // if there are no moves then recalculate path
      if(nextMoves.size()<gridSize*2||nextMoves.size()==0)
        nextMoves = getMovesList();
      if(!nextMoves.isEmpty())
        snakeHead.setSnakeOutDirection(nextMoves.removeLast());
      else
        // go in random direction no moves available 
        snakeHead.setSnakeOutDirection(getNextHeadDirection());
    }

    // if food move then move it!
    if(foodMoveEnabled)
    {
      moveFood();
    }

    int newHeadX = headX + Direction.xDelta(snakeHead.getSnakeOutDirection());
    int newHeadY = headY + Direction.yDelta(snakeHead.getSnakeOutDirection());

    if(canMoveTo(newHeadX,newHeadY))
    {
      if(isFoodAt(newHeadX,newHeadY))
        eatFood(moveValue);
      else
        moveSnakeTail();
      moveSnakeHead(newHeadX,newHeadY);
      resetCountdown();
    } else {
      if(reduceCountdown()){
        if(isOnGrid(newHeadX,newHeadY))
          setScoreMessage("You hit something :(");
        else
          setScoreMessage("Hit the wall :(");
        crashSnakeAt(newHeadX,newHeadY);
      }
    }
    updateOtherTiles();
  } // move


// ----------------------------------------------------------------------
// Part e-2: move the snake head
// moves the snake head to the given cell
  public void moveSnakeHead(int newHeadX, int newHeadY)
  {
    Cell curentHead = getGridCell(headX,headY);
    int inDirection = Direction.opposite(curentHead.getSnakeOutDirection());
    int outDirection = curentHead.getSnakeOutDirection();
    curentHead.setSnakeBody();
    Cell newHead = getGridCell(newHeadX,newHeadY);
    newHead.setSnakeHead(inDirection,outDirection);
    headX = newHeadX;
    headY = newHeadY;
  }

// ----------------------------------------------------------------------
// Part e-3: move the snake tail
// moves the snake tail to the next place
  public void moveSnakeTail()
  {
    Cell snakeTail = getGridCell(tailX,tailY);
    tailX += Direction.xDelta(snakeTail.getSnakeOutDirection());
    tailY += Direction.yDelta(snakeTail.getSnakeOutDirection());
    snakeTail.setOther(50);
    Cell newTail = getGridCell(tailX,tailY);
    newTail.setSnakeTail();
  }

// ----------------------------------------------------------------------
// Part e-4: check for and deal with crashes
// //helper method to check tile is on grid and able to move to by snake
  public boolean canMoveTo(int testX,int testY)
  {
    if(isOnGrid(testX,testY))
    {
      int cellType = getGridCell(testX,testY).getType();
      return cellType != Cell.SNAKE_HEAD
            && cellType != Cell.SNAKE_BODY
            && cellType != Cell.SNAKE_TAIL
            && cellType != Cell.TREE;
    } else
      return false;
  }
  // returns true if coords is on grid
  public boolean isOnGrid(int testX, int testY)
  {
    return testX>=0&&testY>=0&&testX<gridSize&&testY<gridSize;
  }

  // crashes the snake at the cellx,celly position
  public void crashSnakeAt(int cellX, int cellY)
  {
    if(isOnGrid(cellX,cellY))
    {
      Cell cell = getGridCell(cellX,cellY);
      if(cell.isSnakeType())
        cell.setSnakeBloody(true);
    }
    Cell snakeHead = getGridCell(headX,headY);
    snakeHead.setSnakeBloody(true);
  }

// ----------------------------------------------------------------------
// Part e-5: eat the food
// // eats the food with the given value
  public void eatFood(int moveValue)
  {
    int rawIncrease = moveValue 
          * ((length++ / (gridSize * gridSize / 36 + 1)) + 1);
    int actualIncrease = rawIncrease * (treesEnabled&&trees>0?trees:1);
    score += actualIncrease;
    
    setScoreMessage("You gained " + rawIncrease + " points; " + (treesEnabled?
                    "With trees thats " +  actualIncrease + "! ":"") + "Your"+
                    " total is now " + score );

    placeFood();
    if(treesEnabled) placeTree();
  }
  // tests for food at the given location
  public boolean isFoodAt(int cellX, int cellY)
  {
    return getGridCell(cellX,cellY).getType()==Cell.FOOD;
  }

  // retuns the score
  public int getScore()
  {
    return score;
  } // getScore


// ----------------------------------------------------------------------
// Part f: cheat


  // enabled the player to continue playing after death
  public void cheat()
  {
    setScoreMessage("You cheated you lost " + getScore()/2 + " score");
    // loops through each cell and un bloodys it
    for (int index = 0;index<gridSize*gridSize;index++)
        grid[index/gridSize][index%gridSize]
            .setSnakeBloody(false);
    // resets death countdown
    resetCountdown();
  } // cheat


// ----------------------------------------------------------------------
// Part g: trees

  private int trees;
  private boolean treesEnabled = false;

  // toggles the display of trees and updates number of trees
  public void toggleTrees()
  {
    if(treesEnabled)
    {
      //disable
      //remove all trees
      for (int index = 0;index<gridSize*gridSize;index++)
      {
        Cell cell = grid[index/gridSize][index%gridSize];
        if(cell.getType()==Cell.TREE)
          cell.setClear();
      }
      trees = 0;
    } else {
      trees = 0;
      placeTree();
    }
    
    treesEnabled = !treesEnabled;
  } // toggleTrees


  // places tree in empty area of grid
  public void placeTree()
  {
    int randX = random.nextInt(gridSize);
    int randY = random.nextInt(gridSize);
    Cell randCell = getGridCell(randX,randY);
    if(randCell.getType()==Cell.CLEAR)
      randCell.setTree();
    else
      placeTree();
    trees++;
  }

// ----------------------------------------------------------------------
// Part h: crash countdown
// 
// no of turns of countdown to stat with
  private final int countdownStart = 5;
// no of turns of countdown currently left
  private int curentCountdown = countdownStart;

  private boolean aiEnabled = true;
  private boolean foodMoveEnabled = true;


  // resets countdown after danger has been avoided
  public void resetCountdown()
  {
    if(curentCountdown<countdownStart)
      setScoreMessage("You were lucky to survive this time!  you had "
                      + curentCountdown + " moves left...");
    curentCountdown = countdownStart;
  }

  // reduce countdown and return if snake dead
  public boolean reduceCountdown()
  {
    if(curentCountdown-->0)
      setScoreMessage("You only have " + curentCountdown + " moves left! MOVE");
    else {
      curentCountdown = countdownStart;
      return true;
    }
    return false;
  }
// ----------------------------------------------------------------------
// Part i: optional extras
/**     ___    _________
 *     / . \   |_______|
 *    / / \ \     | |
 *    |*|-| |   __| |__
 *    |_|-|_|  |_______|
 *    
 *    copyright james peach 2014
 */

  
  public LinkedList<Integer> nextMoves = new LinkedList<Integer>();

  public void updateOtherTiles()
  {
    for (int index = 0;index<gridSize*gridSize;index++)
    {
      Cell cell = grid[index/gridSize][index%gridSize];
      if(cell.getType()==Cell.OTHER)
      {
        int curentLevel = cell.getOtherLevel();
        curentLevel -= 5;
        if(curentLevel<0)
          cell.setClear();
        else
          cell.setOther(curentLevel);
      }
    }
      
  }

  public void burnTrees()
  {
    int direction = getGridCell(headX,headY).getSnakeOutDirection();
    int xDelta = Direction.xDelta(direction);
    int yDelta = Direction.yDelta(direction);
    int curentX = headX + xDelta;
    int curentY = headY + yDelta;

    while(isOnGrid(curentX,curentY))
    {
      Cell curentCell = getGridCell(curentX,curentY);
      int cellType = curentCell.getType();
      if(cellType==Cell.TREE)
        trees--;
      if(cellType==Cell.CLEAR||cellType==Cell.TREE)
        curentCell.setOther(20);
      curentX += xDelta;
      curentY += yDelta;
    }
  }

  public void moveFood()
  {
    int newFoodX = foodX;
    int newFoodY = foodY;

    if(foodX>headX)
      newFoodX++;
    else if(foodX<headX)
      newFoodX--;

    if(foodY>headY)
      newFoodY++;
    else if(foodY<headY)
      newFoodY--;

    if(newFoodY<0||newFoodY>=gridSize)
      newFoodY = foodY;

    if(newFoodX<0||newFoodX>=gridSize)
      newFoodX = foodX;

    if(getGridCell(newFoodX,newFoodY).getType()==Cell.CLEAR)
    {
      getGridCell(foodX,foodY).setClear();
      getGridCell(newFoodX,newFoodY).setFood();
      foodY = newFoodY;
      foodX = newFoodX;
    }
  }


  public LinkedList<Integer> getMovesList()
  {
    int[][] grid = new int[gridSize][gridSize];
    for(int y = 0;y<gridSize;y++)
      for(int x = 0;x<gridSize;x++)
        grid[x][y] = 99;
    HashSet<GridPosition> completedPositions 
        = new HashSet<GridPosition>(gridSize*gridSize);
    PriorityQueue<GridPosition> workingNodes
        = new PriorityQueue<GridPosition>(gridSize,
                                          new GridPositionComparator());

    workingNodes.add(new GridPosition(headX,headY));
    grid[headX][headY] = 0;

    //int itt = 99999;

    while(!workingNodes.isEmpty())
    {
      GridPosition curentCell = workingNodes.remove();
      completedPositions.add(curentCell);
      int travelDistance = grid[curentCell.getX()][curentCell.getY()] + 1;
      LinkedList<Integer> path = null;

      path = processCell(curentCell.getCellTop(),grid,completedPositions
                  ,workingNodes,travelDistance);
      if(path != null) return path;
      path = processCell(curentCell.getCellRight(),grid,completedPositions
                  ,workingNodes,travelDistance);
      if(path != null) return path;
      path = processCell(curentCell.getCellBottom(),grid,completedPositions
                  ,workingNodes,travelDistance);
      if(path != null) return path;
      path = processCell(curentCell.getCellLeft(),grid,completedPositions
                  ,workingNodes,travelDistance);
      if(path != null) return path;

      // if(itt--<0)
      // {
      //   printGrid(grid);
      //   System.exit(1);
      // }
    }

    // no path found so return no moves!
    return new LinkedList<Integer>();
  }

  public LinkedList<Integer> processCell(GridPosition cell
              , int[][] grid
              , HashSet<GridPosition> completedPositions
              , PriorityQueue<GridPosition> workingNodes
              , int travelDistance)
  {
    if(isOnGridAndClear(cell)&&!completedPositions.contains(cell))
      {
        if(isCellFood(cell))
        {
          grid[cell.getX()][cell.getY()] = travelDistance;
          return getMovesFromGrid(cell.getX(),cell.getY(),grid);
        } else {
          int curValue = grid[cell.getX()][cell.getY()];
          if(curValue>travelDistance || curValue == 99)
            grid[cell.getX()][cell.getY()] = travelDistance;
          workingNodes.add(cell);
        }
      }
      return null;
  }

  public LinkedList<Integer> getMovesFromGrid(int finishX,int finishY
                            ,int[][] grid)
  {
    
    int curentX = finishX;
    int curentY = finishY;
    LinkedList<Integer> moves = new LinkedList<Integer>();
    int min = grid[curentX][curentY];
    while(min!=0)
    {
      //System.out.println(min);
      int top = curentY+1<gridSize? grid[curentX][curentY+1]:min+1;
      int right = curentX+1<gridSize? grid[curentX+1][curentY]:min+1;
      int bottom = curentY>0? grid[curentX][curentY-1]:min+1;
      int left = curentX>0? grid[curentX-1][curentY]:min+1;
      
      //System.out.println(top + " " + right + " " + bottom + " " + left + " " + min);

      min = Math.min(top,Math.min(left,Math.min(right,bottom)));
      if(min==top){
        curentY++;
        moves.add(Direction.NORTH);
      }else if(min ==left){
        curentX--;
        moves.add(Direction.EAST);
      }else if(min ==right){
        curentX++;
        moves.add(Direction.WEST);
      }else if(min==bottom){
        curentY--;
        moves.add(Direction.SOUTH);
      }else throw new RuntimeException("math broke... again");
    }
    //Iterator<Integer> it = moves.descendingIterator();
    //while(it.hasNext())
    // System.out.println(it.next());
    
    return moves;
  }

  public boolean isCellFood(GridPosition position)
  {
    Cell cell = getGridCell(position.getX(),position.getY());
    return cell.getType()==Cell.FOOD;
  }

  public boolean isOnGridAndClear(GridPosition position)
  {
    if(!isOnGrid(position.getX(),position.getY())) return false;
    Cell cell = getGridCell(position.getX(),position.getY());
    return cell.getType()==Cell.OTHER
        ||cell.getType()==Cell.CLEAR
        ||cell.getType()==Cell.FOOD;
  }

  public int getNextHeadDirection()
  {
    if(canGoToCell(headX+1,headY))
      return Direction.EAST;

    if(canGoToCell(headX-1,headY))
      return Direction.WEST;
    
    if(canGoToCell(headX,headY+1))
      return Direction.SOUTH;
    
    if(canGoToCell(headX,headY-1))
      return Direction.NORTH;

    //throw new RuntimeException("couldnt find direction");
    return Direction.NORTH;
  }

  public boolean canGoToCell(int xPosition,int yPosition)
  {
    return (isOnGrid(xPosition,yPosition)
            && (
                getGridCell(xPosition,yPosition).getType()==Cell.CLEAR
                || getGridCell(xPosition,yPosition).getType()==Cell.OTHER
                || getGridCell(xPosition,yPosition).getType()==Cell.FOOD
               )
           );
  }

  public void printGrid(int[][] grid)
  {
    for(int y = 0;y<gridSize;y++)
    {
      for(int x = 0;x<gridSize;x++)
        System.out.printf("%2d ",grid[x][y]);
      System.out.println();
    }
  }

  public String optionalExtras()
  {
    return "  b: BURN BURN\n  b: toggle ai\n  n: toggle food movement";
  } // optionalExtras


  public void optionalExtraInterface(char c)
  {
    if(c == 'b')
    {
      burnTrees();
      return;
    }
    if(c == 'v')
    {
      aiEnabled = !aiEnabled;
      setScoreMessage("Ai is currently " + (aiEnabled?"enabled":"disabled") 
                      + " toggle to " +(!aiEnabled?"enable":"disable") + " it");
      return;
    }
    if(c == 'n')
    {
      foodMoveEnabled = !foodMoveEnabled;
      setScoreMessage("Food movement is currently " 
                      + (foodMoveEnabled?"enabled":"disabled") 
                      + " toggle to " +(!foodMoveEnabled?"enable":"disable") 
                      + " it");
      return;
    }
    if (c > ' ' && c <= '~')
      setScoreMessage("Key " + new Character(c).toString()
                      + " is unrecognised (try h)");
  } // optionalExtraInterface

  public static class GridPosition
  {
    private int x,y;
    public GridPosition(int x, int y)
    {
      this.x = x;
      this.y = y;
    }
    public boolean equals(Object other)
    {
      if(!(other instanceof GridPosition)) return false;
      GridPosition gridPosition = (GridPosition)other;
      return x == gridPosition.x && y == gridPosition.y;
    }
    public int hashCode()
    {
      return 7+59*x+7*y;
    }
    public int getX() {return x;}
    public int getY() {return y;}
    public GridPosition getCellTop(){return new GridPosition(x,y+1);}
    public GridPosition getCellRight(){return new GridPosition(x+1,y);}
    public GridPosition getCellBottom(){return new GridPosition(x,y-1);}
    public GridPosition getCellLeft(){return new GridPosition(x-1,y);}
    public String toString(){return getX()+ " "+getY();}
  }

  public class GridPositionComparator implements Comparator<GridPosition>
  {
    public int compare(GridPosition p1, GridPosition p2)
    {
      return manhattenDist(p1) - manhattenDist(p2);
    }
    public int manhattenDist(GridPosition pos)
    {
      int xDelta = pos.getX() - foodX;
      int yDelta = pos.getY() - foodY;
      return (xDelta>0?xDelta:-xDelta)+(yDelta>0?yDelta:-yDelta);
    }
  }
} // class Game
