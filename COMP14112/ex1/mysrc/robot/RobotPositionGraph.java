package robot;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;

public class RobotPositionGraph extends JFrame implements Runnable, ActionListener, KeyListener
{
    private static final int winXLocation = 530;
    private static final int winYLocation = 0;
    private static final int xBorder = 25;
    private static final int yBorder = 35;
    private static final double scale = 5.0;
    private static final int winXSize = 750;
    private static final int winYSize = 770;
    private static final int xOff = 375;
    private static final int yOff = 410;
    private JButton dolphinButton;
    private JButton animateButton;
    private Container cp;
    private double theta;
    private double deltaTheta;
    private double phi;
    private double deltaPhi;
    private int thetaCounter;
    private int phiCounter;
    private double verticalStretch;
    private Boolean animationOn;
    private int skip;
    private RobotBeliefState rbs;
    private WorldMap map;
    private Thread animationThread;
    
    public RobotPositionGraph(final RobotBeliefState rbs, final WorldMap map) {
        super();
        this.dolphinButton = new JButton(" Dophin (un)friendly ");
        this.animateButton = new JButton(" Toggle animation ");
        this.cp = this.getContentPane();
        this.theta = 0.523598776;
        this.deltaTheta = 0.01745329;
        this.phi = 0.34906584999999996;
        this.deltaPhi = 0.01745329;
        this.thetaCounter = 60;
        this.phiCounter = 20;
        this.verticalStretch = 200.0;
        this.animationOn = false;
        this.skip = 3;
        this.rbs = rbs;
        this.map = map;
        this.setTitle("Robot Position Graph");
        this.setLayout(new FlowLayout());
        this.add(this.dolphinButton);
        this.add(this.animateButton);
        this.dolphinButton.setBorder(new BevelBorder(1));
        this.animateButton.setBorder(new BevelBorder(1));
        this.cp.setBackground(Color.yellow);
        this.dolphinButton.addActionListener(this);
        this.animateButton.addActionListener(this);
        this.dolphinButton.addKeyListener(this);
        this.animateButton.addKeyListener(this);
        this.addKeyListener(this);
        this.setDefaultCloseOperation(3);
        this.setLocation(530, 0);
        this.setSize(750, 770);
        this.setVisible(true);
        this.createBufferStrategy(2);
        (this.animationThread = new Thread(this)).start();
    }
    
    public void paint(final Graphics g) {
        super.paint(g);
        this.drawstuff();
    }
    
