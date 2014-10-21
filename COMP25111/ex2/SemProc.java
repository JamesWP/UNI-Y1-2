import java.util.Random ;

class SemProc extends Thread {

    private SemScheduler mySched ; 
    private int count ;
    private static Random threadSeed = new Random() ;

    private static int noProcs = 0 ;
    private static int barCounter = 0 ;
    private static Semaphore mutex = new Semaphore(1) ;
    private static Semaphore barQueue = new Semaphore(0) ;

    private static long getSeed() {
        return 1 + threadSeed.nextLong() ;
    }

    public SemProc(SemScheduler sch, String name, int i) {
	super(name) ;
	mySched = sch ;
	noProcs++ ;
	count = i ;
        synchronized (this) {
                start() ;
                try {
                        wait() ; // for process to be ready
                } catch (InterruptedException e) {}
        }
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

    private void reachBarrier(boolean lastTime) {
        mutex.P(this) ;
        barCounter++ ;
        if (barCounter == noProcs) {
		if (lastTime) noProcs-- ;
		if (barCounter > 1) barQueue.V() ;
		else { 
		    barCounter = 0 ; 
		    mutex.V() ;
		}
	} else {
            mutex.V() ; // must let others in and wait
            barQueue.P(this) ;
            barCounter -- ;
	    if (lastTime) noProcs-- ;
            if (barCounter > 1)  // i.e. more waiters
                barQueue.V() ;
             else {        // the last waiter
                barCounter = 0 ; // reset barrier
                mutex.V() ;
            }
        }
    }

    public void addToReadyQueue(boolean starting) {
	mySched.addProcess(this) ;
        if (starting) synchronized (this) {
                notify() ; // so constructor completes
                try {
                        wait() ; // so process is waiting when scheduler starts
                } catch (InterruptedException e) {}
        }
    }

    public void removeFromReadyQueue() {
	mySched.removeProcess(this) ;
    }

    public void run() {
	addToReadyQueue(true) ;
	Random myrand = new Random(getSeed()) ;
	while(count > 0) {
		int k = Math.abs(myrand.nextInt()) ;
		int ans = collatz(k) ;
		if (ans > 0) count-- ;
		System.out.println("This is " + getName() + " - Collatz of "
			+ k + " is " + ans) ;
                reachBarrier(count==0) ;
	}
	removeFromReadyQueue() ;
    }
}
