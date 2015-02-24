import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by james on 24/02/15.
 */
public class AvailabilityRequest extends Request
{
  public AvailabilityRequest(Account a, URI api)
  {
    super(a, api);
  }

  @Override
  public String toXml()
  {
    return generateXML(null,"availability");
  }

  public List<Integer> executeList()
  {
    AvailabilityResponse response = new AvailabilityResponse(executePut());
    RequestResultState result = processResponse(response);
    if (result.equals(RequestResultState.Success)){
      return response.freeSlots;
    }else{
      throw new RuntimeException("error getting list:" + result);
    }
  }

  public class AvailabilityResponse extends Response
  {
    final List<Integer> freeSlots = new ArrayList<Integer>();

    public AvailabilityResponse(Document d){super(AvailabilityRequest.this,d);}

    @Override
    protected void processResult(Document messageDocument)
    {
      Element body = (Element) messageDocument.getDocumentElement()
                                .getElementsByTagName("body").item(0);
      Element availability = (Element) body.getElementsByTagName("availability").item(0);
      NodeList availabilityElements = availability.getElementsByTagName("slot_id");
      for (int i = 0;i < availabilityElements.getLength();i++){
        Element avail = (Element) availabilityElements.item(i);
        freeSlots.add(Integer.parseInt(avail.getTextContent()));
      }
    }
  }
}
