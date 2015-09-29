/*
 * A very simple application illustrating how to use the interface.
 * Prints the names of all the drivers in the database.
 * @author John Sargeant, James Peach
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        database.openBusDatabase();
        int[] driverIDs = DriverInfo.getDrivers();
        for (int driverID : driverIDs)
            System.out.println(driverID + ":\t" + DriverInfo.getName(driverID));
        database.closeBusDatabase();
    }

}
