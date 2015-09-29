package javagently;
import java.awt.*;
import java.awt.event.*;

public class Graph extends Frame {

  /*  Graph      by J M Bishop   June 1999
   *********
   is a simple class that provides facilities for 
      - storing points
      - printing them as a dot or line graph
      - printing the graph.
      Several graphs can be printed in one window.
      The axes labels are worked out from the points themselves.
    Assistance given by J Lo, I de Villiers and B Worrall
  
  Interface:
  
  Basic
  -----
  new Graph ()  Compulsory constructor call
  add(x, y)     Adds a point to a list. Expects points to come in x-order.
  showGraph()   Compulsory call to get the axes and graph drawn
  
  Advanced
  --------
  new Graph (graphTitle, xAxisTitle, yAxisTitle)
                         Version of the constructor with labelling options.
                         Use empty strings if not all titles applicable
  nextGraph()            Starts a new graph on the same axes.
                         One showGraph call can be used for all the graphs.
  setColor(int 0 to 3)   Choice of black, magenta, blue, red
                         (constants are available instead of numbers)
  setSymbol(boolean)     Deduced from the colour
                         (Convenient if colours are being set)
  setSymbol(int 0 to 3)  circle, upside down triangle, triangle, square
                         (Used if colours are not being set)
  setLine(boolean)       Normally on, can be turned off
                         (Remember to turn it on again for the next line)
  setTitle(String)       Will appear on a key alongside the symbol and/or
                         in the chosen colour
  */
  
  private String xAxisTitle, yAxisTitle, graphTitle;
  private boolean keys;
  
  public Graph () {
//-------------
    /* the simple constructor -
       leaves the window and axes titles blank */
    initializeGraph();
    xAxisTitle = "";
    yAxisTitle = "";
    graphTitle = "";
  }
  
  public Graph (String g, String x, String y) {
//------------
    /* the alternative constructor -
       has window and axes titles */
    initializeGraph();
    graphTitle = g;
    xAxisTitle = x;
    yAxisTitle = y;
  }
  
  private class Dataset {
//---------------------
    /* Simple class for recording basic info
  about a set of points.  Allows more than one
  graph to be drawn on an axis. 
  All initialisation done in nextGraph. */
    int count;
    int plotType;
    String title;
    boolean colorRequired, symbolRequired, titleRequired, lineRequired;
  }
  
  private List points;
  private List datasets;
  
  private void initializeGraph () {
    datasets = new List ();
    nextGraph();
    points = new List ();
    xMax = yMax = Double.MIN_VALUE;
    xMin = yMin = Double.MAX_VALUE;
    keys = false;
    setSize (640,480);
    super.setTitle ("Graph");
    addWindowListener (new WindowAdapter () {
      public void windowClosing(WindowEvent e) {
        dispose();
      }
    });
  }
  
  public void nextGraph () {
//---------------------
    Dataset d = new Dataset ();
    d.count = 0;
    d.plotType = black;
    d.title = "";
    d.symbolRequired = false;
    d.colorRequired = false;
    d.titleRequired = false;
    d.lineRequired = true;
    datasets.add (d);
  }

  public void setColor (int c) {
//--------------------
    ((Dataset) datasets.current()).colorRequired = true;
    ((Dataset) datasets.current()).plotType = c;
  }
  
  public void setSymbol (boolean b) {
//---------------------
    ((Dataset) datasets.current()).symbolRequired = b;
  }

  public void setSymbol (int c) {
//---------------------
    ((Dataset) datasets.current()).symbolRequired = true;
    ((Dataset) datasets.current()).plotType = c;
  }

  public void setTitle (String s) {
//--------------------
    ((Dataset) datasets.current()).titleRequired = true;
    ((Dataset) datasets.current()).title = s;
  keys = true;
  }
    
  public void setLine (boolean b) {
//-------------------
    ((Dataset) datasets.current()).lineRequired = b;
    if (b==false)  
      ((Dataset) datasets.current()).symbolRequired = true;
  }
  
