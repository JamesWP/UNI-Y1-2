class TestBarrier extends Thread {

    public static void main(String [] args) {
        SemScheduler mySched =  new SemScheduler() ;
	Thread p1 = new SemProc(mySched, "Anne", 3) ;
	Thread p2 = new SemProc(mySched, "Brenda", 3) ;
	Thread p3 = new SemProc(mySched, "Carole", 3) ; 
	Thread p4 = new SemProc(mySched, "Diana", 3) ;
	Thread p5 = new SemProc(mySched, "Evelyn", 3) ;
	Thread p6 = new SemProc(mySched, "Francis", 3) ;
	mySched.start() ;
    }

}
