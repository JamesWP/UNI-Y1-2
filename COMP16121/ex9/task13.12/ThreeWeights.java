import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ThreeWeights extends JFrame
{
  private JTextField weight1TF = new JTextField(20);
  private JTextField weight2TF = new JTextField(20);
  private JTextField weight3TF = new JTextField(20);
  private JTextArea resultTA = new JTextArea(15,20);
  private JButton computeButton = new JButton("Compute");

  public ThreeWeights()
  {
    // add event listeners
    computeButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent actionEvent)
      {
        double weight1 = Double.parseDouble(weight1TF.getText());
        double weight2 = Double.parseDouble(weight2TF.getText());
        double weight3 = Double.parseDouble(weight3TF.getText());

        resultTA.setText("");
        for(int w1Co = -1;w1Co<=1;w1Co++)
          for(int w2Co = -1;w2Co<=1;w2Co++)
            for(int w3Co = -1;w3Co<=1;w3Co++)
              resultTA.append(weight1 *w1Co + weight2 *w2Co + weight3 *w3Co 
                             +"\n"); 
      }
    });

    // add components to layout
    setLayout(new BorderLayout(10,10));
    JPanel inputsPane = new JPanel(new GridLayout(0,2));
    add(inputsPane,BorderLayout.NORTH);
    inputsPane.add(new JLabel("Weight One:"));
    inputsPane.add(weight1TF);
    inputsPane.add(new JLabel("Weight Two:"));
    inputsPane.add(weight2TF);
    inputsPane.add(new JLabel("Weight Three:"));
    inputsPane.add(weight3TF);
    add(computeButton);
    add(new JScrollPane(resultTA),BorderLayout.SOUTH);
    // display window
    pack();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String[] args)
  {
    new ThreeWeights();
  }
}