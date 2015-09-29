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
