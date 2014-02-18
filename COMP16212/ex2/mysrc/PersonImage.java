import java.awt.*;
import java.awt.event.*;

public class PersonImage extends Component implements MouseListener
{
    private Person person;
    private SpeedController speedController;
    private boolean showClassHierarchy;
    private boolean showSpeech;
    
    public PersonImage(final Person person) {
        super();
        this.showClassHierarchy = false;
        this.showSpeech = false;
        this.person = person;
        this.addMouseListener(this);
    }
    
    public void setSpeedController(final SpeedController speedController) {
        this.speedController = speedController;
    }
    
    public void paint(final Graphics graphics) {
        graphics.setColor(this.person.getColour());
        graphics.fillOval(0, 0, 60, 60);
        graphics.setColor(Color.black);
        graphics.fillOval(10, 20, 10, 10);
        graphics.fillOval(40, 20, 10, 10);
        graphics.setColor(Color.white);
        graphics.drawOval(0, 0, 60, 60);
        graphics.drawOval(1, 1, 58, 58);
        graphics.drawOval(2, 2, 57, 57);
        graphics.fillOval(14, 22, 4, 4);
        graphics.fillOval(44, 22, 4, 4);
        graphics.setColor(Color.black);
        graphics.drawLine(30, 25, 35, 35);
        graphics.drawLine(35, 35, 30, 35);
        if (this.person.isHappy()) {
            graphics.drawArc(10, 10, 40, 40, -30, -80);
        }
        else {
            graphics.drawArc(20, 40, 60, 60, 100, 40);
        }
        if (this.showSpeech) {
            graphics.setColor(Color.white);
        }
        else {
            graphics.setColor(Color.lightGray);
        }
        graphics.fillOval(60, 0, 330, 60);
        graphics.fillPolygon(new int[] { 45, 65, 65 }, new int[] { 45, 25, 35 }, 3);
        graphics.setColor(Color.black);
        graphics.drawString(this.person.getLatestSaying(), 65, 35);
        if (this.showClassHierarchy) {
            graphics.drawString(this.person.getClassHierarchy(), 0, 70);
        }
        else {
            graphics.drawString(this.person.getPersonType() + ": \"" + this.person.getPersonName() + "\"", 0, 70);
        }
    }
    
    public void update() {
        this.repaint(0L);
    }
    
    public void flash(final int n, final int n2) {
        for (int i = 0; i < n; ++i) {
            this.showSpeech = true;
            this.repaint();
            if (this.speedController != null) {
                this.speedController.delay(n2);
            }
            this.showSpeech = false;
            this.repaint();
            if (this.speedController != null) {
                this.speedController.delay(n2);
            }
        }
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(430, 75);
    }
    
    public void mouseClicked(final MouseEvent mouseEvent) {
    }
    
    public void mousePressed(final MouseEvent mouseEvent) {
    }
    
    public void mouseReleased(final MouseEvent mouseEvent) {
    }
    
    public void mouseEntered(final MouseEvent mouseEvent) {
        this.showClassHierarchy = true;
        this.repaint();
    }
    
    public void mouseExited(final MouseEvent mouseEvent) {
        this.showClassHierarchy = false;
        this.repaint();
    }
}