    public void actionPerformed(final ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.dolphinButton) {
            if (this.skip == 1) {
                this.skip = 3;
            }
            else {
                this.skip = 1;
            }
            this.drawstuff();
        }
        if (actionEvent.getSource() == this.animateButton) {
            this.animationOn = !this.animationOn;
        }
    }
    
    public void keyPressed(final KeyEvent keyEvent) {
        final int keyCode = keyEvent.getKeyCode();
        if (keyCode == 38) {
            this.phi = Math.min(this.phi + this.deltaPhi, 1.5707963267948966);
            this.drawstuff();
        }
        if (keyCode == 37) {
            this.theta += this.deltaTheta;
            this.drawstuff();
        }
        if (keyCode == 39) {
            this.theta -= this.deltaTheta;
            this.drawstuff();
        }
        if (keyCode == 40) {
            this.phi = Math.max(this.phi - this.deltaPhi, 0.0);
            this.drawstuff();
        }
    }
    
    public void keyReleased(final KeyEvent keyEvent) {
    }
    
    public void keyTyped(final KeyEvent keyEvent) {
    }
    
    public void run() {
        while (true) {
            if (this.animationOn) {
                this.theta += this.deltaTheta;
                this.drawstuff();
                try {
                    final Thread animationThread = this.animationThread;
                    Thread.sleep(20L);
                }
                catch (InterruptedException ex) {}
            }
        }
    }
    
    private void drawstuff() {
        this.cp.setBackground(Color.yellow);
        Graphics drawGraphics = null;
        final BufferStrategy bufferStrategy = this.getBufferStrategy();
        try {
            drawGraphics = bufferStrategy.getDrawGraphics();
            drawGraphics.setColor(Color.yellow);
            drawGraphics.fillRect(0, 0, 750, 770);
            drawGraphics.setColor(Color.black);
            drawGraphics.drawString("Maxprob=" + this.rbs.getMaxPositionProbability(), 50, 80);
            drawGraphics.drawString("Isometric projection", 50, 690);
            double n;
            if (this.rbs.getMaxPositionProbability() == 0.0) {
                n = 0.0;
            }
            else {
                n = this.verticalStretch / this.rbs.getMaxPositionProbability();
            }
            final int n2 = 100;
            final double cos = Math.cos(this.phi);
            final double n3 = 5.0 * Math.cos(this.theta);
            final double n4 = -5.0 * Math.sin(this.theta) * Math.sin(this.phi);
            final double n5 = 5.0 * Math.sin(this.theta);
            final double n6 = 5.0 * Math.cos(this.theta) * Math.sin(this.phi);
            double n7 = -(n3 + n5) * (n2 / 2);
            double n8 = -(n4 + n6) * (n2 / 2);
            for (int i = 0; i < n2; i += this.skip) {
                double n9 = n7;
                double n10 = n8;
                for (int j = 0; j < n2 - this.skip; j += this.skip) {
                    final double n11 = n9 + n5 * this.skip;
                    final double n12 = n10 + n6 * this.skip;
                    if (this.map.isOccupied(i, j) && this.map.isOccupied(i, j + this.skip)) {
                        drawGraphics.setColor(Color.red);
                    }
                    else {
                        drawGraphics.setColor(Color.black);
                    }
                    drawGraphics.drawLine(375 + (int)n9, 410 - (int)(n10 + cos * this.rbs.getPositionProbability(i, j) * n), 375 + (int)n11, 410 - (int)(n12 + cos * this.rbs.getPositionProbability(i, j + this.skip) * n));
                    n9 = n11;
                    n10 = n12;
                }
                n7 += n3 * this.skip;
                n8 += n4 * this.skip;
            }
            double n13 = -(n3 + n5) * (n2 / 2);
            double n14 = -(n4 + n6) * (n2 / 2);
            for (int k = 0; k < n2; k += this.skip) {
                double n15 = n13;
                double n16 = n14;
                for (int l = 0; l < n2 - this.skip; l += this.skip) {
                    final double n17 = n15 + n3 * this.skip;
                    final double n18 = n16 + n4 * this.skip;
                    if (this.map.isOccupied(l, k) && this.map.isOccupied(l + this.skip, k)) {
                        drawGraphics.setColor(Color.red);
                    }
                    else {
                        drawGraphics.setColor(Color.black);
                    }
                    drawGraphics.drawLine(375 + (int)n15, 410 - (int)(n16 + cos * this.rbs.getPositionProbability(l, k) * n), 375 + (int)n17, 410 - (int)(n18 + cos * this.rbs.getPositionProbability(l + this.skip, k) * n));
                    n15 = n17;
                    n16 = n18;
                }
                n13 += n5 * this.skip;
                n14 += n6 * this.skip;
            }
            drawGraphics.setColor(Color.red);
            drawGraphics.drawLine(375 - (int)((n3 + n5) * n2 / 2.0), 410 + (int)((n4 + n6) * n2 / 2.0), 375 - (int)((n3 + n5) * n2 / 2.0), 410 + (int)((n4 + n6) * n2 / 2.0) - (int)(cos * this.verticalStretch));
            drawGraphics.drawLine(375 + (int)((n3 + n5) * n2 / 2.0), 410 - (int)((n4 + n6) * n2 / 2.0), 375 + (int)((n3 + n5) * n2 / 2.0), 410 - (int)((n4 + n6) * n2 / 2.0) - (int)(cos * this.verticalStretch));
            drawGraphics.drawLine(375 - (int)((n3 - n5) * n2 / 2.0), 410 + (int)((n4 - n6) * n2 / 2.0), 375 - (int)((n3 - n5) * n2 / 2.0), 410 + (int)((n4 - n6) * n2 / 2.0) - (int)(cos * this.verticalStretch));
            drawGraphics.drawLine(375 - (int)((-n3 + n5) * n2 / 2.0), 410 + (int)((-n4 + n6) * n2 / 2.0), 375 - (int)((-n3 + n5) * n2 / 2.0), 410 + (int)((-n4 + n6) * n2 / 2.0) - (int)(cos * this.verticalStretch));
            for (int n19 = 0; n19 < 2; ++n19) {
                drawGraphics.drawLine(375 - (int)((n3 + n5) * n2 / 2.0), 410 + (int)((n4 + n6) * n2 / 2.0) - n19 * (int)(cos * this.verticalStretch), 375 + (int)((-n3 + n5) * n2 / 2.0), 410 - (int)((-n4 + n6) * n2 / 2.0) - n19 * (int)(cos * this.verticalStretch));
                drawGraphics.drawLine(375 - (int)((n3 + n5) * n2 / 2.0), 410 + (int)((n4 + n6) * n2 / 2.0) - n19 * (int)(cos * this.verticalStretch), 375 + (int)((n3 - n5) * n2 / 2.0), 410 - (int)((n4 - n6) * n2 / 2.0) - n19 * (int)(cos * this.verticalStretch));
                drawGraphics.drawLine(375 + (int)((n3 - n5) * n2 / 2.0), 410 - (int)((n4 - n6) * n2 / 2.0) - n19 * (int)(cos * this.verticalStretch), 375 + (int)((n3 + n5) * n2 / 2.0), 410 - (int)((n4 + n6) * n2 / 2.0) - n19 * (int)(cos * this.verticalStretch));
                drawGraphics.drawLine(375 + (int)((n3 + n5) * n2 / 2.0), 410 - (int)((n4 + n6) * n2 / 2.0) - n19 * (int)(cos * this.verticalStretch), 375 + (int)((-n3 + n5) * n2 / 2.0), 410 - (int)((-n4 + n6) * n2 / 2.0) - n19 * (int)(cos * this.verticalStretch));
            }
        }
        finally {
            drawGraphics.dispose();
        }
        bufferStrategy.show();
        this.dolphinButton.repaint();
        this.animateButton.repaint();
        Toolkit.getDefaultToolkit().sync();
    }
}
