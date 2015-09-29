import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

/** Window allow input of flight booking details.*/ 

public class BookFlightWindow extends JFrame {

/* *** PART D. You will need to make additions/changes to this class
   in order to complete part D of the exercise. */

    private FlightBookingManager manager;
    private Flight flight;

    private JTextField flightDetails;
    private JComboBox title;
    private JTextField givenName;
    private JTextField familyName;
    private JTextField creditCard;
    private JTextArea specialRequest;
    private int bookingClass;
    
    /** Create a new Window for a booking. */
    public BookFlightWindow( FlightBookingManager aManager,
			     Flight aFlight ) {
	/* Use the manager and flight passed in */
	this.manager = aManager;
	this.flight = aFlight;

	Container contents = getContentPane();
	contents.setLayout(new BorderLayout());
	
	JPanel topPanel = new JPanel();
	topPanel.setLayout( new GridLayout( 0, 1 ) );
	
	topPanel.add( new JLabel( "Flight:" ) );
	flightDetails = new JTextField( 40 );
	topPanel.add( flightDetails );
	
	topPanel.add( new JLabel( "Title:" ) );
	/* Requires initialisation with the allowed titles */
	title = new JComboBox();
	topPanel.add( title );
	
	topPanel.add( new JLabel( "Given Name:" ) );
	givenName = new JTextField( 40 );
	topPanel.add( givenName );
	
	topPanel.add( new JLabel( "Family Name:" ) );
	familyName = new JTextField( 40 );
	topPanel.add( familyName );

	topPanel.add( new JLabel( "Credit Card:" ) );
	creditCard = new JTextField( 20 );
	topPanel.add( creditCard );

	topPanel.add( new JLabel( "Class:" ) );

	/* Code will be needed here to ensure that the radio buttons
	   behave properly. This will involve making sure that they
	   operate together as a group and that the selected button
	   effects the properties of the booking made. */

	JPanel radioButtons = new JPanel();
	JRadioButton radioButton;
	
	radioButton = 
	    new JRadioButton( "First" );
	radioButtons.add( radioButton );

	radioButton = 
	    new JRadioButton( "Business" );
	radioButtons.add( radioButton );

	radioButton = 
	    new JRadioButton( "Economy" );
	radioButtons.add( radioButton );

	topPanel.add( radioButtons );

	topPanel.add( new JLabel( "Special Requests:" ) );

	contents.add( topPanel, BorderLayout.NORTH);


	JPanel middlePanel = new JPanel();
	specialRequest = new JTextArea( 10, 40 );
	middlePanel.add( new JScrollPane( specialRequest ) );

	contents.add( middlePanel , BorderLayout.CENTER );

	/* Again the buttons here need listeners to ensure that they
	   operator correctly. */

	JPanel buttons = new JPanel();

	JButton button = new JButton( "OK" );

	buttons.add( button );

	button = new JButton( "Cancel" );
	buttons.add( button );

	contents.add( buttons, BorderLayout.SOUTH );

	/* Make sure that window closing behaviour is done correctly.  */

	pack();
    }
}
