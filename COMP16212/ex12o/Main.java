/** Basic driver for the application */

public class Main {
    public static void main(String[] args) {
	/* Create a new FlightBookingManager. In this case, we use the
	   implementation supplied by class
	   FlightBookingManagerImpl. If this confuses you, you should
	   go back and reconsult your notes on Java interfaces. */
	FlightBookingManager fm = new FlightBookingManagerImpl();

	/* If a filename is supplied, try and open it as a collection
	   of bookings. */
	if (args.length > 0) {
	    try {
		fm.loadBookings( new java.io.File( args[0] ) );
	    } catch (Exception ex) {
		System.out.println( "Exception when loading: ");
		System.out.println( ex.getMessage() );
	    }
	}

	FlightBookingManagerGUI gui = 
	    new FlightBookingManagerGUI( fm );
	gui.setVisible( true );
    }
}