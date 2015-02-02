import java.rmi.registry.* ;
import java.rmi.RemoteException ;
import java.rmi.server.UnicastRemoteObject ;

public class RMServer implements RemoteServer {

      public String getId(String s) throws RemoteException {
        System.out.println("A client said: " + s) ;
        return "Server replying" ;
      }

      public static void main(String[] args) {
         try {
           RMServer ms = new RMServer() ;
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