  public void setLimits (double xMin, double xMax, double yMin, double yMax) {
      this.xMax = xMax;
      this.xMin = xMin;
      this.yMax = yMax;
      this.yMin = yMin;
  }

      public void add (double x, double y) {
//---------------
    points.add (new Point (x,y));  
    ((Dataset)datasets.current()).count++;
    if (x > xMax) xMax = x;
    if (x < xMin) xMin = x;
    if (y > yMax) yMax = y;
    if (y < yMin) yMin = y;
  }

  public void showGraph () {
//---------------------
    repaint();
    setVisible(true);
  }
  
 private class Point {
//------------------
    /* simple class for an x,y coordinate */
    double xCoord, yCoord;
    Point (double x, double y) {
      xCoord = x;
      yCoord = y;
    }
  }
   
  private void drawTitles (Graphics g) {
//-----------------------
    Dataset d;
    int x = xBorder;
    int y = getHeight()-yBorder/2;
    datasets.reset();
    boolean lastset = datasets.eol();
    while (!lastset) {
      d = (Dataset) datasets.current();
      if (d.colorRequired) 
        changeColor(g, d.plotType);
      if (d.symbolRequired) {
        drawSymbol(g, d.plotType, x, y-cs);
        x += 4*cs;
      }
      g.drawString(d.title, x, y);
        x += g.getFontMetrics().stringWidth(d.title)+20;
      lastset = datasets.eol();
      if (!lastset) datasets.succ();
    }
    g.setColor(Color.black);
  }
  
  private void drawAxes (Graphics g) {
//---------------------
    Font plain = g.getFont();
    Font small = new Font(plain.getFamily(),Font.PLAIN,10);
    Font bold = new Font(plain.getFamily(),Font.BOLD,14);
        
    g.drawLine(xBorder-5,yOrigin,xAxisLength+xBorder+5,yOrigin);
    g.drawLine(xOrigin,yBorder-5,xOrigin,yAxisLength+yBorder+5);
    g.drawString(xAxisTitle,
               getWidth()-g.getFontMetrics().stringWidth(xAxisTitle)-xBorder/2,
             yOrigin-5);
    g.drawString(yAxisTitle,
                 xOrigin-g.getFontMetrics().stringWidth(yAxisTitle)/2,
             yBorder-8);
             
    g.setFont(bold);
    g.drawString(graphTitle,
                (getWidth()-g.getFontMetrics().stringWidth(graphTitle))/2,
                 yBorder/2);
    g.setFont(plain);
    if (keys) drawTitles(g);
      
    // Tick and Label the four min/max points only
    int scaleXMin = scaleX(xMin);
    int scaleXMax = scaleX(xMax);
    int scaleYMin = scaleY(yMin);
    int scaleYMax = scaleY(yMax);
    g.drawLine(xOrigin-5,scaleYMax,xOrigin+5,scaleYMax);
    g.drawLine(xOrigin-5,scaleYMin,xOrigin+5,scaleYMin);
    g.drawLine(scaleXMax,yOrigin+5,scaleXMax,yOrigin);
    g.drawLine(scaleXMin,yOrigin+5,scaleXMin,yOrigin);
      
    g.setFont(small);
    g.drawString(Text.writeDouble(xMin,6,5),scaleXMin-10,yOrigin+15);
    g.drawString(Text.writeDouble(xMax,6,5),scaleXMax-10,yOrigin+15);
    g.drawString(Text.writeDouble(yMin,6,5),xOrigin-35,scaleYMin+4);
    g.drawString(Text.writeDouble(yMax,6,5),xOrigin-35,scaleYMax+4);
    g.setFont(plain);
  }
   
  private double xSpread, ySpread, xMin, xMax, yMin, yMax;
  private int xAxisLength, yAxisLength, xOrigin, yOrigin;
  private int xBorder, yBorder;
  
