package robot;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public final class RobotPlan extends JFrame implements ActionListener, Runnable{

  /**
     The central area of the screen is used to display the Scenario
     scenario. The coordinates of scenario.map are mapped to screen
     coordinates with (0,0) being the bottom left-hand corner.
  */

  // Non-graphical constants
  private Scenario scenario;
  private RobotBeliefState robotBeliefState;
  private RobotPositionGraph robotPositionGraph;
  private RobotOrientationGraph robotOrientationGraph;
  private String messageString= "";
  private ProbActionToggler probActionToggler;

  private Thread planThread;

  // Constatnts for window dimensions
  // There is currently no scaling: the active drawing are is
  // RunRobot.SIZE by RunRobot.SIZE a border

  private static final int winXLocation= 0;
  private static final int winYLocation= 0;
  private static final int xOffset= 155;
  private static final int yOffset= 150;
  private static final int yUpperMargin= 157;
  private static final int displayScale= 2;
  private static final int winXSize= RunRobot.SIZE*displayScale+2*xOffset;
  private static final int winYSize= RunRobot.SIZE*displayScale+yOffset+yUpperMargin;
  private static final int robotGraphicSize= 5;
  private static final double directionGraphicSize= 8;
  
  //Graphical member variables

  private Color mainColour= Color.lightGray;

  private JPanel actionPanel= new JPanel();
  private JPanel sensorPanel= new JPanel();
  private JPanel controlPanel= new JPanel();

  private JButton goForward= new JButton(" Forward 10 ");
  private JButton turnLeft= new JButton(" Left 10 ");
  private JButton turnRight= new JButton(" Right 10 ");

  private JButton pollForward= new JButton(" ForwardSensor ");
  private JButton pollLeft= new JButton(" Left Sensor ");
  private JButton pollRight= new JButton(" Right Sensor ");

  private JButton resetSimulator= new JButton(" Reset Simulator ");
  private JButton exitSimulator= new JButton(" Exit Simulator ");

  private JButton probActionToggle= new JButton(" Toggle Prob. Actions ");

  public RobotPlan(Scenario s, RobotBeliefState r, 
		   RobotPositionGraph pg, RobotOrientationGraph og,
                   ProbActionToggler probActionToggler1){

    // This graphical class contains the controls, so it has to have access to 
    // pretty well everything.

    scenario= s;
    robotBeliefState= r;
    robotPositionGraph= pg;
    robotOrientationGraph= og;
    probActionToggler= probActionToggler1;

    setTitle("Robot Plan");
    setLayout(new FlowLayout());
 
    actionPanel.setBackground(mainColour);
    sensorPanel.setBackground(mainColour);
    controlPanel.setBackground(mainColour);

    actionPanel.add(goForward);
    actionPanel.add(turnLeft);
    actionPanel.add(turnRight);
    sensorPanel.add(pollForward);
    sensorPanel.add(pollLeft);
    sensorPanel.add(pollRight);
    controlPanel.add(probActionToggle);
    controlPanel.add(resetSimulator);
    controlPanel.add(exitSimulator);

    add(actionPanel);
    add(sensorPanel);
    add(controlPanel);
    
    goForward.addActionListener(this);
    turnLeft.addActionListener(this);
    turnRight.addActionListener(this);
    pollForward.addActionListener(this);
    pollLeft.addActionListener(this);
    pollRight.addActionListener(this);
    resetSimulator.addActionListener(this);
    exitSimulator.addActionListener(this);
    probActionToggle.addActionListener(this);


    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(winXSize,winYSize);
    setLocation(winXLocation, winYLocation);
    getContentPane().setBackground(mainColour);
    setVisible(true);

    planThread= new Thread(this);
    planThread.start();

  }

  public void paint(Graphics g){
    super.paint(g);


    g.drawString(robotBeliefState.statusString, 20,170);
    g.drawString(messageString, 300,winYSize-50);
    if(probActionToggler.probActions())
	g.drawString("Actions are PROBABILISTIC   ", 20,winYSize -80);
    else
	g.drawString("Actions are DETERMINISTIC", 20,winYSize -80);

    int x= scenario.robotPose.x;
    int y= scenario.robotPose.y;
    int theta= scenario.robotPose.theta;
    double psi= 2*Math.PI*((double) theta)/((double) RunRobot.SIZE);

    // Draw background 
    g.drawRect(xOffset-1,winYSize-RunRobot.SIZE*displayScale-yOffset-1,
	       (RunRobot.SIZE-1)*displayScale+2,(RunRobot.SIZE-1)*displayScale+2);
    // Draw objects
    for(WorldObject o : scenario.map.objects)
      // Begin rectangle in upper left hand corner and get width and height
      g.drawRect(o.xLL*displayScale+xOffset, 
		 winYSize-o.yUR*displayScale-yOffset,
		 (o.xUR-o.xLL)*displayScale,
		 (o.yUR-o.yLL)*displayScale);
    // Draw robot (circle with line to indicate direction)
    g.setColor(Color.red);
    g.drawOval((x-robotGraphicSize)*displayScale+xOffset,
	       winYSize-((y+robotGraphicSize)*displayScale)-yOffset,
	       2*robotGraphicSize*displayScale,
	       2*robotGraphicSize*displayScale);
    g.drawLine(x*displayScale+xOffset,
	       winYSize-(y*displayScale)-yOffset,
	       (x + (int) (directionGraphicSize*Math.cos(psi)))*displayScale+xOffset,
	       winYSize - yOffset-
	       (y - (int) (directionGraphicSize*Math.sin(psi)))*displayScale);

    // Print data
    g.setColor(Color.black);

    g.drawString("Robot Position Data",20, winYSize -60);
    g.drawString("x-coord: "+x, 40, winYSize -45);
    g.drawString("y-coord: "+y, 40, winYSize -30);
    g.drawString("orientation: "+theta,40, winYSize -15);
  }

  public void actionPerformed(ActionEvent e){

    // Assemble the action to be performed
    Action a;
    Observation obs;
    int x= scenario.robotPose.x;
    int y= scenario.robotPose.y;
    int t= scenario.robotPose.theta;

    Action intendedAction= new Action();  // Work variable in case action button hit
    Action virtualAction=  new Action();  // Work variable in case action button hit

    if(e.getSource() == goForward){
      intendedAction.type= ActionType.GO_FORWARD;
      intendedAction.parameter= 10;
      // Probabilify action and execute
      scenario.executeAction(intendedAction); 
      robotBeliefState.updateProbabilityOnAction(intendedAction);//update probabilities
      repaint();                                             // Redraw everything
      robotPositionGraph.repaint();
      robotOrientationGraph.repaint();
    }
    if(e.getSource() == turnLeft){
      intendedAction= new Action();
      intendedAction.type= ActionType.TURN_LEFT;
      intendedAction.parameter= 10;
      scenario.executeAction(intendedAction);
      robotBeliefState.updateProbabilityOnAction(intendedAction);
      repaint();
      robotPositionGraph.repaint();
      robotOrientationGraph.repaint();
    }
    if(e.getSource() == turnRight){
      intendedAction.type= ActionType.TURN_RIGHT;
      intendedAction.parameter= 10;
      scenario.executeAction(intendedAction);
      robotBeliefState.updateProbabilityOnAction(intendedAction);
      repaint();
      robotPositionGraph.repaint();
      robotOrientationGraph.repaint();
    }
    if(e.getSource() == pollForward){
      obs= scenario.pollSensor(SensorType.FORWARD_SENSOR);
      messageString= "Forward sensor reads: "+obs.reading;
      robotBeliefState.updateProbabilityOnObservation(obs);
      robotPositionGraph.repaint();
      robotOrientationGraph.repaint();
      repaint();
    }
    if(e.getSource() == pollLeft){
      obs= scenario.pollSensor(SensorType.LEFT_SENSOR);
      messageString= "Left sensor reads: "+obs.reading;
      robotBeliefState.updateProbabilityOnObservation(obs);
      robotPositionGraph.repaint();
      robotOrientationGraph.repaint();
      repaint();
    }
    if(e.getSource() == pollRight){
      obs= scenario.pollSensor(SensorType.RIGHT_SENSOR);
      messageString= "Right sensor reads: "+obs.reading;
      robotBeliefState.updateProbabilityOnObservation(obs);
      robotPositionGraph.repaint();
      robotOrientationGraph.repaint();
      repaint();
    }
    if(e.getSource() == resetSimulator){

      // Make new pose for robot
      Pose p= new Pose(50, 50, 0); 

      // Make scernario using Map map and Pose pose
      scenario.robotPose= p;
      // Turn of probabilistic actions
      if (probActionToggler.probActions())
	  probActionToggler.toggle();
      repaint();
      // Reset probabilities
      robotBeliefState.initializeMatrices();
      robotPositionGraph.repaint();
      robotOrientationGraph.repaint();
    }
    if(e.getSource() == probActionToggle){
	// Toggle the flag which determines whether actions are probabilistic
	probActionToggler.toggle();
	repaint();
    }
    if(e.getSource() == exitSimulator){
	System.exit(0);
    }
  }

    public void run()
    {
	while(true){
	    repaint();
	    try{
		planThread.sleep(100);
	    }
	    catch(InterruptedException e){
	    }
	}
    }

}
