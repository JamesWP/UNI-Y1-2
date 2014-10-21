import java.util.Random ;

class SimProc extends Thread {

    private Scheduler mySched ; 
    private int count ;
    private static Random threadSeed = new Random() ;

    private static long getSeed() {
        return 1 + threadSeed.nextLong() ; 
    }

    public SimProc(Scheduler sch, String name, int i) {
	super(name) ;
	mySched = sch ;
	count = i ;
	synchronized (this) { 
	        start() ;
		try {
			wait() ; // for process to be ready
		} catch (InterruptedException e) {}
	}
    }

    public int getCount() {
       return count ;
    }

    private int collatz(int i) {
	int ans = 0 ;
	while (i != 1) {
		if (i <= 0) return -1 ; // numbers get too big!
		ans++ ;
		if (i % 2 == 0) i /= 2 ;
		else i = i*3 + 1 ;
	}
	return ans ;
    }

    private void addToReadyQueue() {
	mySched.addProcess(this) ;
	synchronized (this) {
		notify() ; // so constructor completes
		try { 
			wait() ; // so process is waiting when scheduler starts
		} catch (InterruptedException e) {}
	}
    }

    private void removeFromReadyQueue() {
	mySched.removeProcess(this) ;
	mySched.endSlice(this) ; // want something else to run!
    }

    public void run() {
	addToReadyQueue() ;
	Random myrand = new Random(getSeed()) ;
	while(count > 0) {
		int k = Math.abs(myrand.nextInt()) ;
		int ans = collatz(k) ;
		if (ans > 0) count-- ;
		System.out.println("This is " + getName() + " - Collatz of "
			+ k + " is " + ans) ;
		mySched.endSlice(this) ;

	}
	removeFromReadyQueue() ;
    }
}
