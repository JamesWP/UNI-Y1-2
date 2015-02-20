import java.net.URI;

/**
 * Created by james on 17/02/15.
 */
public class Config
{
  public static URI bandAPI;
  public static Account userAccount = new Account("jp2107","QgScXJ");

  static
  {
    try
    {
      bandAPI = new URI("http://jewel.cs.man.ac" +
                            ".uk:3020/queue/enqueue");
    }catch(Exception e){e.printStackTrace();}
  }
}
