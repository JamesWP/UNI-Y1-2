package robot;

public class Pose
{
    public int x;
    public int y;
    public int theta;
    
    public Pose(final int x, final int y, final int theta) {
        super();
        this.x = x;
        this.y = y;
        this.theta = theta;
    }
    
    public Pose() {
        super();
        this.x = 0;
        this.y = 0;
        this.theta = 0;
    }
}
