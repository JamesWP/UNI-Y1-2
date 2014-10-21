import java.util.ArrayList ;

abstract public class Scheduler extends Thread {

protected ArrayList <SimProc> processList ;
// Note the Java 1.5 "generic" feature - which says
// that only SimProc objects will be stored in processList
// This removes the need for a cast when getting elements
// from processList. It has been used here to avoid the
// warning messages which the 1.5 compiler gives if the old
// syntax is used.

    public Scheduler() {
	processList = new ArrayList <SimProc> () ;
    }

   public synchronized void addProcess(SimProc t) {
	processList.add(t) ;
   }

   public void endSlice(SimProc t) {
	// the order of the following is important because we
	// don't want the scheduler to e.g. pick t for running
	// before we have made it wait
	synchronized(t) {
            synchronized(this) { notify() ; }
	    try { t.wait() ;}
 	    catch (Exception e) { 
		System.out.println("Unexpected interrupt in endSlice " + e) ;
	    }
	}
   }	

   public synchronized void removeProcess(SimProc t) {
	int i = processList.indexOf(t) ;
	processList.remove(i) ;
   }
}
