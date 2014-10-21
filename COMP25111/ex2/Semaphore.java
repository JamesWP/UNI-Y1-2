import java.util.ArrayList ;

public class Semaphore {

   private int value ;
   private ArrayList <SemProc> queue ;

   public Semaphore(int i) {
      value = i ;
      queue = new ArrayList <SemProc> () ;
   }

   public synchronized void V() {
	// you need to write this
   }

   public void P(SemProc t) {
	// you need to write this too. Note it is not synchronized
	// because it may end by removing the current process, t, 
	// from the ready queue and that would leave the semaphore locked!
	// However it IS important to exclude the possibility that two 
	// processes could confuse each other - even though this is only a 
	// theorectical possibility. You will not observe it "going wrong" 
	// in the lab configuration - but you will lose a mark of you don't
	// get it right!
   }
}
