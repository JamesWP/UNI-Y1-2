import java.awt.Color;

public class LotteryTestC
{
  public static void main(String args[])
  {
    SpeedController speedController
      = new SpeedController(SpeedController.HALF_SPEED);

    LotteryGUI gui = new LotteryGUI("TV Studio", speedController);

    Game game = new Game("Magic Test"
                            ,10,"Magic Rack Name",4);
    gui.addGame(game);
    

    for (int ballNo = 1; ballNo <= game.getMachineSize(); ballNo++)
    {
      final MagicBall newBall = new MagicBall(ballNo,getRandomBallColor());
      game.machineAddBall(newBall);
    }
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
