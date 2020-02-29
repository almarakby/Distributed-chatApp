
import java.rmi.*; 
import java.rmi.server.*; 
import java.rmi.registry.*;

public class HelloServer {
 

  public static void  main(String [] args) {
	  try {
		  // Create a Hello remote object
	    HelloImpl h = new HelloImpl ("Hello world !");
	    Hello h_stub = (Hello) UnicastRemoteObject.exportObject(h, 0); //NOTE: this is the remote reference of the object 

		ClientRegister registeredCLients = new ClientRegister();
		Registry_itf clientRegister_stub = (Registry_itf) UnicastRemoteObject.exportObject(registeredCLients,0);

		// Register the remote object in RMI registry with a given identifier
	    Registry registry= LocateRegistry.getRegistry(); 
	    registry.bind("HelloService", h_stub);
		registry.bind("registerservice",clientRegister_stub);
		
	    System.out.println ("Server ready");

	  } catch (Exception e) {
		  System.err.println("Error on server :" + e) ;
		  e.printStackTrace();
	  }
  }
}
