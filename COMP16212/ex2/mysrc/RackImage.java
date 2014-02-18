import java.awt.*;

public class RackImage extends BallContainerImage
{
    public RackImage(final Rack rack) {
        super(rack);
        this.setBackground(Color.white);
        this.setForeground(Color.black);
    }
    
    public LayoutManager makeBallsPanelLayout() {
        return new FlowLayout(0, 0, 0);
    }
    
    public Dimension getPreferredSizeOfBallPanel() {
        return new Dimension(this.getBallContainer().getMaximumSize() * 50, 50);
    }
}
