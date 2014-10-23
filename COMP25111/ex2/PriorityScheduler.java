import java.util.Random;

public class PriorityScheduler extends Scheduler {

  public PriorityScheduler() {
  }

  public synchronized void run() {
    while (true) {
      int noProcs = processList.size();
      if (noProcs == 0) System.exit(0);
      
      int next = getNextProcess();
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
  int getNextProcess(){
    int max = processList.get(0).getCount(),next = 0;
    for (int i = 0;i<processList.size();i++){
      int thisMax = processList.get(i).getCount();
      if(max<thisMax){
        max = thisMax;
        next = i;
      }
    }
    return next;
  }
}
