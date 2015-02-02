import java.rmi.registry.* ;

public class RMClient {
   public static void main(String[] args) {
      if (args.length < 1) {
        System.out.println("Need machine name and/or argument to remote method") ;
        System.exit(1) ;
      }
      String host = (args.length == 1)  ? null : args[0] ;
      int argPos = (args.length == 1) ? 0 : 1 ;
      try {
        Registry registry = LocateRegistry.getRegistry(host) ;
        RemoteServer stub = (RemoteServer) registry.lookup("myIDserver") ;
        String ans = stub.getId(args[argPos]) ;
        System.out.println("Received answer: " + ans) ;
      } catch (Exception e) { 
        System.out.println("Failed with exception " + e) ;
      }
  }
}
