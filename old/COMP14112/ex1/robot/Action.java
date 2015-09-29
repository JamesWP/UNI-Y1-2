package robot;

/**
   Basic robot actions. E.g. go forward 3 units
   
*/
public class Action{

  /**
     One of the constants GO_FORWARD, TURN_LEFT or TURN_RIGHT
  */
  public ActionType type;
  /**
     Number of units to move/turn
  */
  public int parameter;

  public Action(){}

  public Action(ActionType type1, int parameter1){

    type= type1;
    parameter= parameter1;
  }
}
