package javagently;

public class ListJJ  {

    /* The List class     by J M Bishop October 1997
	 *                    no change for Java 1.1
	 * Maintains a list of objects in
	 * last in first out order and provides simple
	 * iterator methods.
	 */

    private Node start, now, prev;
  
    public ListJJ () {
      now = null;
      start = null;
      prev = null;
    }

    public void add(Object x) {
      prev = now;
      if (start == null) { 
          start = new Node (x, null);
          now = start;
      } else { 
          Node T = new Node (x, now.link);
          now.link = T;
          now = T;
      }
    }

    public void remove() {
      if (isempty() || eol()) {
	      return;
	  } else {
          if (prev == null) {
              start = now.link;
          } else {
              prev.link = now.link;
              now = now.link;
          }
      }
    }
        
    public boolean isempty() {
      return start == null;
    }
  
    public Object current() {
      return now.data;
    }
  
    public void reset() {
      now = start;
	  prev=null;
    }
  
    public boolean eol() {
      return now.link == null;
    }
  
    public void succ() {
	  now = now.link;
      if (prev == null)
	      prev = start;
      else
	      prev = prev.link;
    }
  
  class Node {
  
    /* The node class for storing objects that 
	 * are linked together.
	 */
  
    Node link;
    Object data;
       
  
    /* The constructor copies in the data 
     * and the link to the given node
     * (which may be null).
	 */

    Node(Object d, Node n) {
        link = n;
        data = d;
    }
  }
}