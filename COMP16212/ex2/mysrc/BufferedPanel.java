import java.awt.*;
import java.awt.image.*;

public class BufferedPanel extends Panel
{
    private Image offscreenImage;
    private Graphics offscreenGraphics;
    private Dimension offscreenGraphicsSize;
    
    public synchronized void showBufferedImage() {
        if (this.offscreenImage != null) {
            final Graphics graphics = this.getGraphics();
            if (graphics != null) {
                graphics.drawImage(this.offscreenImage, 0, 0, this);
            }
        }
    }
    
    public synchronized void paint(final Graphics graphics) {
        this.ensureOffscreenGraphicsExists();
        super.paint(this.offscreenGraphics);
        if (graphics != this.offscreenGraphics) {
            graphics.drawImage(this.offscreenImage, 0, 0, this);
        }
    }
    
    public synchronized void update(final Graphics graphics) {
        this.ensureOffscreenGraphicsExists();
        this.offscreenGraphics.setClip(graphics.getClip());
        super.update(this.offscreenGraphics);
        graphics.drawImage(this.offscreenImage, 0, 0, this);
    }
    
    private void ensureOffscreenGraphicsExists() {
        if (this.offscreenGraphicsSize == null || !this.offscreenGraphicsSize.equals(this.getSize())) {
            this.offscreenGraphicsSize = this.getSize();
            this.offscreenImage = this.createImage(this.offscreenGraphicsSize.width, this.offscreenGraphicsSize.height);
            this.offscreenGraphics = this.offscreenImage.getGraphics();
        }
    }
}
