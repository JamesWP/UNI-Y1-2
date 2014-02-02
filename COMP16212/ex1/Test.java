import java.util.HashSet;
public class Test{
  public static void main(String[] args){

    HashSet<GridPosition> set = new HashSet<GridPosition>();
    System.out.println(set.add(new GridPosition(1,1)));
    System.out.println(set.add(new GridPosition(1,2)));
    System.out.println(set.add(new GridPosition(1,3)));
    System.out.println(set.add(new GridPosition(1,1)));
    for(GridPosition g : set) System.out.println(g);
    
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
      System.out.println("equals");
      if(!(other instanceof GridPosition)) return false;
      GridPosition gridPosition = (GridPosition)other;
      return x == gridPosition.x && y == gridPosition.y;
    }
    public String toString(){return x + " " + y;}
  }
}