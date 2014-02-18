public class LotteryTestA
{
  public static void main(String args[])
  {
    SpeedController speedController
      = new SpeedController(SpeedController.HALF_SPEED);

    LotteryGUI gui = new LotteryGUI("TV Studio", speedController);

    TVHost tvHost = new TVHost("Terry Bill Woah B'Gorne");
    gui.addPerson(tvHost);

    AudienceMember audienceMember1 = new AudienceMember("Ivana Di Yowt");
    gui.addPerson(audienceMember1);

    Punter punter1 = new Punter("Ian Arushfa Rishly Ving");
    gui.addPerson(punter1);

    Psychic psychic = new Psychic("Miss T. Peg de Gowt");
    gui.addPerson(psychic);

    AudienceMember audienceMember2 = new AudienceMember("Norma Lurges");
    gui.addPerson(audienceMember2);

    Director director = new Director("Sir Lance Earl Otto");
    gui.addPerson(director);

    CleverPunter cleverPunter1 = new CleverPunter("Wendy Athinkile-Win");
    gui.addPerson(cleverPunter1);

    Worker worker = new Worker("Jacob");
    gui.addPerson(worker);

    TraineeWorker traineeWorker = new TraineeWorker("Jim", 0);
    gui.addPerson(traineeWorker);

    Teenager teeneger = new Teenager("Terry ");
    gui.addPerson(teeneger);
  } // main

} // class LotteryTest
