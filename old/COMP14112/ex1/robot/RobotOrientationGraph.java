package robot;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;

public class RobotOrientationGraph extends JFrame implements Runnable{
  /**
     Display the probability map for the robot's position in isometric
     projection, with controls for navigation.      
  */

  /*
    For natigation purposes, we imagine a system of (x,y,z)
    coordinates centred in the middle of the window, corresponding to
    the position in the middle of the world.
  */


  // Constatnts for window dimensions
  // Scaling needed so we can see grid
  // RunRobot.SIZE by RunRobot.SIZE a border

  private static final int winXLocation= 0;
  private static final int winYLocation= 510;
  private static final int xBorder= 55;
  private static final int yBorder= 55;
  private static final int ySize= 150;     // Height of drawing area
  private static final int hScale= 4;      // Horizontal scale for display
  private static final int winXSize= RunRobot.SIZE*hScale+2*xBorder;
  private static final int winYSize= ySize+2*yBorder;
  private static final int xOff= 35;
  private static final int yOff= 30;

    // Non-graphical member variables

  private RobotBeliefState rbs;
  private WorldMap map;

  private Thread orientationThread;

  public RobotOrientationGraph(RobotBeliefState r){

    rbs= r;

    setTitle("Robot Orientation Graph");
    setLayout(new FlowLayout());

    getContentPane().setBackground(Color.pink);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setSize(winXSize,winYSize);
    setLocation(winXLocation,winYLocation);
    setVisible(true);

    orientationThread= new Thread(this);
    orientationThread.start();

  }


  public void paint(Graphics g){
    super.paint(g);

    g.setColor(Color.black);

    double vScale= ((double) ySize)/rbs.getMaxOrientationProbability(); 

    int numSquares= RunRobot.SIZE;   // abbreviation (and it may change ...)
    int x;
    int y;
    int nextY;

    // Draw axes
    g.drawLine(xOff,winYSize-yOff,xOff+RunRobot.SIZE*hScale,winYSize-yOff);
    g.drawLine(xOff,winYSize-yOff,xOff,winYSize-yOff-ySize);

    // Draw little itty-bitty things on x-axis
    for(int i= 0;i<RunRobot.SIZE/5; i++)
      g.drawLine(xOff+i*5*hScale,winYSize-yOff,xOff+i*5*hScale,winYSize-yOff+5);
    for(int i= 0;i<RunRobot.SIZE/25 +1; i++){
      g.drawLine(xOff+i*25*hScale,winYSize-yOff,xOff+i*25*hScale,winYSize-yOff+10);
      g.drawString(""+(i*25),xOff+i*25*hScale,winYSize-yOff+20);
    }

    // Draw little itty-bitty things on y-axis
    for(int i= 0;i<5; i++){
      g.drawLine(xOff,winYSize-yOff-i*ySize/4,xOff-10,winYSize-yOff-i*ySize/4);
      g.drawString(""+i,
		   xOff-20,winYSize-yOff-i*ySize/4);
    }

    // Draw lables
    g.drawString("Probability (units of Maxprob/4)",xOff,45);
    g.drawString("Angle",winXSize-2*xOff,winYSize-yOff);
    g.drawString("Maxprob="+rbs.getMaxOrientationProbability(),xOff, 60);

    // Draw graph
    y= (int) (rbs.getOrientationProbability(0)*vScale);
    for(x= 0;x< numSquares-1;x++){
      nextY= (int) (rbs.getOrientationProbability(x+1)*vScale);
      g.drawLine(xOff+x*hScale,winYSize-y-yOff,xOff+(x+1)*hScale,winYSize-nextY-yOff);
      y= nextY;
    }    
    nextY= (int) (rbs.getOrientationProbability(0)*vScale);
    g.drawLine(xOff+x*hScale,winYSize-y-yOff,xOff+(x+1)*hScale,winYSize-nextY-yOff);
  }


  public void run()
  {
    while(true){
	repaint();
	try{
	    orientationThread.sleep(100);
	}
	catch(InterruptedException e){
	}
    }
  }



}
