import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by james on 17/02/15.
 */

public class ReservationRequest extends Request {
    private final int slotId;

    public ReservationRequest(Account a, int slotId, URI api) {
        super(a, api);
        this.slotId = slotId;
    }

    @Override
    public String toXml() {
        final Map<String, String> nvpairs = new HashMap<String, String>();
        String rootname = "reserve";
        nvpairs.put("slot_id", "" + this.slotId);
        return generateXML(nvpairs, rootname);
    }
}
