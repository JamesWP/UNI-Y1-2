import java.util.Random ;

public class RandomScheduler extends Scheduler {

private Random randGen ;

    public RandomScheduler() {
	randGen = new Random() ;
    }

    public synchronized void run() {
	while (true) {
  	    int noProcs = processList.size() ;
	    if (noProcs == 0) System.exit(0) ;
	    int next = Math.abs(randGen.nextInt()) % noProcs ;
	    SimProc nextRunner = processList.get(next) ;
	    synchronized(nextRunner) { nextRunner.notify() ;}
   	    try { wait() ;}
	    catch (Exception e) { 
		System.out.println("Unexpected interrupt in run " + e) ; 
	    }
	}
    }
}
