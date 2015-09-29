package robot;

import java.util.*;
import java.lang.Math.*;


public class WorldObject{

  /**
     Object in the simulated environment---always a rectangle
  
  */

  // Basic geometry. Public because drawing methods need access.

  public int xLL; // x-cordinete of lower left point
  public int yLL; // y-cordinete of lower left point
  public int xUR; // x-cordinete of upper right point
  public int yUR; // y-cordinete of upper right point


  public WorldObject(int x1, int y1, int x2, int y2){

    xLL= x1;
    yLL= y1;
    xUR= x2;
    yUR= y2;
  }

  public boolean isIn(int x, int y){
    /**
       Returns true if the square (x,y) is occupied by This.
    */
    return (x >= xLL && x <= xUR && y >= yLL && y <= yUR);
  }

  public double getInterceptDistance(int x, int y, int t){
    /**
       Return length of ray starting at (x,y) with orientation psi
       (measured anticlockwise from x-axis) to This. If the ray does
       not intersect This, return -1. Assume: (i) 0 <= x <
       GRANULARITY, (ii) 0 <= y < GRANULARITY, (iii) 0 <= psi <
       GRANULARITY.
    */

    // integer-angle to angle conversion
    double psi= 2*Math.PI*((double) t)/((double) RunRobot.SIZE);

    double xIntercept;
    double yIntercept;

    double xD= (double) x;
    double yD= (double) y;
    double xLLD= (double) xLL-0.2;  // Add `margin' to ensure intercept is not at integer
    double yLLD= (double) yLL-0.2;
    double xURD= (double) xUR+0.2;
    double yURD= (double) yUR+0.2;
    double sizeD= (double) RunRobot.SIZE;

    // If the square is occupied distance to object is 0
    if(isIn(x,y))
      return 0;

    // Trap out special (axis-oriented) cases
    if(t == 0 && x < xLL && yLL <= y && y <= yUR)
      return xLLD-xD;
    if(t == RunRobot.SIZE/4 && y > yUR && xLL <= x && x <= yUR)
      return yD-yURD;
    if(t == RunRobot.SIZE/2 && x > xUR && yLL <= y && y <= yUR)
      return xD-xURD;
    if(t == 3*RunRobot.SIZE/4 && y < yLL && xLL <= x && x <= xUR)
      return yLLD - yD;

    // Compute intercept with left-hand vertical line
    if((t < RunRobot.SIZE/4 || t > 3*RunRobot.SIZE/4) && x < xLL){
      yIntercept= yD - (xLLD -xD)*Math.tan(psi);
      if(yLLD <= yIntercept && yIntercept <= yURD)
	return (xLLD - xD)/Math.cos(psi);
    }
    // Compute intercept with top horizontal line
    if(t < RunRobot.SIZE/2 && y > yUR){
      xIntercept= xD + (yD- yURD)/Math.tan(psi);
      if(xLLD <= xIntercept && xIntercept <= xURD)
	return (yD-yURD)/Math.sin(psi);
    }
    // Compute intercept with right-hand vertical line
    if((t > RunRobot.SIZE/4 && t < 3*RunRobot.SIZE/4) && x > xUR){
      yIntercept= yD + (xD-xURD)*Math.tan(psi);
      if(yLLD <= yIntercept && yIntercept <= yURD)
	return -(xD-xURD)/Math.cos(psi);
    }
    // Compute intercept with bottom horizontal line
    if(t > RunRobot.SIZE/2 && y < yLL){
      xIntercept= xD - (yLLD-yD)/Math.tan(psi);
      if(xLLD <= xIntercept && xIntercept <= xURD)
	return -(yLLD-yD)/Math.sin(psi);
    }
    return -1; // No intercept found
  }
}
