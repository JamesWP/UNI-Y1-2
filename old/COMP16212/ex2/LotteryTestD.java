import java.awt.Color;

public class LotteryTestD
{
  public static void main(String args[])
  {
    SpeedController speedController
      = new SpeedController(SpeedController.HALF_SPEED);

    LotteryGUI gui = new LotteryGUI("TV Studio", speedController);

    Game game1 = new Game("Magic Worker Test 1"
                            ,10,"Magic Rack Name",4);
    gui.addGame(game1);
    
    MagicWorker worker1 = new MagicWorker("Tim");
    gui.addPerson(worker1);
    worker1.fillMachine(game1);

    Game game2 = new Game("Magic Worker Test 2"
                            ,10,"Magic Rack Name",4);
    gui.addGame(game2);

    MagicWorker worker2 = new MagicWorker("Ted");
    gui.addPerson(worker2);
    worker2.fillMachine(game2);

    for (int count = 1; count <= game1.getRackSize(); count ++)
    {
      game1.ejectBall();
    } // for

    for (int count = 1; count <= game2.getRackSize(); count ++)
    {
      game2.ejectBall();
    } // for

  } // main

  public static final Color [] colors
      = new Color [] {Color.red, Color.orange, Color.yellow,
                      Color.green, Color.blue, Color.pink,
                      Color.magenta};

  public static Color getRandomBallColor()
  {
    return colors[(int) (Math.random() * colors.length)];
  }

} // class LotteryTest
