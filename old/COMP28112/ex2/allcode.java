/**
 * Created by james on 17/02/15.
 */
public class Account {
    public final String username, password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 24/02/15.
 */
public class AvailabilityRequest extends Request {
    public AvailabilityRequest(Account a, URI api) {
        super(a, api);
    }

    @Override
    public String toXml() {
        return generateXML(null, "availability");
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
            super(AvailabilityRequest.this, d);
        }

        @Override
        protected void processResult(Document messageDocument) {
            Element body = (Element) messageDocument.getDocumentElement()
                    .getElementsByTagName("body").item(0);
            Element availability = (Element) body.getElementsByTagName("availability").item(0);
            NodeList availabilityElements = availability.getElementsByTagName("slot_id");
            for (int i = 0; i < availabilityElements.getLength(); i++) {
                Element avail = (Element) availabilityElements.item(i);
                freeSlots.add(Integer.parseInt(avail.getTextContent()));
            }
        }
    }
}
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
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by james on 23/02/15.
 */
public class CancellationRequest extends Request {
    private final int slotId;

    public CancellationRequest(Account a, int slotId, URI api) {
        super(a, api);
        this.slotId = slotId;
    }

    @Override
    public String toXml() {
        final Map<String, String> nvpairs = new HashMap<String, String>();
        String rootname = "cancel";
        nvpairs.put("slot_id", "" + this.slotId);
        return generateXML(nvpairs, rootname);
    }
}
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
import java.net.URI;

/**
 * Created by james on 17/02/15.
 */
public class Config {
    public static URI bandAPI;
    public static URI hotelAPI;
    public static Account userAccount = new Account("jp2107", "QgScXJ");

