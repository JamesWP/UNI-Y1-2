package robot;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;

import java.awt.image.BufferStrategy; // Maybe this will work
import javax.swing.JFrame;


public class RobotPositionGraph extends JFrame 
                implements Runnable, ActionListener, KeyListener{
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

  private static final int winXLocation= 530;
  private static final int winYLocation= 0;
  private static final int xBorder= 25;
  private static final int yBorder= 35;
  private static final double scale= 5;      // Horizontal scale for display
  private static final int winXSize= 
    RunRobot.SIZE*((int) (1.5 * scale))+2*xBorder;
  private static final int winYSize= 
    RunRobot.SIZE*((int) (1.5 * scale))+2*yBorder;
  private static final int xOff= winXSize/2;
  private static final int yOff= winYSize/2+25;

  
  //Graphical member variables
  private JButton dolphinButton= new JButton(" Dophin (un)friendly "); 
  private JButton animateButton= new JButton(" Toggle animation "); 

  private Container cp= getContentPane();

  // Graphical control variables
  private double theta= .523598776;
  private double deltaTheta= 0.01745329;
  private double phi= 1.04719755/3;
  private double deltaPhi= 0.01745329;
  private int thetaCounter= 60;
  private int phiCounter= 20;
  private double verticalStretch= 200;
  private Boolean animationOn= false; 

  private int skip= 3;           // Display every other point in probability grid
                                 // Assume is is either 1 or 3

  // Non-graphical member variables

  private RobotBeliefState rbs;
  private WorldMap map;

  private Thread animationThread;

  public RobotPositionGraph(RobotBeliefState r, WorldMap m){

    rbs= r;
    map= m;

    setTitle("Robot Position Graph");
    setLayout(new FlowLayout());
    add(dolphinButton);
    add(animateButton);

    dolphinButton.setBorder(new BevelBorder(BevelBorder.LOWERED));
    animateButton.setBorder(new BevelBorder(BevelBorder.LOWERED));

    cp.setBackground(Color.yellow);

    dolphinButton.addActionListener(this);
    animateButton.addActionListener(this);

    dolphinButton.addKeyListener(this);
    animateButton.addKeyListener(this);
    addKeyListener(this);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setLocation(winXLocation,winYLocation);
    setSize(winXSize,winYSize);
    //    setUndecorated(true);
    setVisible(true);
    createBufferStrategy(2);

    animationThread= new Thread(this);
    animationThread.start();
  }


  public void paint(Graphics g){
    super.paint(g);
    drawstuff();
  }

  public void actionPerformed(ActionEvent e){
    
    if(e.getSource() == dolphinButton){
      if(skip == 1)
	skip= 3;
      else
	skip= 1;
      drawstuff();
      //      repaint();
    }
    if(e.getSource() == animateButton){
	animationOn= !animationOn;
    }
  }

  public void keyPressed(KeyEvent e){

    int KeyPress= e.getKeyCode();
    if(KeyPress== KeyEvent.VK_UP){
      phi=Math.min(phi+deltaPhi,Math.PI/2);
      drawstuff();
      //      repaint();
    }
    if(KeyPress== KeyEvent.VK_LEFT){
      theta+=deltaTheta;
      drawstuff();
      //      repaint();
    }
    if(KeyPress== KeyEvent.VK_RIGHT){
      theta-=deltaTheta;
      drawstuff();
      //      repaint();
    }
    if(KeyPress== KeyEvent.VK_DOWN){
      phi=Math.max(phi-deltaPhi,0);
      drawstuff();
      //      repaint();
    }
  }


  public void keyReleased(KeyEvent e){}
  public void keyTyped(KeyEvent e){}


  public void run()
  {
    while(true){
      if(animationOn){
	theta+=deltaTheta;
	drawstuff();
	try{
	  animationThread.sleep(20);
	}
	catch(InterruptedException e){
	}
      }
    }
  }

  private void drawstuff(){
    
    cp.setBackground(Color.yellow);
    Graphics g = null;
    BufferStrategy bf = this.getBufferStrategy();
    
    try {
      g = bf.getDrawGraphics();
      
      // It is assumed that mySprite is created somewhere else.
      // This is just an example for passing off the Graphics object.

      g.setColor(Color.yellow);
      g.fillRect(0,0,winXSize,winYSize);
      g.setColor(Color.black);
      
      g.drawString("Maxprob="+rbs.getMaxPositionProbability(),50,80);
      
      g.drawString("Isometric projection",50,winYSize-80);
      
      double vScale;
      if(rbs.getMaxPositionProbability()==0)vScale=0;
      else vScale = verticalStretch/rbs.getMaxPositionProbability(); 
                                                  // Dynamic vertical scale

      int numSquares= RunRobot.SIZE;   // abbreviation (and it may change ...)
      
      double xDeltaX, xDeltaY, yDeltaX, yDeltaY,
             xCurrentX, xCurrentY, yCurrentX, yCurrentY,
	     nextX, nextY, elev;

      elev= Math.cos(phi);

      // Calculate x-axis and y-axis steps
      /*
	X = xcos(theta) + y*sin(theta)
	Y = (-x*sin(theta)+y*cos(theta)*sin(phi) + z*cos(phi)
      */

      // Change in (X,Y) screen coordinates as you move along x axis in grid
      xDeltaX= scale*Math.cos(theta);
      xDeltaY= -scale*Math.sin(theta)*Math.sin(phi);
      // Change in (X,Y) screen coordinates as you move along y axis in grid
      yDeltaX= scale*Math.sin(theta);
      yDeltaY= scale*Math.cos(theta)*Math.sin(phi);
      
      
      // Draw x-lines
      // Base (X,Y) coords as you move along x axis in grid
      xCurrentX=  -(xDeltaX+yDeltaX)*(numSquares/2);
      xCurrentY=  -(xDeltaY+yDeltaY)*(numSquares/2);
      
      for(int i= 0;i< numSquares;i+=skip)  // Move along x-axis in grid
	{
	  // Base (X,Y) coords as you move along y axis in grid
	  yCurrentX=  xCurrentX;
	  yCurrentY=  xCurrentY;
	  for(int j= 0;j< numSquares-skip;j+=skip){   
            // Move along y-axis in grid
	    nextX= yCurrentX+yDeltaX*((double) skip);
	    nextY= yCurrentY+yDeltaY*((double) skip);
	    if(map.isOccupied(i,j) && map.isOccupied(i,j+skip))
	      g.setColor(Color.red);
	    else g.setColor(Color.black); 
	    g.drawLine(xOff+ (int) yCurrentX, 
		       yOff-  (int) (yCurrentY +
                                     elev*
                                      rbs.getPositionProbability(i,j)*vScale),
		       xOff + (int) nextX, 
		       yOff - (int) (nextY +
                                     elev*
                                      rbs.getPositionProbability(i,j+skip)*
                                        vScale));
	    yCurrentX= nextX; 
	    yCurrentY= nextY;
	  }
	  xCurrentX+= xDeltaX*((double) skip);  // Move along x-axis in grid
	  xCurrentY+= xDeltaY*((double) skip);
	}
      
      // Draw y-lines
      // Base (X,Y) coords as you move along y axis in grid
      yCurrentX=  -(xDeltaX+yDeltaX)*(numSquares/2);
      yCurrentY=  -(xDeltaY+yDeltaY)*(numSquares/2);
      
      for(int j= 0;j< numSquares;j+=skip)  // Move along y-axis in grid
	{
	  // Base (X,Y) coords as you move along x axis in grid
	  xCurrentX=  yCurrentX;
	  xCurrentY=  yCurrentY;
	  for(int i= 0;i< numSquares-skip;i+=skip){   
                                                  // Move along x-axis in grid
	    nextX= xCurrentX+xDeltaX*((double) skip);
	    nextY= xCurrentY+xDeltaY*((double) skip);
	    if(map.isOccupied(i,j) && map.isOccupied(i+skip,j))
	      g.setColor(Color.red);
	    else g.setColor(Color.black);
	    g.drawLine(xOff + (int) (xCurrentX), 
		       yOff - (int) (xCurrentY+
                                     elev*rbs.getPositionProbability(i,j)*
                                      vScale),
		       xOff + (int) (nextX), 
		       yOff - (int) (nextY+
                                     elev*rbs.getPositionProbability(i+skip,j)*
                                      vScale));
	    xCurrentX= nextX;
	    xCurrentY= nextY;
	  }
	  yCurrentX+= yDeltaX*((double) skip);  // Move along y-axis in grid
	  yCurrentY+= yDeltaY*((double) skip);
	}
      
      
      // Draw standard  axes
      g.setColor(Color.red);
      
      
      g.drawLine(xOff-  (int) ((xDeltaX+yDeltaX)*numSquares/2),    
                                                             // Draw Z-axis
		 yOff+  (int) ((xDeltaY+yDeltaY)*numSquares/2),
		 xOff-  (int) ((xDeltaX+yDeltaX)*numSquares/2),						 
		 yOff+  (int) ((xDeltaY+yDeltaY)*numSquares/2) - 
                        (int) (elev*verticalStretch));  
      
      g.drawLine(xOff+  (int) ((xDeltaX+yDeltaX)*numSquares/2),    
                                                             // Draw Z-axis
		 yOff-  (int) ((xDeltaY+yDeltaY)*numSquares/2),
		 xOff+  (int) ((xDeltaX+yDeltaX)*numSquares/2),						 
		 yOff-  (int) ((xDeltaY+yDeltaY)*numSquares/2) - 
                              (int) (elev*verticalStretch)); 
      
      g.drawLine(xOff-  (int) ((xDeltaX-yDeltaX)*numSquares/2),    
                                                             // Draw Z-axis
		 yOff+  (int) ((xDeltaY-yDeltaY)*numSquares/2),
		 xOff-  (int) ((xDeltaX-yDeltaX)*numSquares/2),						 
		 yOff+  (int) ((xDeltaY-yDeltaY)*numSquares/2) - (int) (elev*verticalStretch));  
      
      g.drawLine(xOff-  (int) ((-xDeltaX+yDeltaX)*numSquares/2),    
                                                              // Draw Z-axis
		 yOff+  (int) ((-xDeltaY+yDeltaY)*numSquares/2),
		 xOff-  (int) ((-xDeltaX+yDeltaX)*numSquares/2),						 
		 yOff+  (int) ((-xDeltaY+yDeltaY)*numSquares/2) - 
                        (int) (elev*verticalStretch));  
      
      for(int h= 0;h<2;h++){
	g.drawLine(xOff-  (int) ((xDeltaX+yDeltaX)*numSquares/2),    
                                                               // Draw X-axis
		   yOff+  (int) ((xDeltaY+yDeltaY)*numSquares/2) - 
                          h*((int) (elev*verticalStretch)),
		   xOff+  (int) ((-xDeltaX+yDeltaX)*numSquares/2),						 
		   yOff-  (int) ((-xDeltaY+yDeltaY)*numSquares/2) - 
                          h*((int) (elev*verticalStretch))); 
	g.drawLine(xOff-  (int) ((xDeltaX+yDeltaX)*numSquares/2),    
                                                               // Draw Y-axis
		   yOff+  (int) ((xDeltaY+yDeltaY)*numSquares/2) - 
                          h*((int) (elev*verticalStretch)),
		   xOff+  (int) ((xDeltaX-yDeltaX)*numSquares/2),
		   yOff-  (int) ((xDeltaY-yDeltaY)*numSquares/2) - 
                          h*((int) (elev*verticalStretch))); 
	g.drawLine(xOff+  (int) ((xDeltaX-yDeltaX)*numSquares/2),    
                                                    // Draw X-axis offset base
		   yOff-  (int) ((xDeltaY-yDeltaY)*numSquares/2) - 
                          h*((int) (elev*verticalStretch)),
		   xOff+  (int) ((xDeltaX+yDeltaX)*numSquares/2),		                   yOff-  (int) ((xDeltaY+yDeltaY)*numSquares/2) - 
                          h*((int) (elev*verticalStretch))); 
	g.drawLine(xOff+  (int) ((xDeltaX+yDeltaX)*numSquares/2),    
                                                    // Draw Y-axis offset base
		   yOff-  (int) ((xDeltaY+yDeltaY)*numSquares/2) - 
                          h*((int) (elev*verticalStretch)),
		   xOff+  (int) ((-xDeltaX+yDeltaX)*numSquares/2),						 
		   yOff-  (int) ((-xDeltaY+yDeltaY)*numSquares/2) - 
		   h*((int) (elev*verticalStretch)));  
      }
    }
    
    finally {
      // It is best to dispose() a Graphics object when done with it.
      g.dispose();
    }
    
    bf.show();
    dolphinButton.repaint();
    animateButton.repaint();


    // Tell the System to do the Drawing now, 
    // otherwise it can take a few extra ms until 
    // Drawing is done which looks very jerky
    Toolkit.getDefaultToolkit().sync();	
    
  }


}
