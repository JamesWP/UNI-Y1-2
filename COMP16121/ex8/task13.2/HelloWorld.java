import javax.swing.JFrame;
import javax.swing.JLabel;
public class HelloWorld extends JFrame
{
  public  HelloWorld(){
    super("HelloWorld");
    add(new JLabel("Bonjour tout le monde!"));
    pack();
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  public static void main(String[] args)
  {
    new HelloWorld();
  }
}
