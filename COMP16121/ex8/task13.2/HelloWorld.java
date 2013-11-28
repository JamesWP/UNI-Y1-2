import javax.swing.JFrame;
import javax.swing.JLabel;
public class HelloWorld extends JFrame
{
  /**
   * creates the hello world gui application and shows it
   * @return a reference to the object
   */
  public  HelloWorld(){
    super("HelloWorld");
    add(new JLabel("Bonjour tout le monde!"));
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
    new HelloWorld();
    new HelloWorld();
  }
}
