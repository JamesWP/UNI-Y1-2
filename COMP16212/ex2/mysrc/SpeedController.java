public class SpeedController
{
    private static final int[] delayFactor;
    private static final int NO_OF_SPEEDS;
    public static final int MIN_SPEED = 0;
    public static final int HALF_SPEED;
    public static final int MAX_SPEED;
    private int currentSpeed;
    private boolean paused;
    private SpeedControllerGUI image;
    
    public SpeedController(final int currentSpeed) {
        super();
        this.paused = false;
        this.currentSpeed = currentSpeed;
        this.image = this.makeImage();
        if (this.currentSpeed <= 0) {
            this.slowDown();
        }
        else {
            this.slowDown();
            this.speedUp();
        }
    }
    
    public SpeedControllerGUI makeImage() {
        return new SpeedControllerGUI(this);
    }
    
    public int getCurrentSpeed() {
        return this.currentSpeed;
    }
    
    public boolean getPaused() {
        return this.paused;
    }
    
    public int getNoOfSpeeds() {
        return SpeedController.NO_OF_SPEEDS;
    }
    
    public SpeedControllerGUI getImage() {
        return this.image;
    }
    
    public void speedUp() {
        ++this.currentSpeed;
        if (this.currentSpeed >= SpeedController.NO_OF_SPEEDS) {
            this.currentSpeed = SpeedController.NO_OF_SPEEDS - 1;
        }
        this.image.update();
    }
    
    public void slowDown() {
        --this.currentSpeed;
        if (this.currentSpeed < 0) {
            this.currentSpeed = 0;
        }
        this.image.update();
    }
    
    public void pause() {
        this.paused = true;
        this.image.update();
    }
    
    public void resume() {
        this.paused = false;
        this.image.update();
    }
    
    public void delay(final int n) {
        try {
            Thread.sleep(n * SpeedController.delayFactor[this.currentSpeed]);
            while (this.paused) {
                Thread.sleep(SpeedController.delayFactor[this.currentSpeed]);
            }
        }
        catch (Exception ex) {}
    }
    
    static {
        delayFactor = new int[] { 256, 215, 181, 152, 128, 108, 91, 76, 64, 54, 45, 38, 32, 27, 23, 19, 16, 13, 11, 10, 8, 7, 6, 5, 4, 3, 3, 2, 2, 2, 1, 1, 1 };
        NO_OF_SPEEDS = SpeedController.delayFactor.length;
        HALF_SPEED = SpeedController.NO_OF_SPEEDS / 2;
        MAX_SPEED = SpeedController.NO_OF_SPEEDS - 1;
    }
}
