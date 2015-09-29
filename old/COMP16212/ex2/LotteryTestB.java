public class LotteryTestB
{
  public static void main(String args[])
  {
    SpeedController speedController
      = new SpeedController(SpeedController.HALF_SPEED);

    LotteryGUI gui = new LotteryGUI("TV Studio", speedController);

    Worker worker = new Worker("Jacob");
    gui.addPerson(worker);

    DramaticGame game = new DramaticGame("Dramatic Test"
                            ,49,"Rack Name",7);
    gui.addGame(game);
    worker.fillMachine(game);
    
    for (int count = 1; count <= game.getRackSize(); count ++)
    {
      game.ejectBall();
    } // for

 } // main

} // class LotteryTest
