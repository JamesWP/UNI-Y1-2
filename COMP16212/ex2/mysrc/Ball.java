import java.awt.*;

public class Ball
{
    private final int value;
    private final Color colour;
    private final BallImage image;
    
    public Ball(final int value, final Color colour) {
        super();
        this.value = value;
        this.colour = colour;
        this.image = this.makeImage();
    }
    
    public BallImage makeImage() {
        return new BallImage(this);
    }
    
    public int getValue() {
        return this.value;
    }
    
    public Color getColour() {
        return this.colour;
    }
    
    public BallImage getImage() {
        return this.image;
    }
    
    public int compareTo(final Ball ball) {
        return this.value - ball.value;
    }
    
    public void flash(final int n, final int n2) {
        this.image.flash(n, n2);
    }
    
    public String toString() {
        return "Ball " + this.value + " " + this.colour;
    }
}
