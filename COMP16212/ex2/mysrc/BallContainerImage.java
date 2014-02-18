import java.awt.*;

public class BallContainerImage extends Panel
{
    private BallContainer ballContainer;
    private Panel ballsPanel;
    private SpeedController speedController;
    
    public BallContainerImage(final BallContainer ballContainer) {
        super();
        this.ballsPanel = new BallsPanel();
        this.ballContainer = ballContainer;
        this.setLayout(new BorderLayout(0, 0));
        this.add(new Label(this.ballContainer.getType() + ": \"" + this.ballContainer.getName() + "\""), "North");
        this.add(this.ballsPanel, "South");
        this.ballsPanel.setLayout(this.makeBallsPanelLayout());
    }
    
    public LayoutManager makeBallsPanelLayout() {
        return new FlowLayout();
    }
    
    public void setSpeedController(final SpeedController speedController) {
        this.speedController = speedController;
    }
    
    public void doLayout() {
        this.ballsPanel.doLayout();
        super.doLayout();
    }
    
    public void update() {
        this.ballsPanel.removeAll();
        for (int i = 0; i < this.ballContainer.getNoOfBalls(); ++i) {
            final BallImage image = this.ballContainer.getBall(i).getImage();
            this.ballsPanel.add(image);
            image.setSpeedController(this.speedController);
        }
        this.doLayout();
        this.ballsPanel.repaint();
    }
    
    public BallContainer getBallContainer() {
        return this.ballContainer;
    }
    
    public Dimension getPreferredSizeOfBallPanel() {
        return new Dimension(50, 50);
    }
    
    private class BallsPanel extends BufferedPanel
    {
        public Dimension getPreferredSize() {
            return BallContainerImage.this.getPreferredSizeOfBallPanel();
        }
    }
}
