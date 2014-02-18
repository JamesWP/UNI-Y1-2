import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class AboutBox extends Dialog
{
    public AboutBox(final Frame owner, final String title, final boolean modal, final String str) {
        super(owner, title, modal);
        final Panel comp = new Panel(new GridLayout(0, 1, 0, 0));
        this.add(comp, "North");
        final StringTokenizer stringTokenizer = new StringTokenizer(str, "\n");
        while (stringTokenizer.countTokens() > 0) {
            comp.add(new Label(stringTokenizer.nextToken()));
        }
        final Panel comp2 = new Panel();
        this.add(comp2, "South");
        final Button comp3 = new Button("Dismiss");
        comp2.add(comp3);
        comp3.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                AboutBox.this.setVisible(false);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                AboutBox.this.setVisible(false);
            }
        });
        this.pack();
        this.show();
    }
    
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
        if (!visible) {
            this.dispose();
        }
    }
}
