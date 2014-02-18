public class Rack extends BallContainer
{
    public Rack(final String s, final int n) {
        super(s, n);
    }
    
    public BallContainerImage makeImage() {
        return new RackImage(this);
    }
    
    public String getType() {
        return "Landing rack";
    }
    
    public void sortBalls() {
        for (int i = 0; i < this.getNoOfBalls() - 1; ++i) {
            for (int j = 0; j < this.getNoOfBalls() - i - 1; ++j) {
                this.getBall(j).flash(1, 1);
                this.getBall(j + 1).flash(1, 1);
                if (this.getBall(j).compareTo(this.getBall(j + 1)) > 0) {
                    for (int k = i; k <= 4; ++k) {
                        this.getBall(j).flash(1, 4);
                        this.getBall(j + 1).flash(1, 4);
                    }
                    this.swapBalls(j, j + 1);
                }
            }
        }
    }
    
    public boolean contains(final int n) {
        boolean b = false;
        for (int n2 = 0; !b && n2 < this.getNoOfBalls(); b = (this.getBall(n2).getValue() == n), ++n2) {}
        return b;
    }
}
