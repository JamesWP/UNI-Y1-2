public class Machine extends BallContainer
{
    public Machine(final String s, final int n) {
        super(s, n);
    }
    
    public BallContainerImage makeImage() {
        return new MachineImage(this);
    }
    
    public String getType() {
        return "Lottery machine";
    }
    
    public Ball ejectBall() {
        if (this.getNoOfBalls() <= 0) {
            return null;
        }
        final int n = (int)(Math.random() * this.getNoOfBalls());
        final Ball ball = this.getBall(n);
        ball.flash(4, 5);
        this.swapBalls(n, this.getNoOfBalls() - 1);
        this.removeBall();
        return ball;
    }
}
