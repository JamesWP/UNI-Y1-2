import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by james on 17/02/15.
 */

public class ReservationRequest
{
  private final Account account;
  private final int     requestId;
  private final int     slotId;

  public ReservationRequest(Account a, int requestId, int slotId)
  {
    this.account = a;
    this.requestId = requestId;
    this.slotId = slotId;
  }

  public String toXml()
  {
    try
    {
      Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                       .newDocument();
      Element root = d.createElement("reserve");
      Element requestId = d.createElement("request_id");
      requestId.setTextContent("" + this.requestId);
      root.appendChild(requestId);
      Element username = d.createElement("username");
      username.setTextContent(this.account.username);
      root.appendChild(username);
      Element password = d.createElement("password");
      password.setTextContent(this.account.password);
      root.appendChild(password);
      Element slotId = d.createElement("slot_id");
      slotId.setTextContent("" + this.slotId);
      d.appendChild(root);

      return XmlUtils.docToString(d);
    } catch (ParserConfigurationException e) {e.printStackTrace();}

    throw new RuntimeException();
  }

  /**
   * should be run on seperate thread
   * @return
   */
  public ReservationRequestState execute(){
    ReservationResponse response = new ReservationResponse(HttpUtils.executePut(toXml(),Config
                                                                     .bandAPI));

  }

  public class ReservationResponse
  {
    private final boolean  successful;
    public URI      messageUri;

    public ReservationResponse(String response)
    {
      boolean issuccess = false;
      if(response==null){
        issuccess = false;
      }else
      try
      {
        Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                         .parse(new InputSource(new StringReader(response)));
        if (d.getDocumentElement().getTagName().equals("msg_uri"))
        {
          messageUri = new URI(d.getDocumentElement()
                                   .getTextContent());
          issuccess = true;
        }
      } catch (ParserConfigurationException e)
      {
        e.printStackTrace();
      } catch (SAXException e)
      {
        e.printStackTrace();
      } catch (IOException e)
      {
        e.printStackTrace();
      } catch (URISyntaxException e)
      {
        e.printStackTrace();
      }

      this.successful = issuccess;
      if (!successful) throw new RuntimeException();
    }

    /*shoud be run on sperate thread*/
    public ReservationRequestState processSuccessfullRequest()
    {
      if (successful)
      {
        try
        {
          switch (HttpUtils.excuteGet(new URI(messageUri.getScheme(),
                                              messageUri.getHost(),
                                              messageUri
                                                  .getPath(),
                                              messageUri.getQuery(),
                                              messageUri.getFragment())))
          {
            case 200:
              return ReservationRequestState.Success;
            case 404:
              return ReservationRequestState.FailNoProcess;
            case 503:
              return ReservationRequestState.ServiceUnavailable;
            case 401:
              return ReservationRequestState.AuthorizationException;
          }
        } catch (URISyntaxException e) {e.printStackTrace();}
        return null;
      } else return ReservationRequestState.FailPreError;
    }
  }

  public enum ReservationRequestState
  {
    Success, FailPreError, FailNoProcess,
    ServiceUnavailable, AuthorizationException
  }

  ;
}
