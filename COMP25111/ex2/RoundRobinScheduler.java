import java.util.Random;

public class RoundRobinScheduler extends Scheduler {

  public RoundRobinScheduler() {
  }

  public synchronized void run() {
    int next = 0;
    while (true) {
      int noProcs = processList.size();
      if (noProcs == 0) System.exit(0);
      
      next++;
      next = next % noProcs;
      SimProc nextRunner = processList.get(next);
      synchronized(nextRunner) {
        nextRunner.notify();
      }
      try { wait();}
      catch (Exception e) {
        System.out.println("Unexpected interrupt in run " + e);
      }
    }
  }
}
