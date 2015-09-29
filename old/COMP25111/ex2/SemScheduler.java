import java.util.ArrayList ;

public class SemScheduler extends Thread {

protected ArrayList <SemProc> processList ;

    public SemScheduler() {
	processList = new ArrayList <SemProc> () ;
    }

    public synchronized void addProcess(SemProc t) {
	    processList.add(t) ;
    }

   public void removeProcess(SemProc t) {
	synchronized(t) {
	    synchronized(this) {
	        processList.remove(t) ;
                notify() ;  // it is always the running process which removes itself
			// so need to schedule another
            }
	    try { t.wait() ;} // and send it back to sleep
 	    catch (Exception e) { 
		System.out.println("Unexpected wakeup " + e) ;
	    }
	}
   }	

   public synchronized void run() {
	while (true) {
	    if (processList.isEmpty()) {
		System.out.println("Nothing left to run! Exiting.") ;
		System.exit(0) ;
	    }
	    SemProc nextRunner = processList.get(0) ;
	    synchronized(nextRunner) { nextRunner.notify() ; }
	    try { wait() ; }  // until that process removes itself
	    catch (Exception e) { 
                System.out.println("Unexpected interrupt in run " + e) ; 
	    }
        }
    }

}
