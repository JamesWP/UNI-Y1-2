import javax.annotation.processing.SupportedSourceVersion;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by james on 20/02/15.
 */
public class Client {
    public static void main(String[] args) throws InterruptedException{
        while(true){
            bookBestSlots();
            Thread.sleep(1000);
        }
    }

    public static boolean bookBestSlots(){
        try {
            System.out.println("Trying to book best slots...");
            // get currently owned slots
            BookingRequest ownedHotelSlotsRequest = new BookingRequest(Config.userAccount, Config.hotelAPI);
            BookingRequest ownedBandSlotsRequest = new BookingRequest(Config.userAccount, Config.bandAPI);
            Set<Integer> ownedHotelSlots = new HashSet<Integer>(ownedHotelSlotsRequest.executeList());
            Set<Integer> ownedBandSlots = new HashSet<Integer>(ownedBandSlotsRequest.executeList());

            System.out.print("Owned hotel slots:");
            for(Integer o:ownedHotelSlots)System.out.print(o+",");
            System.out.println();
            System.out.print("Owned band slots:");
            for(Integer o:ownedBandSlots)System.out.print(o+",");
            System.out.println();

            // get all free slots
            AvailabilityRequest availableHotelSlotsRequest = new AvailabilityRequest(Config.userAccount, Config.hotelAPI);
            AvailabilityRequest availableBandSlotsRequest = new AvailabilityRequest(Config.userAccount, Config.bandAPI);
            Set<Integer> availableHotelSlots = new HashSet<Integer>(availableHotelSlotsRequest.executeList());
            Set<Integer> availableBandSlots = new HashSet<Integer>(availableBandSlotsRequest.executeList());


            // add owned slots to the available ones (though we only book non owned ones)
            availableBandSlots.addAll(ownedBandSlots);
            availableHotelSlots.addAll(ownedHotelSlots);

            System.out.print("Available hotel slots:");
            for(Integer o:availableHotelSlots)System.out.print(o+",");
            System.out.println();
            System.out.print("Available band slots:");
            for(Integer o:availableBandSlots)System.out.print(o+",");
            System.out.println();


            // find lowest common slot
            Set<Integer> commonSlots = new HashSet<Integer>(availableBandSlots);
            commonSlots.retainAll(availableHotelSlots);

            Integer minimum = getSetMinimum(commonSlots);

            System.out.println("Trying to book: " + minimum);

            // if necessary un-book previous slots (largest first)
            if (!ownedHotelSlots.contains(minimum) && ownedHotelSlots.size() > 1) {
                // un-book largest owned hotel slot
                Integer largestHotelSlot = getSetMaximum(ownedHotelSlots);
                System.out.println("Canceling hotel slot: " + largestHotelSlot);
                CancellationRequest cancelLargestHotelSlotRequest = new CancellationRequest(Config.userAccount, largestHotelSlot, Config.hotelAPI);
                Request.RequestResultState state = cancelLargestHotelSlotRequest.execute();
                if (state!= Request.RequestResultState.Success) throw new RuntimeException("could not cancel hotel slot");
            }
            if (!ownedBandSlots.contains(minimum) && ownedBandSlots.size() > 1) {
                // un-book largest owned band slot
                Integer largestBandSlot = getSetMaximum(ownedBandSlots);
                System.out.println("Canceling band slot: " + largestBandSlot);
                CancellationRequest cancelLargestBandSlotRequest = new CancellationRequest(Config.userAccount, largestBandSlot, Config.bandAPI);
                Request.RequestResultState state = cancelLargestBandSlotRequest.execute();
                if (state!= Request.RequestResultState.Success) throw new RuntimeException("could not cancel band slot");
            }

            // try to book the slots if required
            if(!ownedHotelSlots.contains(minimum)) {
                System.out.println("Booking hotel slot: " + minimum);
                ReservationRequest bookBestHotelSlotRequest = new ReservationRequest(Config.userAccount, minimum, Config.hotelAPI);
                Request.RequestResultState state = bookBestHotelSlotRequest.execute();
                if (state!= Request.RequestResultState.Success) throw new RuntimeException("could not book hotel slot");
            }
            if(!ownedBandSlots.contains(minimum)){
                System.out.println("Booking band slot: " + minimum);
                ReservationRequest bookBestBandSlotRequest = new ReservationRequest(Config.userAccount, minimum, Config.bandAPI);
                Request.RequestResultState state = bookBestBandSlotRequest.execute();
                if (state!= Request.RequestResultState.Success) throw new RuntimeException("could not book band slot");
            }

            System.out.println("Got best slots!");
            System.out.println("Hotel Slot:" + minimum);
            System.out.println("Band Slot:" + minimum);
            return true;
        }catch(Exception e){
            System.out.println("Encountered error when trying to book best slots");
            e.printStackTrace();
            return false;
        }
    }

    private static Integer getSetMaximum(Set<Integer> integerSet) {
        if(integerSet.size()==0) throw new RuntimeException("No maximum of set");
        Iterator<Integer> setIterator = integerSet.iterator();
        Integer maximum = setIterator.next();
        while(setIterator.hasNext()){
            Integer next = setIterator.next();
            if(next>maximum)
                maximum = next;
        }
        return maximum;
    }

    private static Integer getSetMinimum(Set<Integer> integerSet) {
        if(integerSet.size()==0) throw new RuntimeException("No minimum of set");
        Iterator<Integer> setIterator = integerSet.iterator();
        Integer minimum = setIterator.next();
        while(setIterator.hasNext()){
            Integer next = setIterator.next();
            if(next<minimum)
                minimum = next;
        }
        return minimum;
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
