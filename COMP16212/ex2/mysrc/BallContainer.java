public abstract class BallContainer
{
    private final String name;
    private Ball[] balls;
    private int noOfBalls;
    private final BallContainerImage image;
    
    public BallContainer(final String name, final int n) {
        super();
        this.name = name;
        this.balls = new Ball[n];
        this.noOfBalls = 0;
        this.image = this.makeImage();
    }
    
    public BallContainerImage makeImage() {
        return new BallContainerImage(this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public abstract String getType();
    
    public Ball getBall(final int n) {
        if (n >= 0 && n < this.noOfBalls) {
            return this.balls[n];
        }
        return null;
    }
    
    public int getNoOfBalls() {
        return this.noOfBalls;
    }
    
    public int getMaximumSize() {
        return this.balls.length;
    }
    
    public BallContainerImage getImage() {
        return this.image;
    }
    
    public void addBall(final Ball ball) {
        if (this.noOfBalls < this.balls.length) {
            this.balls[this.noOfBalls] = ball;
            ++this.noOfBalls;
            this.image.update();
            ball.flash(1, 1);
        }
    }
    
    public void swapBalls(final int n, final int n2) {
        if (n >= 0 && n < this.noOfBalls && n2 >= 0 && n2 < this.noOfBalls) {
            this.balls[n].flash(1, 1);
            this.balls[n2].flash(1, 1);
            final Ball ball = this.balls[n];
            this.balls[n] = this.balls[n2];
            this.balls[n2] = ball;
            this.image.update();
            this.balls[n].flash(1, 1);
            this.balls[n2].flash(1, 1);
        }
    }
    
    public void removeBall() {
        if (this.noOfBalls > 0) {
            this.balls[this.noOfBalls - 1].flash(1, 1);
            --this.noOfBalls;
            this.image.update();
        }
    }
    
    public String toString() {
        String str = this.getType() + " " + this.name + "(<=" + this.balls.length + ")";
        for (int i = 0; i < this.noOfBalls; ++i) {
            str = str + "\n" + i + " " + this.balls[i];
        }
        return str;
    }
}
