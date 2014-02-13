package robot;

import javax.swing.*;
import java.awt.*;

public class RobotOrientationGraph extends JFrame implements Runnable
{
    private static final int winXLocation = 0;
    private static final int winYLocation = 510;
    private static final int xBorder = 55;
    private static final int yBorder = 55;
    private static final int ySize = 150;
    private static final int hScale = 4;
    private static final int winXSize = 510;
    private static final int winYSize = 260;
    private static final int xOff = 35;
    private static final int yOff = 30;
    private RobotBeliefState rbs;
    private WorldMap map;
    private Thread orientationThread;
    
    public RobotOrientationGraph(final RobotBeliefState rbs) {
        super();
        this.rbs = rbs;
        this.setTitle("Robot Orientation Graph");
        this.setLayout(new FlowLayout());
        this.getContentPane().setBackground(Color.pink);
        this.setDefaultCloseOperation(3);
        this.setSize(510, 260);
        this.setLocation(0, 510);
        this.setVisible(true);
        (this.orientationThread = new Thread(this)).start();
    }
    
    public void paint(final Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        final double n = 150.0 / this.rbs.getMaxOrientationProbability();
        final int n2 = 100;
        g.drawLine(35, 230, 435, 230);
        g.drawLine(35, 230, 35, 80);
        for (int i = 0; i < 20; ++i) {
            g.drawLine(35 + i * 5 * 4, 230, 35 + i * 5 * 4, 235);
        }
        for (int j = 0; j < 5; ++j) {
            g.drawLine(35 + j * 25 * 4, 230, 35 + j * 25 * 4, 240);
            g.drawString("" + j * 25, 35 + j * 25 * 4, 250);
        }
        for (int k = 0; k < 5; ++k) {
            g.drawLine(35, 230 - k * 150 / 4, 25, 230 - k * 150 / 4);
            g.drawString("" + k, 15, 230 - k * 150 / 4);
        }
        g.drawString("Probability (units of Maxprob/4)", 35, 45);
        g.drawString("Angle", 440, 230);
        g.drawString("Maxprob=" + this.rbs.getMaxOrientationProbability(), 35, 60);
        int n3 = (int)(this.rbs.getOrientationProbability(0) * n);
        int l;
        for (l = 0; l < n2 - 1; ++l) {
            final int n4 = (int)(this.rbs.getOrientationProbability(l + 1) * n);
            g.drawLine(35 + l * 4, 260 - n3 - 30, 35 + (l + 1) * 4, 260 - n4 - 30);
            n3 = n4;
        }
        g.drawLine(35 + l * 4, 260 - n3 - 30, 35 + (l + 1) * 4, 260 - (int)(this.rbs.getOrientationProbability(0) * n) - 30);
    }
    
    public void run() {
        while (true) {
            this.repaint();
            try {
                final Thread orientationThread = this.orientationThread;
                Thread.sleep(100L);
            }
            catch (InterruptedException ex) {}
        }
    }
}
