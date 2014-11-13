import java.util.ArrayList;

public class Semaphore {
  private int value;
  private ArrayList <SemProc> queue;

  public Semaphore(int i) {
    value = i;
    queue = new ArrayList <SemProc> ();
  }

  public void P(SemProc t) {
	  value--;
		synchronized(t){
		  if(value<0){
	      queue.add(t);	
			  t.removeFromReadyQueue();
      }
  	}
  }

  public synchronized void V() {
		value++;
		if(value<=0){
			SemProc next = queue.get(0);
			queue.remove(0);
			next.addToReadyQueue(false);
		}
  }
}
