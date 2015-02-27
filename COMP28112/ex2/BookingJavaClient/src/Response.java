import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.w3c.dom.Document;

import java.net.SocketTimeoutException;
import java.net.URI;

public class Response
{
  final Request req;
  boolean successful;
  public URI messageUri;

  public Response(Request req, Document response)
  {
    this.req = req;
    if (response.getDocumentElement().getTagName().equals("msg_uri"))
    {
      try
      {
        messageUri = new URI(response.getDocumentElement()
                                 .getTextContent());
        messageUri = new URIBuilder(messageUri)
                         .addParameter("username", req.account.username)
                         .addParameter("password", req.account.password)
                         .build();
      } catch (Exception e)
      {
        throw new RuntimeException("Could not parse message uri from " +
                                       "response");
      }
    }
  }

  /*shoud be run on sperate thread*/
  public Request.RequestResultState processSuccessfullRequest()
  {
    System.out.println("Message url:" + messageUri.toString());
    MessageResult res = null;
    int remainingattempts = 21;
    while ((res == null || res.messageCode == 404) && remainingattempts > 0)
    {
      try
      {
        System.out.println("Trying to read response message: attempts " +
                             "remaining " + remainingattempts);
        res = HttpUtils.excuteGet(messageUri);
        System.out.println("Code:" + res.messageCode);
        switch (res.messageCode)
        {
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
              Thread.sleep((25/remainingattempts) * 1000);
            } catch (Exception e) {}
            continue;
          default:
            throw new RuntimeException("unknown message result code fail");
        }
      } catch(SocketTimeoutException e){
        remainingattempts--;
        try { Thread.sleep(1000);} catch (Exception f) {}
        continue;
      } catch (ConnectTimeoutException e){
        remainingattempts--;
        try { Thread.sleep(1000);} catch (Exception f) {}
        continue;
      }
    }
    return Request.RequestResultState.FailureMaxAttempts;
  }

  protected void processResult(Document messageDocument)
  {}
}