import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 27/02/15.
 */
public class BookingRequest extends Request {
    public BookingRequest(Account a, URI api) {
        super(a, api);
    }

    @Override
    public String toXml() {
        return generateXML(null, "bookings");
    }

    public List<Integer> executeList() {
        AvailabilityResponse response = new AvailabilityResponse(executePut());
        RequestResultState result = processResponse(response);
        if (result.equals(RequestResultState.Success)) {
            return response.freeSlots;
        } else {
            throw new RuntimeException("error getting list:" + result);
        }
    }

    public class AvailabilityResponse extends Response {
        final List<Integer> freeSlots = new ArrayList<Integer>();

        public AvailabilityResponse(Document d) {
            super(BookingRequest.this, d);
        }

        @Override
        protected void processResult(Document messageDocument) {
            Element body = (Element) messageDocument.getDocumentElement()
                    .getElementsByTagName("body").item(0);
            Element bookings = (Element) body.getElementsByTagName("bookings")
                    .item(0);
            NodeList availabilityElements = bookings.getElementsByTagName
                    ("slot_id");
            for (int i = 0; i < availabilityElements.getLength(); i++) {
                Element avail = (Element) availabilityElements.item(i);
                freeSlots.add(Integer.parseInt(avail.getTextContent()));
            }
        }
    }
}
