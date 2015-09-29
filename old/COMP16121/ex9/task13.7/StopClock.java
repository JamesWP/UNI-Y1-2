import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;



import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * a program to simulate a stopwatch
 */
public class StopClock extends JFrame
{
  private final JLabel startedAtLabel = new JLabel("not started");
  private final JLabel finishedAtLabel = new JLabel("not started");
  private final JLabel elapsedTimeLabel = new JLabel("not started");
  private final JLabel splitTimeLabel = new JLabel("not started");
  private final JLabel statusLabel = new JLabel("Not started");
  private final JButton startStopButton = new JButton("Start / Stop");
  private final JButton splitButton = new JButton("Split");

  private boolean isRunning = false;
  private long startTime = 0L;
  private long endTime = 0L;
  private long splitTime = -1L;

  /**
   * constructs a StopClock and displays it on the screen
   */
  public StopClock ()
  {
    // assign event listeners
    startStopButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent actionEvent)
      {
        if(!isRunning)
        {
          // start the clock
          startTime = System.currentTimeMillis();
          splitTime = -1L;
        }else{
          // stop the clock
          endTime = System.currentTimeMillis();
        }
        isRunning = !isRunning;
        updateDisplay();
      }
    });

    splitButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent actionEvent)
      {
        if(isRunning){
          splitTime = System.currentTimeMillis();
          updateDisplay();
        }
      }
    });

    // instantiate layout
    setLayout(new GridLayout(0,2));
    
    //// old code
    //add(new JLabel("Started at:"));
    //add(startedAtLabel);
    //add(new JLabel("Finished at:"));
    //add(finishedAtLabel);
    
    add(new JLabel("Status:"));
    add(statusLabel);
    add(new JLabel("Split Time (seconds):"));    
    add(splitTimeLabel);
    add(new JLabel("Elapsed Time (seconds):"));
    add(elapsedTimeLabel);
    add(startStopButton);
    add(splitButton);

    // display window
    pack();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  /**
   * starts the program
   */
  public static void main(String[] args)
  {
    new StopClock();
  }

  /**
   * updates the display of the program
   */
  private void updateDisplay()
  {
    startedAtLabel.setText("" + startTime);
    if(isRunning){
      statusLabel.setText("Running...");
      elapsedTimeLabel.setText("Running...");
      finishedAtLabel.setText("");
    }else{
      statusLabel.setText("Stopped");
      finishedAtLabel.setText("" + endTime);
      elapsedTimeLabel.setText("" + ((endTime - startTime) / 1000));
    }
    if(splitTime != -1L)
    {
      splitTimeLabel.setText("" + ((splitTime - startTime) / 1000));
    }else{
      splitTimeLabel.setText("no split");
    }
  }
}
