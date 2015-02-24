import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by james on 23/02/15.
 */
public class CancellationRequest extends Request
{
  private final int slotId;

  public CancellationRequest(Account a, int slotId, URI api)
  {
    super(a, api);
    this.slotId = slotId;
  }

  @Override
  public String toXml()
  {
    final Map<String, String> nvpairs = new HashMap<String, String>();
    String rootname = "cancel";
    nvpairs.put("slot_id", "" + this.slotId);
    return generateXML(nvpairs, rootname);
  }
}
