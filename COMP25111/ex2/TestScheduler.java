class TestScheduler extends Thread {

    public static void main(String [] args) {
        Scheduler mySched =  new RandomScheduler() ;
		// or new RoundRobinScheduler() - when written
		// or new PriorityScheduler()  - when written
	Thread p1 = new SimProc(mySched, "Alan", 4) ;
	Thread p2 = new SimProc(mySched, "Brian", 4) ;
	Thread p3 = new SimProc(mySched, "Charles", 4) ; 
	Thread p4 = new SimProc(mySched, "Dave", 4) ;
	Thread p5 = new SimProc(mySched, "Edward", 4) ;
	Thread p6 = new SimProc(mySched, "Francis", 4) ;
	mySched.start() ;
    }

}
