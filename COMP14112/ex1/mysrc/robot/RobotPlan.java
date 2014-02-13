package robot;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public final class RobotPlan extends JFrame implements ActionListener, Runnable
{
    private Scenario scenario;
    private RobotBeliefState robotBeliefState;
    private RobotPositionGraph robotPositionGraph;
    private RobotOrientationGraph robotOrientationGraph;
    private String messageString;
    private ProbActionToggler probActionToggler;
    private Thread planThread;
    private static final int winXLocation = 0;
    private static final int winYLocation = 0;
    private static final int xOffset = 155;
    private static final int yOffset = 150;
    private static final int yUpperMargin = 157;
    private static final int displayScale = 2;
    private static final int winXSize = 510;
    private static final int winYSize = 507;
    private static final int robotGraphicSize = 5;
    private static final double directionGraphicSize = 8.0;
    private Color mainColour;
    private JPanel actionPanel;
    private JPanel sensorPanel;
    private JPanel controlPanel;
    private JButton goForward;
    private JButton turnLeft;
    private JButton turnRight;
    private JButton pollForward;
    private JButton pollLeft;
    private JButton pollRight;
    private JButton resetSimulator;
    private JButton exitSimulator;
    private JButton probActionToggle;
    
    public RobotPlan(final Scenario scenario, final RobotBeliefState robotBeliefState, final RobotPositionGraph robotPositionGraph, final RobotOrientationGraph robotOrientationGraph, final ProbActionToggler probActionToggler) {
        super();
        this.messageString = "";
        this.mainColour = Color.lightGray;
        this.actionPanel = new JPanel();
        this.sensorPanel = new JPanel();
        this.controlPanel = new JPanel();
        this.goForward = new JButton(" Forward 10 ");
        this.turnLeft = new JButton(" Left 10 ");
        this.turnRight = new JButton(" Right 10 ");
        this.pollForward = new JButton(" ForwardSensor ");
        this.pollLeft = new JButton(" Left Sensor ");
        this.pollRight = new JButton(" Right Sensor ");
        this.resetSimulator = new JButton(" Reset Simulator ");
        this.exitSimulator = new JButton(" Exit Simulator ");
        this.probActionToggle = new JButton(" Toggle Prob. Actions ");
        this.scenario = scenario;
        this.robotBeliefState = robotBeliefState;
        this.robotPositionGraph = robotPositionGraph;
        this.robotOrientationGraph = robotOrientationGraph;
        this.probActionToggler = probActionToggler;
        this.setTitle("Robot Plan");
        this.setLayout(new FlowLayout());
        this.actionPanel.setBackground(this.mainColour);
        this.sensorPanel.setBackground(this.mainColour);
        this.controlPanel.setBackground(this.mainColour);
        this.actionPanel.add(this.goForward);
        this.actionPanel.add(this.turnLeft);
        this.actionPanel.add(this.turnRight);
        this.sensorPanel.add(this.pollForward);
        this.sensorPanel.add(this.pollLeft);
        this.sensorPanel.add(this.pollRight);
        this.controlPanel.add(this.probActionToggle);
        this.controlPanel.add(this.resetSimulator);
        this.controlPanel.add(this.exitSimulator);
        this.add(this.actionPanel);
        this.add(this.sensorPanel);
        this.add(this.controlPanel);
        this.goForward.addActionListener(this);
        this.turnLeft.addActionListener(this);
        this.turnRight.addActionListener(this);
        this.pollForward.addActionListener(this);
        this.pollLeft.addActionListener(this);
        this.pollRight.addActionListener(this);
        this.resetSimulator.addActionListener(this);
        this.exitSimulator.addActionListener(this);
        this.probActionToggle.addActionListener(this);
        this.setDefaultCloseOperation(3);
        this.setSize(510, 507);
        this.setLocation(0, 0);
        this.getContentPane().setBackground(this.mainColour);
        this.setVisible(true);
        (this.planThread = new Thread(this)).start();
    }
    
    public void paint(final Graphics g) {
        super.paint(g);
        g.drawString(this.robotBeliefState.statusString, 20, 170);
        g.drawString(this.messageString, 300, 457);
        if (this.probActionToggler.probActions()) {
            g.drawString("Actions are PROBABILISTIC   ", 20, 427);
        }
        else {
            g.drawString("Actions are DETERMINISTIC", 20, 427);
        }
        final int x = this.scenario.robotPose.x;
        final int y = this.scenario.robotPose.y;
        final int theta = this.scenario.robotPose.theta;
        final double n = 6.283185307179586 * theta / 100.0;
        g.drawRect(154, 156, 200, 200);
        for (final WorldObject worldObject : this.scenario.map.objects) {
            g.drawRect(worldObject.xLL * 2 + 155, 507 - worldObject.yUR * 2 - 150, (worldObject.xUR - worldObject.xLL) * 2, (worldObject.yUR - worldObject.yLL) * 2);
        }
        g.setColor(Color.red);
        g.drawOval((x - 5) * 2 + 155, 507 - (y + 5) * 2 - 150, 20, 20);
        g.drawLine(x * 2 + 155, 507 - y * 2 - 150, (x + (int)(8.0 * Math.cos(n))) * 2 + 155, 357 - (y - (int)(8.0 * Math.sin(n))) * 2);
        g.setColor(Color.black);
        g.drawString("Robot Position Data", 20, 447);
        g.drawString("x-coord: " + x, 40, 462);
        g.drawString("y-coord: " + y, 40, 477);
        g.drawString("orientation: " + theta, 40, 492);
    }
    
    public void actionPerformed(final ActionEvent actionEvent) {
        final int x = this.scenario.robotPose.x;
        final int y = this.scenario.robotPose.y;
        final int theta = this.scenario.robotPose.theta;
        Action action = new Action();
        final Action action2 = new Action();
        if (actionEvent.getSource() == this.goForward) {
            action.type = ActionType.GO_FORWARD;
            action.parameter = 10;
            this.scenario.executeAction(action);
            this.robotBeliefState.updateProbabilityOnAction(action);
            this.repaint();
            this.robotPositionGraph.repaint();
            this.robotOrientationGraph.repaint();
        }
        if (actionEvent.getSource() == this.turnLeft) {
            action = new Action();
            action.type = ActionType.TURN_LEFT;
            action.parameter = 10;
            this.scenario.executeAction(action);
            this.robotBeliefState.updateProbabilityOnAction(action);
            this.repaint();
            this.robotPositionGraph.repaint();
            this.robotOrientationGraph.repaint();
        }
        if (actionEvent.getSource() == this.turnRight) {
            action.type = ActionType.TURN_RIGHT;
            action.parameter = 10;
            this.scenario.executeAction(action);
            this.robotBeliefState.updateProbabilityOnAction(action);
            this.repaint();
            this.robotPositionGraph.repaint();
            this.robotOrientationGraph.repaint();
        }
        if (actionEvent.getSource() == this.pollForward) {
            final Observation pollSensor = this.scenario.pollSensor(SensorType.FORWARD_SENSOR);
            this.messageString = "Forward sensor reads: " + pollSensor.reading;
            this.robotBeliefState.updateProbabilityOnObservation(pollSensor);
            this.robotPositionGraph.repaint();
            this.robotOrientationGraph.repaint();
            this.repaint();
        }
        if (actionEvent.getSource() == this.pollLeft) {
            final Observation pollSensor2 = this.scenario.pollSensor(SensorType.LEFT_SENSOR);
            this.messageString = "Left sensor reads: " + pollSensor2.reading;
            this.robotBeliefState.updateProbabilityOnObservation(pollSensor2);
            this.robotPositionGraph.repaint();
            this.robotOrientationGraph.repaint();
            this.repaint();
        }
        if (actionEvent.getSource() == this.pollRight) {
            final Observation pollSensor3 = this.scenario.pollSensor(SensorType.RIGHT_SENSOR);
            this.messageString = "Right sensor reads: " + pollSensor3.reading;
            this.robotBeliefState.updateProbabilityOnObservation(pollSensor3);
            this.robotPositionGraph.repaint();
            this.robotOrientationGraph.repaint();
            this.repaint();
        }
        if (actionEvent.getSource() == this.resetSimulator) {
            this.scenario.robotPose = new Pose(50, 50, 0);
            if (this.probActionToggler.probActions()) {
                this.probActionToggler.toggle();
            }
            this.repaint();
            this.robotBeliefState.initializeMatrices();
            this.robotPositionGraph.repaint();
            this.robotOrientationGraph.repaint();
        }
        if (actionEvent.getSource() == this.probActionToggle) {
            this.probActionToggler.toggle();
            this.repaint();
        }
        if (actionEvent.getSource() == this.exitSimulator) {
            System.exit(0);
        }
    }
    
    public void run() {
        while (true) {
            this.repaint();
            try {
                final Thread planThread = this.planThread;
                Thread.sleep(100L);
            }
            catch (InterruptedException ex) {}
        }
    }
}
