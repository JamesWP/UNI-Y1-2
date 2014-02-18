import java.awt.*;
import java.awt.event.*;

public class SpeedControllerGUI extends Frame implements ActionListener
{
    private SpeedController speedController;
    private Label speedIndicatorLabel;
    private Label pausedIndicatorLabel;
    
    public SpeedControllerGUI(final SpeedController speedController) {
        super();
        this.speedController = speedController;
        this.setTitle("Speed controller");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                SpeedControllerGUI.this.setVisible(false);
            }
        });
        this.setLayout(new GridLayout(3, 0));
        this.add(new Label("Speed controller"));
        final Button comp = new Button("Hide");
        comp.setActionCommand("close");
        comp.addActionListener(this);
        this.add(comp);
        this.add(new Label(""));
        this.add(this.speedIndicatorLabel = new Label());
        final Button comp2 = new Button("Slower");
        comp2.setActionCommand("slower");
        comp2.addActionListener(this);
        this.add(comp2);
        final Button comp3 = new Button("Faster");
        comp3.setActionCommand("faster");
        comp3.addActionListener(this);
        this.add(comp3);
        this.add(this.pausedIndicatorLabel = new Label());
        final Button comp4 = new Button("Pause");
        comp4.setActionCommand("pause");
        comp4.addActionListener(this);
        this.add(comp4);
        final Button comp5 = new Button("Resume");
        comp5.setActionCommand("resume");
        comp5.addActionListener(this);
        this.add(comp5);
        this.update();
        this.pack();
    }
    
    public void actionPerformed(final ActionEvent actionEvent) {
        final String actionCommand = actionEvent.getActionCommand();
        if (actionCommand.equals("slower")) {
            this.speedController.slowDown();
        }
        else if (actionCommand.equals("faster")) {
            this.speedController.speedUp();
        }
        else if (actionCommand.equals("pause")) {
            this.speedController.pause();
        }
        else if (actionCommand.equals("resume")) {
            this.speedController.resume();
        }
        else if (actionCommand.equals("close")) {
            this.setVisible(false);
        }
    }
    
    public void update() {
        this.speedIndicatorLabel.setText("Current speed " + this.speedController.getCurrentSpeed());
        if (this.speedController.getPaused()) {
            this.pausedIndicatorLabel.setText("Simulation is PAUSED");
        }
        else {
            this.pausedIndicatorLabel.setText("Simulation is RUNNING");
        }
    }
}
