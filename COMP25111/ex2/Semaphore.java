import java.util.ArrayList;

public class Semaphore {
  private int value;
  private ArrayList <SemProc> queue;

  public Semaphore(int i) {
    value = i;
    queue = new ArrayList <SemProc> ();
  }

  public synchronized void V() {
    System.out.println(this + " V()");
    value++;
    while(value <= 0) {
      System.out.println("Finished " + this + " value=" + value);
      queue.remove(0);
      notifyAll();
    }
  }

  public void P(SemProc t) {
    System.out.println(this + " P(" + t +")");
    synchronized(this){
      value--;
      if(value < 0) {
        System.out.println("Add to queue " + t);
        queue.add(t); 
        while(value < 0 || t != queue.get(0)) {
          System.out.println("Waiting " + this + " value=" + value);
          try{wait();}
          catch(InterruptedException e){}
        }
      }
    }
  }
}
