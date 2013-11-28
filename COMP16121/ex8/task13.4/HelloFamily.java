import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;
public class HelloFamily extends JFrame
{
  /**
   * creates the gui for greeting the whole family
   * @return
   */
  public HelloFamily()
  {
    super("HelloFamily");
    setLayout(new GridLayout(2,2,10,10));
    add(new JLabel("James"));
    add(new JLabel("Ruth"));
    add(new JLabel("Tony"));
    add(new JLabel("Jane"));
    pack();
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  
  /**
   * starts the application
   * @param args
   */
  public static void main(String[] args)
  {
    new HelloFamily();
  }
}
