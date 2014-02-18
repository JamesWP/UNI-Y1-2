import java.awt.event.*;

public class MagicWorkerImage extends PersonImage
{
    private MagicWorker magicWorker;
    
    public MagicWorkerImage(final MagicWorker magicWorker) {
        super((Person)magicWorker);
        this.magicWorker = magicWorker;
    }
    
    public void mouseClicked(final MouseEvent mouseEvent) {
        super.mouseClicked(mouseEvent);
        final int modifiers = mouseEvent.getModifiers();
        if ((modifiers & 0x10) == 0x10) {
            this.magicWorker.doMagic(1);
        }
        else if ((modifiers & 0x8) == 0x8) {
            this.magicWorker.doMagic(2);
        }
        else if ((modifiers & 0x4) == 0x4) {
            this.magicWorker.doMagic(3);
        }
    }
}
