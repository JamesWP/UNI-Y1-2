import org.w3c.dom.Document;

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
public class XmlUtils
{
  public static String docToString(Document d){
    try
    {
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      StringWriter writer = new StringWriter();
      transformer.transform(new DOMSource(d), new StreamResult(writer));
      return writer.getBuffer().toString().replaceAll("\n|\r", "");
    }catch (TransformerException e){ e.printStackTrace(); }
    throw new RuntimeException();
  }
}
