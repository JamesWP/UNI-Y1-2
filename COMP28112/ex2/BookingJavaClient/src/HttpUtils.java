/**
 * Created by james on 17/02/15.
 */


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpUtils
{
  public static int excuteGet(URI requestUri) {
    URL url;
    HttpURLConnection connection = null;
    int resp = 0;
    try {
      url = requestUri.toURL();
      connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("GET");
      connection.setUseCaches (false);
      connection.setDoInput(false);
      connection.setDoOutput(false);
      resp = connection.getResponseCode();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if(connection != null) {
        connection.disconnect();
      }
    }
    if(resp == 0) throw new RuntimeException();
    return resp;
  }

  public static String executePut(String request,URI requestAPI){
    URL url;
    HttpURLConnection connection;
    try{
      url = requestAPI.toURL();
      connection = (HttpURLConnection) url.openConnection();
      connection.setDoInput(true);
      connection.setDoOutput(true);
      connection.setRequestProperty("Content-Type","application/xml");
      connection.setRequestProperty("Accept","application/xml");
      DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
      dos.write(request.getBytes(Charset.defaultCharset()));
      dos.flush();
      dos.close();

      switch(connection.getResponseCode()){
        case 200:
          InputStream is = connection.getInputStream();
          BufferedReader rd = new BufferedReader(new InputStreamReader(is));
          String line;
          StringBuffer response = new StringBuffer();
          while((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
          }
          rd.close();
          return response.toString();
        case 503:
          return null;
        default: throw new RuntimeException();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    throw new RuntimeException();
  }
}