  public void paint (Graphics g) {
//-----------------
    // calulate length of axes from window size minus a border of 20
    xBorder = 40;
    yBorder = 80;
  
    xAxisLength = (int) getWidth() - 2*xBorder;
    yAxisLength = (int) getHeight() - 2*yBorder;
 
    // calculate value spreads from mins and maxs which have 
    // been recorded as we go
    xSpread = xMax - xMin;
    ySpread = yMax - yMin;
    if (xMin > 0) xOrigin = scaleX(xMin); else xOrigin = scaleX(0);
    if (yMin > 0) yOrigin = scaleY(yMin); else yOrigin = scaleY(0);
  
    drawAxes(g);
    plotGraphs(g); 
  }
  
  private int scaleX(double x) {
//------------------
    return (int) ((x-xMin) / xSpread*xAxisLength)  + xBorder ;
  }
  
  private int scaleY(double y) {
//------------------
    return (int) (yAxisLength - ((y-yMin) / 
                  ySpread*yAxisLength)) + yBorder;
  }
  
  private void changeColor(Graphics g, int c) {
//------------------------
    switch (c) {
      case black : {g.setColor(Color.black); break;}
      case magenta : {g.setColor(Color.magenta); break;}
      case blue :  {g.setColor(Color.blue); break;}
      case red :   {g.setColor(Color.red); break;}
    }
  }
 private static final int cs = 3; // pixel size of a symbol

  private void drawSymbol (Graphics g, int sy, int x, int y) {
//-----------------------
    switch (sy) {
      case black : {g.drawOval (x-cs, y-cs, 2*cs, 2*cs); break;}
      case magenta : {g.drawPolygon (trix(x),uptriy(y), 3); break;}
      case blue :  {g.drawPolygon (trix(x),triy(y), 3); break;}
      case red :   {g.drawRect (x-cs, y-cs, 2*cs, 2*cs); break;}
    }
  }
  
  private void plotGraphs (Graphics g) {
//-----------------------
    Point p, q;
    int x1, y1, x2, y2;
    Dataset d;
    boolean lastset;
    Color c;
  
    // Loop through each dataset
    datasets.reset();
    /* The points are in one big list, split by the
    counts recorded in each dataset */
    points.reset();
    lastset = datasets.eol();
  
    do {
      d = (Dataset) datasets.current();
      if (d.colorRequired) changeColor(g, d.plotType);
      // Start with the first point in the list
      // for this graph
      p = (Point) points.current();
      x1 = scaleX(p.xCoord);
      y1 = scaleY(p.yCoord);
      if (d.symbolRequired) 
        drawSymbol(g, d.plotType, x1, y1);
      
      // Loop through the points as stored in the list
      for (int i=1; i<d.count; i++) {
        points.succ();
        q = (Point) points.current();
        x2 = scaleX(q.xCoord);
        y2 = scaleY(q.yCoord);
    
        // plot the line and/or point
        if (d.lineRequired)
          g.drawLine(x1, y1, x2, y2);
        if (d.symbolRequired) 
          drawSymbol(g, d.plotType, x2, y2);
        x1 = x2; y1 = y2;
      }
      lastset = datasets.eol();
      if (!lastset) {
        datasets.succ();
        points.succ();
      }
    } while (!lastset);
  }
  
  private int [] trix (int p) {
//-------------------
    int [] a = new int [3];
    a[0] = p-cs;  
    a[1] = p+cs; 
    a[2] = p;    
    return a;
  }
  
  private int [] triy (int p) {
//-------------------
    int [] a = new int [3];
    a[0] = p+cs;  
    a[1] = p+cs; 
    a[2] = p-cs;  
    return a;
  }
  
  private int [] uptriy (int p) {
//---------------------
    int [] a = new int [3];
    a[0] = p-cs;  
    a[1] = p-cs; 
    a[2] = p+cs;  
    return a;
  }
    
  public final int red = 3;
  public final int blue = 2;
  public final int magenta = 1;
  public final int black = 0;
  
}
    
