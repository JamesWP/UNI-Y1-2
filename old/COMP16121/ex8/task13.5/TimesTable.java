import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;
public class TimesTable extends JFrame
{
  public TimesTable(int number, int table)
  {
    super("TimesTable");
    setLayout(new GridLayout(5,5));
    // loops through each row in the table
    for (int curentNumber = 1;curentNumber<=number;curentNumber++)
    {
      add(new JLabel("" + curentNumber));
      add(new JLabel("X"));
      add(new JLabel("" + table));
      add(new JLabel("="));
      add(new JLabel("" + (curentNumber*table)));
    }
    pack();
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  public static void main(String[] args)
  {
    // get params
    int number = Integer.parseInt(args[0]);
    int table = Integer.parseInt(args[1]);
    // create gui
    new TimesTable(number,table);
  }
}
