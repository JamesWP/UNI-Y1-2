import java.util.HashSet;
public class Test{
  public static void main(String[] args){

    HashSet<GridPosition> set = new HashSet<GridPosition>();
    set.add(new GridPosition(1,1));
    set.add(new GridPosition(1,2));
    set.add(new GridPosition(1,3));
    set.add(new GridPosition(1,1));
    for(GridPosition g : set) System.out.println(g);
    System.out.println("blah");
    System.out.println(set.contains(new GridPosition(1,1)));

  }
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
    public int getX() {return x;}
    public int getY() {return y;}
    public GridPosition getCellTop(){return new GridPosition(x,y+1);}
    public GridPosition getCellRight(){return new GridPosition(x+1,y);}
    public GridPosition getCellBottom(){return new GridPosition(x,y-1);}
    public GridPosition getCellLeft(){return new GridPosition(x-1,y);}
    public String toString(){return getX()+ " "+getY();}
  }
}