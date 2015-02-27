/**
 * Created by james on 17/02/15.
 */


import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.naming.ServiceUnavailableException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class HttpUtils
{
  public static HttpClient client;

  static {client = HttpClients.createDefault();}

  public static MessageResult excuteGet(URI requestUri)
      throws ConnectTimeoutException, SocketTimeoutException
  {
    HttpGet get = new HttpGet(requestUri);
    get.setConfig(RequestConfig.custom().setConnectTimeout(5000)
                      .setSocketTimeout(5000)
                      .setConnectionRequestTimeout(5000)
                      .build());
    get.addHeader("Accepts","application/xml");
    get.addHeader("Content-Type","application/xml");
    try
    {
      HttpResponse resp = client.execute(get);

      switch (resp.getStatusLine().getStatusCode())
      {
        case 200:
          return parseMessageResult(EntityUtils.toString(resp.getEntity()));
        case 404:
          return MessageResult.NotProcessedYet;
        case 503:
          return MessageResult.NotProcessedYet;
        case 401:
          System.out.println("HttpGet response:"+ resp.getStatusLine()
                                                      .getStatusCode()+ " + " +
                                 EntityUtils.toString(resp.getEntity()));
          throw new RuntimeException("message retrieval fail: " + Request
                                                                      .RequestResultState.AuthorizationException);
        default:
          System.out.println("HttpGet response:"+ resp.getStatusLine()
                                                      .getStatusCode()+ " + " +
                                 EntityUtils.toString(resp.getEntity()));
          throw new RuntimeException("could not retreive message result");
      }
    } catch (ConnectTimeoutException e){
      throw e;
    } catch (SocketTimeoutException e){
      throw e;
    }catch (Exception e)
    {
      throw new RuntimeException("could not retreive message result",e);
    }
    finally
    {
      get.releaseConnection();
    }
  }

  private static MessageResult parseMessageResult(String s)
  {
    MessageResult mr = new MessageResult();
    Document d;
    try
    {
      d = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                       .parse(new InputSource(new StringReader(s)));

      mr.messageCode = Integer.parseInt(
                d.getDocumentElement()
                .getElementsByTagName("code").item(0).getTextContent());
      mr.messageBody = d.getDocumentElement().getElementsByTagName("body").item(0).getTextContent();
      mr.messageDocument = d;

      return mr;
    } catch (SAXException e)
    {
      e.printStackTrace();
    } catch (IOException e)
    {
      e.printStackTrace();
    } catch (ParserConfigurationException e)
    {
      e.printStackTrace();
    }
    throw new RuntimeException("could not parse message result");
  }

  public static String executePut(String request, URI requestAPI)
      throws ConnectTimeoutException, ServiceUnavailableException
  {
    HttpPut put = new HttpPut(requestAPI);
    put.setConfig(RequestConfig.custom().setConnectTimeout(1000)
                      .setSocketTimeout(1000).setConnectionRequestTimeout(1000)
                      .build());
      put.addHeader("Accepts","application/xml");
      put.addHeader("Content-Type","application/xml");
    try
    {
      put.setEntity(new StringEntity(request));
    } catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    try
    {
      HttpResponse resp = client.execute(put);
      switch (resp.getStatusLine().getStatusCode())
      {
        case 200:
          try
          {
            return EntityUtils.toString(resp.getEntity());
          } catch (IOException e)
          {
            e.printStackTrace();
          }
        case 503:
          throw new ServiceUnavailableException();
        default:
          throw new RuntimeException("error sending request");
      }
    } catch(ConnectTimeoutException e){
      throw e;
    } catch (IOException e){
      throw new RuntimeException("could not send put " ,e);
    }
  }
}
