import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

/**
 *  An extension of {@link JList JList} giving `toggling' selection of items
 *  displayed in the list.<P>
 *  This is taken directly from an article on
 *  <A HREF="http://java.sun.com/products/jfc/tsc/tech_topics/jlist_1/jlist.html">Advanced JList Programming</A> 
 *  from the Sun documentation for {@link JList JList}, and is written by Hans Muller.
 */

public class ToggleList extends JList 
{
  public ToggleList()
  {
    setSelectionModel(new ToggleSelectionModel());
  }

  public void removeSelection()
  {
     removeSelectionInterval(0, getModel().getSize() - 1);
  }
  // Taken from Sun Java 1.5 Tutorial:
  private class ToggleSelectionModel extends DefaultListSelectionModel
  {
      boolean gestureStarted = false;
      
      public void setSelectionInterval(int index0, int index1) {
	  if (isSelectedIndex(index0) && !gestureStarted) {
	      super.removeSelectionInterval(index0, index1);
	  }
	  else {
	      super.setSelectionInterval(index0, index1);
	  }
	  gestureStarted = true;
      }
  
      public void setValueIsAdjusting(boolean isAdjusting) {
	  if (isAdjusting == false) {
	      gestureStarted = false;
	  }
      }
  }
}
