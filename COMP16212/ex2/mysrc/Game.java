public class Game
{
    private final Machine machine;
    private final Rack rack;
    
    public Game(final String s, final int n, final String s2, final int n2) {
        super();
        this.machine = this.makeMachine(s, n);
        this.rack = this.makeRack(s2, n2);
    }
    
    public Machine makeMachine(final String s, final int n) {
        return new Machine(s, n);
    }
    
    public Rack makeRack(final String s, final int n) {
        return new Rack(s, n);
    }
    
    public int getMachineSize() {
        return this.machine.getMaximumSize();
    }
    
    public int getRackSize() {
        return this.rack.getMaximumSize();
    }
    
    public int getRackNoOfBalls() {
        return this.rack.getNoOfBalls();
    }
    
    public BallContainerImage getMachineImage() {
        return this.machine.getImage();
    }
    
    public BallContainerImage getRackImage() {
        return this.rack.getImage();
    }
    
    public void machineAddBall(final Ball ball) {
        this.machine.addBall(ball);
    }
    
    public Ball ejectBall() {
        if (this.machine.getNoOfBalls() > 0 && this.rack.getNoOfBalls() < this.rack.getMaximumSize()) {
            final Ball ejectBall = this.machine.ejectBall();
            this.rack.addBall(ejectBall);
            return ejectBall;
        }
        return null;
    }
    
    public boolean rackContains(final int n) {
        return this.rack.contains(n);
    }
    
    public void rackSortBalls() {
        this.rack.sortBalls();
    }
    
    public String toString() {
        return this.machine + "\n" + this.rack;
    }
}
