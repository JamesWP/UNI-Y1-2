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
