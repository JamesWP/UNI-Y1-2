import java.rmi.registry.* ;
import java.rmi.RemoteException ;
import java.rmi.server.UnicastRemoteObject ;

public class RMServer implements RemoteServer {

      public final String serverName;
      public RMServer(String serverName){
        this.serverName = serverName;
      }

      public String getId(String s) throws RemoteException {
        if(s.equals("whoRU")){
          return "Scince you asked so nicley, its " + serverName;
        }else{
          return "If you ask me nicely, I will tell you who I am";
        }
      }

      public static void main(String[] args) {
         try {
           if(args.length<1) {System.out.println("You must specify a name for the server"); return;}
           RMServer ms = new RMServer(args[0]) ;
           RemoteServer stub = (RemoteServer) UnicastRemoteObject.exportObject(ms, 0) ;

           Registry registry = LocateRegistry.getRegistry() ;
           registry.rebind("myIDserver", stub) ;
           System.out.println("Server is running") ;
         } catch (Exception e) {
           System.out.println("Server failed with exception " + e) ;
           System.exit(1) ;
         }
      }
}
