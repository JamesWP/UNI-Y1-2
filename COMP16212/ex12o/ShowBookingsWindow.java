import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPanel;

/** Window showing bookings and details. */ 

public class ShowBookingsWindow extends JFrame {

/* *** PART E. You will need to make additions/changes to this class
   in order to complete part E of the exercise. */

    private FlightBookingManager manager;

    private JList bookings;
    private JTextArea details;
    
    /** Create a window to show bookings. */
    public ShowBookingsWindow( FlightBookingManager aManager ) {
	/* Use the manager passed in */
	this.manager = aManager;

	Container contents = getContentPane();
	contents.setLayout(new BorderLayout());
	
	/* List initialisation will need to be changed. A listener
	   will also be needed to listen for when the user clicks on a
	   booking in the list.  */

	bookings = new JList();
	contents.add( new JScrollPane( bookings ), BorderLayout.NORTH );

	details = new JTextArea( 10, 40 );
	contents.add( new JScrollPane( details), BorderLayout.CENTER );

	JPanel buttonPanel = new JPanel();

	/* Listeners are needed for these buttons */
	JButton button = new JButton( "Dismiss" );
	buttonPanel.add( button );
	contents.add( buttonPanel, BorderLayout.SOUTH );

	button = new JButton( "Delete" );
	buttonPanel.add( button );
	contents.add( buttonPanel, BorderLayout.SOUTH );

	/* Make sure that window closing behaviour is done correctly.  */

	pack();
    }

}
