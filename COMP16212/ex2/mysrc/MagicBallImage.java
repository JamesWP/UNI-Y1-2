import java.awt.*;
import java.awt.event.*;

public class MagicBallImage extends BallImage implements MouseListener
{
    private MagicBall magicBall;
    private boolean runningFlashThread;
    
    public MagicBallImage(final MagicBall magicBall) {
        super((Ball)magicBall);
        this.runningFlashThread = false;
        this.magicBall = magicBall;
        this.addMouseListener(this);
    }
    
    public void update() {
        if (this.magicBall.isFlashing() && !this.runningFlashThread) {
            this.runningFlashThread = true;
            new Thread() {
                public void run() {
                    while (MagicBallImage.this.magicBall.isFlashing()) {
                        MagicBallImage.this.flash(4, 4);
                    }
                    MagicBallImage.this.repaint();
                    MagicBallImage.this.runningFlashThread = false;
                }
            }.start();
        }
        super.update();
    }
    
    public void paint(final Graphics graphics) {
        if (this.magicBall.isVisible()) {
            super.paint(graphics);
        }
    }
    
    public void mouseClicked(final MouseEvent mouseEvent) {
        final int modifiers = mouseEvent.getModifiers();
        if ((modifiers & 0x10) == 0x10) {
            this.magicBall.doMagic(1);
        }
        else if ((modifiers & 0x8) == 0x8) {
            this.magicBall.doMagic(2);
        }
        else if ((modifiers & 0x4) == 0x4) {
            this.magicBall.doMagic(3);
        }
    }
    
    public void mousePressed(final MouseEvent mouseEvent) {
    }
    
    public void mouseReleased(final MouseEvent mouseEvent) {
    }
    
    public void mouseEntered(final MouseEvent mouseEvent) {
    }
    
    public void mouseExited(final MouseEvent mouseEvent) {
    }
}
