import java.awt.*;

public class MachineImage extends BallContainerImage
{
    private int ballsPerSide;
    
    public MachineImage(final Machine machine) {
        super(machine);
        this.setBackground(Color.black);
        this.setForeground(Color.white);
    }
    
    public LayoutManager makeBallsPanelLayout() {
        this.ballsPerSide = (int)Math.round(Math.sqrt(this.getBallContainer().getMaximumSize()));
        return new GridLayout(0, this.ballsPerSide, 0, 0);
    }
    
    public Dimension getPreferredSizeOfBallPanel() {
        return new Dimension(this.ballsPerSide * 50, this.ballsPerSide * 50);
    }
}