    static {
        try {
            bandAPI = new URI("http://jewel.cs.man.ac" +
                    ".uk:3020/queue/enqueue");
            hotelAPI = new URI("http://jewel.cs.man.ac" +
                    ".uk:3010/queue/enqueue");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/**
 * Created by james on 17/02/15.
 */


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.naming.ServiceUnavailableException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;

public class HttpUtils {
    public static HttpClient client;

    static {
        client = HttpClients.createDefault();
    }

    public static MessageResult excuteGet(URI requestUri)
            throws ConnectTimeoutException, SocketTimeoutException {
        HttpGet get = new HttpGet(requestUri);
        get.setConfig(RequestConfig.custom().setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build());
        get.addHeader("Accepts", "application/xml");
        get.addHeader("Content-Type", "application/xml");
        try {
            HttpResponse resp = client.execute(get);

            switch (resp.getStatusLine().getStatusCode()) {
                case 200:
                    return parseMessageResult(EntityUtils.toString(resp.getEntity()));
                case 404:
                    return MessageResult.NotProcessedYet;
                case 503:
                    return MessageResult.NotProcessedYet;
                case 401:
                    /*System.out.println("HttpGet response:" + resp.getStatusLine()
                            .getStatusCode() + " + " +
                            EntityUtils.toString(resp.getEntity()));*/
                    throw new RuntimeException("message retrieval fail: " + Request
                            .RequestResultState.AuthorizationException);
                default:
                    /*System.out.println("HttpGet response:" + resp.getStatusLine()
                            .getStatusCode() + " + " +
                            EntityUtils.toString(resp.getEntity()));*/
                    throw new RuntimeException("could not retreive message result");
            }
        } catch (ConnectTimeoutException e) {
            throw e;
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("could not retreive message result", e);
        } finally {
            get.releaseConnection();
        }
    }

    private static MessageResult parseMessageResult(String s) {
        MessageResult mr = new MessageResult();
        Document d;
        try {
            d = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(s)));

            mr.messageCode = Integer.parseInt(
                    d.getDocumentElement()
                            .getElementsByTagName("code").item(0).getTextContent());
            mr.messageBody = d.getDocumentElement().getElementsByTagName("body").item(0).getTextContent();
            mr.messageDocument = d;

            return mr;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("could not parse message result");
    }

    public static String executePut(String request, URI requestAPI)
            throws ConnectTimeoutException, ServiceUnavailableException {
        HttpPut put = new HttpPut(requestAPI);
        put.setConfig(RequestConfig.custom().setConnectTimeout(5000)
                .setSocketTimeout(5000).setConnectionRequestTimeout(5000)
                .build());
        put.addHeader("Accepts", "application/xml");
        put.addHeader("Content-Type", "application/xml");
        try {
            put.setEntity(new StringEntity(request));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            HttpResponse resp = client.execute(put);
            switch (resp.getStatusLine().getStatusCode()) {
                case 200:
                    try {
                        return EntityUtils.toString(resp.getEntity());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                case 503:
                    throw new ServiceUnavailableException();
                default:
                    throw new RuntimeException("error sending request");
            }
        } catch (ConnectTimeoutException e) {
            throw e;
        } catch (IOException e) {
            throw new RuntimeException("could not send put ", e);
        } finally {
            put.releaseConnection();
        }
    }
}
import org.w3c.dom.Document;

/**
 * Created by james on 23/02/15.
 */
public class MessageResult {
    public static final MessageResult NotProcessedYet = new MessageResult(404,
            "not " +
                    "processed yet");
    public int messageCode;
    public String messageBody;
    public Document messageDocument;

    public MessageResult() {
    }

    public MessageResult(int code, String body) {
        messageCode = code;
        messageBody = body;
    }
}
import org.apache.http.conn.ConnectTimeoutException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.naming.ServiceUnavailableException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by james on 23/02/15.
 */
public abstract class Request {
    final Account account;
    final URI api;
    public RequestUniqueID requestID;

    public Request(Account a, URI api) {
        this.account = a;
        this.api = api;
    }

    abstract public String toXml();

    public Document executePut() {
        Document d = null;
        int remainingAttempts = 10;
        while (d == null && remainingAttempts-- > 1) {
            //System.out.println("Trying to send request: remaining attempts " +
            //        remainingAttempts);
            try {
                String response = HttpUtils.executePut(toXml(), api);
                d = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(new InputSource(new StringReader(response)));
                return d;
            } catch (ConnectTimeoutException e) {
                try {
                    //System.out.println("ConnectTimeout");
                    Thread.sleep( (20/remainingAttempts) * 1000 + 1000);
                } catch (InterruptedException f) {
                }
            } catch (ServiceUnavailableException e) {
                try {
                    //System.out.println("ServiceUnavailable");
                    Thread.sleep((20/remainingAttempts) * 1000 + 1000);
                } catch (InterruptedException f) {
                }
            } catch (Exception e) {
                throw new RuntimeException("Response result parse error");
            }
        }
        throw new RuntimeException("max attempts reached");
    }

    /**
     * should be run on seperate thread
     *
     * @return
     */
    public RequestResultState execute() {
        Response response = new Response(Request.this, executePut());
        return processResponse(response);
    }

    public RequestUniqueID getRequestID() {
        if (this.requestID == null) this.requestID = new RequestUniqueID();
        return this.requestID;
    }

    protected String generateXML(Map<String, String> nvpairs,
                                 String rootname) {

        if (nvpairs == null) nvpairs = new HashMap<String, String>();
        nvpairs.put("username", this.account.username);
        nvpairs.put("password", this.account.password);
        nvpairs.put("request_id", this.getRequestID().ID);

        Document d;
        try {
            d = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .newDocument();
            Element root = d.createElement(rootname);
            for (String s : nvpairs.keySet()) {
                String name = s;
                String value = nvpairs.get(name);
                Element requestId = d.createElement(name);
                requestId.setTextContent(value);
                root.appendChild(requestId);
            }
            d.appendChild(root);

            return XmlUtils.docToString(d);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("could not generate xml");
    }


    RequestResultState processResponse(Response response) {
        RequestResultState respState = response.processSuccessfullRequest();
        System.out.println("Success Message: " + response.messageUri);
        return respState;
    }

    public enum RequestResultState {
        Success, Failure, FailureMaxAttempts, AuthorizationException
    }

    public static class RequestUniqueID {
        public final String ID;

        public RequestUniqueID() {
            ID = "JWP" + getUID();
        }

        private static int getUID() {
            return Math.round((float) Math.random() * 34567845 + 345678);
        }
    }
}
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
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.w3c.dom.Document;

import java.net.SocketTimeoutException;
import java.net.URI;

public class Response {
    final Request req;
    public URI messageUri;
    boolean successful;

    public Response(Request req, Document response) {
        this.req = req;
        if (response.getDocumentElement().getTagName().equals("msg_uri")) {
            try {
                messageUri = new URI(response.getDocumentElement()
                        .getTextContent());
                messageUri = new URIBuilder(messageUri)
                        .addParameter("username", req.account.username)
                        .addParameter("password", req.account.password)
                        .build();
            } catch (Exception e) {
                throw new RuntimeException("Could not parse message uri from " +
                        "response");
            }
        }
    }

    /*shoud be run on sperate thread*/
    public Request.RequestResultState processSuccessfullRequest() {
        //System.out.println("Message url:" + messageUri.toString());
        MessageResult res = null;
        int remainingattempts = 21;
        while ((res == null || res.messageCode == 404) && remainingattempts > 0) {
            try {
                //System.out.println("Trying to read response message: attempts " +
                //        "remaining " + remainingattempts);
                res = HttpUtils.excuteGet(messageUri);
                //System.out.println("Code:" + res.messageCode);
                switch (res.messageCode) {
                    case 200:
                        processResult(res.messageDocument);
                        return Request.RequestResultState.Success;
                    case 409:
                        return Request.RequestResultState.Failure;
                    case 401:
                        return Request.RequestResultState.AuthorizationException;
                    case 403:
                        return Request.RequestResultState.Failure;
                    case 510:
                        return Request.RequestResultState.Failure;
                    case 404:
                        remainingattempts--;
                        try {
                            Thread.sleep((25 / remainingattempts) * 1000+1000);
                        } catch (Exception e) {
                        }
                        continue;
                    default:
                        throw new RuntimeException("unknown message result code fail");
                }
            } catch (SocketTimeoutException e) {
                remainingattempts--;
                try {
                    Thread.sleep(1000);
                } catch (Exception f) {
                }
                continue;
            } catch (ConnectTimeoutException e) {
                remainingattempts--;
                try {
                    Thread.sleep(1000);
                } catch (Exception f) {
                }
                continue;
            }
        }
        return Request.RequestResultState.FailureMaxAttempts;
    }

    protected void processResult(Document messageDocument) {
    }
}import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Created by james on 17/02/15.
 */
public class XmlUtils {
    public static String docToString(Document d) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(d), new StreamResult(writer));
            return writer.getBuffer().toString().replaceAll("\n|\r", "");
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
