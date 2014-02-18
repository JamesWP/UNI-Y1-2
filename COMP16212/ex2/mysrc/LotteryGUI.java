import java.awt.*;
import java.awt.event.*;

public class LotteryGUI extends Frame implements ActionListener
{
    private SpeedController speedController;
    private Panel peoplePanel;
    private Panel gamePanel;
    private final Color frameColour;
    private AboutBox aboutBox;
    
    public LotteryGUI(final String str, final SpeedController speedController) {
        super();
        this.peoplePanel = new Panel();
        this.gamePanel = new Panel();
        this.frameColour = new Color(200, 240, 255);
        this.speedController = speedController;
        this.speedController.getImage().setBackground(this.frameColour);
        this.setTitle("Notional Lottery: " + str);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        final MenuBar menuBar = new MenuBar();
        this.setMenuBar(menuBar);
        final Menu m = new Menu("File");
        menuBar.add(m);
        final MenuItem mi = new MenuItem("Quit", new MenuShortcut(81));
        mi.setActionCommand("quit");
        mi.addActionListener(this);
        m.add(mi);
        final Menu i = new Menu("Speed");
        menuBar.add(i);
        final MenuItem mi2 = new MenuItem("Toggle Interactive", new MenuShortcut(73));
        mi2.setActionCommand("interactive");
        mi2.addActionListener(this);
        i.add(mi2);
        final MenuItem mi3 = new MenuItem("Speed Up", new MenuShortcut(70));
        mi3.setActionCommand("faster");
        mi3.addActionListener(this);
        i.add(mi3);
        final MenuItem mi4 = new MenuItem("Slow Down", new MenuShortcut(83));
        mi4.setActionCommand("slower");
        mi4.addActionListener(this);
        i.add(mi4);
        final MenuItem mi5 = new MenuItem("Pause", new MenuShortcut(80));
        mi5.setActionCommand("pause");
        mi5.addActionListener(this);
        i.add(mi5);
        final MenuItem mi6 = new MenuItem("Resume", new MenuShortcut(82));
        mi6.setActionCommand("resume");
        mi6.addActionListener(this);
        i.add(mi6);
        final Menu j = new Menu("Help");
        menuBar.add(j);
        final MenuItem mi7 = new MenuItem("About", new MenuShortcut(65));
        mi7.setActionCommand("about");
        mi7.addActionListener(this);
        j.add(mi7);
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(200, 240, 255));
        this.peoplePanel.setLayout(new GridLayout(0, 1, 5, 5));
        this.gamePanel.setLayout(new GridLayout(1, 0, 5, 5));
        this.add(this.peoplePanel, "West");
        this.add(this.gamePanel, "Center");
        this.setVisible(true);
    }
    
    public void actionPerformed(final ActionEvent actionEvent) {
        final String actionCommand = actionEvent.getActionCommand();
        if (actionCommand.equals("interactive")) {
            this.speedController.getImage().setVisible(!this.speedController.getImage().isVisible());
        }
        else if (actionCommand.equals("faster")) {
            this.speedController.speedUp();
        }
        else if (actionCommand.equals("slower")) {
            this.speedController.slowDown();
        }
        else if (actionCommand.equals("pause")) {
            this.speedController.pause();
        }
        else if (actionCommand.equals("resume")) {
            this.speedController.resume();
        }
        else if (actionCommand.equals("about")) {
            if (this.aboutBox == null) {
                (this.aboutBox = new AboutBox(this, "About Notional Lottery", false, "This is the Notional Lottery simulator game.\n \nWritten by John Latham,\nSchool of Computer Science,\nThe University Of Manchester, U.K.\nEmail: jtl@cs.man.ac.uk\n \nCopyright 2001/2005")).setBackground(this.frameColour);
            }
            else {
                this.aboutBox.setVisible(!this.aboutBox.isVisible());
            }
        }
        else if (actionCommand.equals("quit")) {
            System.exit(0);
        }
    }
    
    public void addPerson(final Person person) {
        this.peoplePanel.add(person.getImage());
        person.getImage().setSpeedController(this.speedController);
        this.pack();
    }
    
    public void addGame(final Game game) {
        final Panel comp = new Panel();
        comp.setLayout(new GridLayout(2, 0, 5, 5));
        comp.add(game.getMachineImage());
        game.getMachineImage().setSpeedController(this.speedController);
        comp.add(game.getRackImage());
        game.getRackImage().setSpeedController(this.speedController);
        this.gamePanel.add(comp);
        this.pack();
    }
}
