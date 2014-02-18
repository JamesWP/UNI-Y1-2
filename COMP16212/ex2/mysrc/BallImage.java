import java.awt.*;

public class BallImage extends Component
{
    private Ball ball;
    private Color colour;
    private Color textColour;
    public static final int BALL_SIZE = 50;
    private SpeedController speedController;
    
    public BallImage(final Ball ball) {
        super();
        this.ball = ball;
        this.colour = this.ball.getColour();
        this.textColour = this.getTextColour();
    }
    
    public void setSpeedController(final SpeedController speedController) {
        this.speedController = speedController;
    }
    
    public Color getTextColour() {
        return Color.black;
    }
    
    public void drawBallShape(final Graphics graphics) {
        graphics.setColor(this.colour);
        graphics.fillOval(0, 0, 50, 50);
        graphics.setColor(Color.white);
        graphics.fillOval(7, 7, 10, 10);
    }
    
    public void labelBallShape(final Graphics graphics) {
        graphics.setColor(this.textColour);
        graphics.drawString("" + this.ball.getValue(), 15, 30);
    }
    
    public void paint(final Graphics graphics) {
        this.drawBallShape(graphics);
        this.labelBallShape(graphics);
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }
    
    public void update() {
        this.repaint(0L);
    }
    
    public void flash(final int n, final int n2) {
        for (int i = 1; i <= n * 2; ++i) {
            final Color colour = this.colour;
            this.colour = this.textColour;
            this.textColour = colour;
            this.repaint();
            if (this.speedController != null) {
                this.speedController.delay(n2);
            }
        }
    }
}
