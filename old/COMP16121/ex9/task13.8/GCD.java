import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GCD extends JFrame
{

  private final JTextField numberOneTF = new JTextField(20);
  private final JTextField numberTwoTF = new JTextField(20);
  private final JTextField numberThreeTF = new JTextField(20);
  private final JTextField resultTF = new JTextField(20);
  private final JButton computeBtn = new JButton("Compute");
  public GCD()
  {
    // add action listeners
    computeBtn.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent actionEvent)
      {
        resultTF.setText("" + MyMath.greatestCommonDivisor(
                        Integer.parseInt(numberOneTF.getText()),
                        Integer.parseInt(numberTwoTF.getText()),
                        Integer.parseInt(numberThreeTF.getText())));
      }
    });

    // add components
    setLayout(new GridLayout(0,1));
    add(new JLabel("Number 1:"));
    add(numberOneTF);
    add(new JLabel("Number 2:"));
    add(numberTwoTF);
    add(new JLabel("Number 3:"));
    add(numberThreeTF);
    add(computeBtn);
    add(new JLabel("The Result:"));
    add(resultTF);

    // show the window
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }
  public static void main(String[] args)
  {
    new GCD();
  }
}
