import java.awt.Color;

public class LotteryTestE
{
  public static void main(String args[])
  {
    if(args.length!=1) return;

    SpeedController speedController
      = new SpeedController(SpeedController.HALF_SPEED);

    LotteryGUI gui = new LotteryGUI("TV Studio", speedController);
    
    if(args[0].equals("1"))
    {
      Game game = new Game("Magic Balls Test 1"
                              ,10,"Rack Name",4);
      gui.addGame(game);
      
      MagicWorker worker1 = new MagicWorker("Tim",MagicBall.Type.CAHOS);
      gui.addPerson(worker1);
      worker1.fillMachine(game);
    }else if(args[0].equals("2")){
      DramaticGame dramaticGame = new DramaticGame("Dramatic game swappy"
                                                      ,10,"Rack Name",4);
      gui.addGame(dramaticGame);
      
      MagicWorker worker1 = new MagicWorker("Tim",MagicBall.Type.SWAP);
      gui.addPerson(worker1);
      worker1.fillMachine(dramaticGame);
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
