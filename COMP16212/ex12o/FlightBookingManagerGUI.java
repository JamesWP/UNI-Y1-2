import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/** Basic Flight Booker UI. */ 

public class FlightBookingManagerGUI extends JFrame {

    private FlightBookingManager manager;

    private JComboBox from;
    private JComboBox to;
    private JTextField date;
    private JList flightsList;

    /** Create a new GUI using the given flight booking manager. */
    public FlightBookingManagerGUI( FlightBookingManager aManager ) {
	/* Use the manager passed in */
	this.manager = aManager;

	/* *** PART B. Add a menu item that allows the user to close down
	   the application. */

	/* *** PART F. Add save/load menu items. */

	JMenuBar menuBar = new JMenuBar();
	this.setJMenuBar( menuBar );
	
	JMenu menu = new JMenu( "File" );
	menuBar.add( menu );

	Container contents = getContentPane();
	contents.setLayout(new BorderLayout());

	/* *** PART A: change the creation/initialisation of the combo
	   boxes so that they show available airports. */

	JPanel topPanel = new JPanel();

	topPanel.add( new JLabel( "From:" ) );
	from = new JComboBox();

	topPanel.add( from );

	topPanel.add( new JLabel( "To:" ) );
	to = new JComboBox();

	topPanel.add( to );

	topPanel.add( new JLabel( "Date:" ) );
	date = new JTextField( 10 );
	topPanel.add( date);

	/* *** PART C. Implement query functionality and add a
	   listener that executes the query when the button is
	   pressed. */

	JButton button = new JButton( "Query" );
	topPanel.add( button );

	contents.add( topPanel, BorderLayout.NORTH);

	flightsList = new JList();
	contents.add( new JScrollPane( flightsList) , BorderLayout.CENTER );

	JPanel buttons = new JPanel();

	/* *** PART D. Implement booking functionality and add a
	   listener that opens a booking window when the button is
	   pressed. */

	button = new JButton( "Book" );
	buttons.add( button );

	/* *** PART E. Implement functionality displaying bookings
	   when this button is pressed. */

	button = new JButton( "Show Bookings" );
	buttons.add( button );

	contents.add( buttons, BorderLayout.SOUTH );

	/* *** PART B: Set appropriate behaviour for window closing using
	   a listener. You may want to add additional methods to the
	   class. */

	pack();
    }

}
