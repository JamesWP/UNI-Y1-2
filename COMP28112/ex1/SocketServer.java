import java.io.* ;
import java.net.* ;

class SocketServer {
/* Sets up a server on port 8181 which you can telnet to.
 * If you send a message, it will reply and then disconnect.
*/

public static void main(String [] args) throws IOException {
  final int port = 8181 ;
  if(args.length<1){System.out.println("You must specify a name"); return;}
  final String serverName = args[0];

  ServerSocket listener = new ServerSocket(port) ;
  System.out.println("Server is running") ;
  // Listen for clients ....
  while (true) {
    Socket client = listener.accept() ;
    new SessionHandler(client,serverName).start() ;
    }
  }
}

class SessionHandler extends Thread {
// an instance created by the server for each client 

private BufferedReader in;
private PrintWriter out;
private Socket client;
private String serverName;

  SessionHandler(Socket s, String serverName) {
    client = s;
    this.serverName = serverName; 
    }

  public void run() {
    try {
      in = new BufferedReader(new InputStreamReader(
         client.getInputStream())) ;
      out = new PrintWriter(client.getOutputStream(), true) ;
      String input = in.readLine();
      
      if(input.equals("whoRU")){
        out.println("Scince you asked so nicley, its " + serverName);
      }else{
        out.println("If you ask me nicely, I will tell you who I am");
      }
      System.out.println("A client said: " + input) ;
    } catch (Exception e) { System.out.println("Server error " + e) ; }
    try { client.close() ; }
    catch (Exception e) {;}
  }
}

