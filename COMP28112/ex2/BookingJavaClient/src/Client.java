import java.util.List;

/**
 * Created by james on 20/02/15.
 */
public class Client {
    public static void main(String[] args) {
        AvailabilityRequest req = new AvailabilityRequest(Config.userAccount, Config.bandAPI);
        List<Integer> available = req.executeList();
        for (int i : available) System.out.printf("Slot: %d, ", i);
    }
}

//CancellationRequest req = new CancellationRequest(Config.userAccount,27,
//ReservationRequest req = new ReservationRequest(Config.userAccount,27,
//                                                  Config.bandAPI);
//ReservationRequest.RequestResultState resp = req.execute();

//System.out.println("Client response:" + resp.toString());
//    AvailabilityRequest req = new AvailabilityRequest(Config.userAccount,
//                                                     Config.bandAPI);
//BookingRequest req = new BookingRequest(Config.userAccount,
//                                                 Config.bandAPI);
//List<Integer> available= req.executeList();
//for(int i: available) System.out.printf("Slot: %d, ",i);
//System.out.println();
