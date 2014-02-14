public class LotteryTest
{
  public static void main(String args[])
  {
    SpeedController speedController
      = new SpeedController(SpeedController.HALF_SPEED);

    LotteryGUI gui = new LotteryGUI("TV Studio", speedController);

    TVHost tvHost = new TVHost("Terry Bill Woah B'Gorne");
    gui.addPerson(tvHost);
    speedController.delay(40);

    AudienceMember audienceMember1 = new AudienceMember("Ivana Di Yowt");
    gui.addPerson(audienceMember1);
    speedController.delay(40);

    Punter punter1 = new Punter("Ian Arushfa Rishly Ving");
    gui.addPerson(punter1);
    speedController.delay(40);

    Psychic psychic = new Psychic("Miss T. Peg de Gowt");
    gui.addPerson(psychic);
    speedController.delay(40);

    AudienceMember audienceMember2 = new AudienceMember("Norma Lurges");
    gui.addPerson(audienceMember2);
    speedController.delay(40);

/*
    Punter punter2 = new Punter("Al Winsom");
    gui.addPerson(punter2);
    speedController.delay(40);
*/

    Director director = new Director("Sir Lance Earl Otto");
    gui.addPerson(director);
    speedController.delay(40);

    CleverPunter cleverPunter1 = new CleverPunter("Wendy Athinkile-Win");
    gui.addPerson(cleverPunter1);
    speedController.delay(40);

    Worker worker1 = new TraineeWorker("Jim", 0);
    gui.addPerson(worker1);
    speedController.delay(40);

    CleverPunter cleverPunter2 = new CleverPunter("Luke Kovthe d'Ville");
    gui.addPerson(cleverPunter2);
    speedController.delay(40);

    Worker worker2 = new Worker("Merlin");
    gui.addPerson(worker2);
    speedController.delay(40);

    cleverPunter1.speak();
    speedController.delay(40);
    Game game1 = new Game("Lott O'Luck Larry", 49,
                          "Slippery's Mile", 7);
    gui.addGame(game1);
    speedController.delay(40);
    cleverPunter1.setGame(game1);
    cleverPunter1.speak();
    speedController.delay(40);

    cleverPunter2.speak();
    speedController.delay(40);
    Game game2 = new Game("Second Time Lucky", 34,
                          "Oooz OK Lose", 6);
    gui.addGame(game2);
    speedController.delay(40);
    cleverPunter2.setGame(game2);
    cleverPunter2.speak();
    speedController.delay(40);

    tvHost.speak();
    speedController.delay(40);

    worker1.fillMachine(game1);
    speedController.delay(40);

    punter1.speak();
    speedController.delay(40);

    for (int count = 1; count <= game1.getRackSize(); count ++)
    {
      game1.ejectBall();
      audienceMember1.speak();
      cleverPunter1.speak();
    } // for

    game1.rackSortBalls();
    speedController.delay(40);

    psychic.speak();
//    punter2.speak();
    speedController.delay(40);

    worker2.fillMachine(game2);
    speedController.delay(40);

    for (int count = 1; count <= game2.getRackSize(); count ++)
    {
      game2.ejectBall();
      audienceMember2.speak();
      cleverPunter2.speak();
    } // for

    game2.rackSortBalls();
    speedController.delay(40);

    while (true)
    {
      director.speak();
      cleverPunter1.speak();
      cleverPunter2.speak();
    } // for
  } // main

} // class LotteryTest
