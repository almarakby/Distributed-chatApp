
import java.rmi.server.*;
import java.rmi.registry.*;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

public class HelloServer implements Registry_itf,Hello2{
 
	private static HashMap<Integer,Integer> clientList = new HashMap<Integer,Integer>();
	
	private String message;

	public HelloServer(String s){
		message = s;
	}

  public static void  main(String [] args) {
	  try {
		  // Create a Hello remote object
	    HelloImpl h = new HelloImpl ("Hello world from hello service !");
	    Hello h_stub = (Hello) UnicastRemoteObject.exportObject(h, 0); //NOTE: this is the remote reference of the object 

		
		HelloServer clientRegister = new HelloServer("Hello world form registery service!"); //TODO: can i do this ?
		Registry_itf clientRegister_stub = (Registry_itf) UnicastRemoteObject.exportObject(clientRegister,0);


		HelloServer h2 = new HelloServer("Hello world from Hello2 service !"); //TODO: can i do this ?
		Hello2 h2_stub = (Hello2) UnicastRemoteObject.exportObject(h2,0);

		// Register the remote object in RMI registry with a given identifier
	    Registry registry= LocateRegistry.getRegistry(); 
	    registry.bind("HelloService", h_stub);
		registry.bind("registerservice",clientRegister_stub);
		registry.bind("Hello2Service",h2_stub);

	    System.out.println ("Server ready");

	  } catch (Exception e) {
		  System.err.println("Error on server :" + e) ;
		  e.printStackTrace();
	  }
  }

  	// quick fix: initialize clientTegister class inside server
	@Override
	public void register(Accounting_itf client) throws RemoteException {
		int clientId = client.getId();
		System.out.printf("new client with id : %d registered\n",clientId);
		clientList.put(clientId, 0);

		

	}

	

	public int incrementNumCalls(int id){
		/* 
			increment the number of calls per client, if the number of 
			calls is in order of magnitude of 10, we return the number of calls,
			else we return 0.
		*/
		int numCalls = clientList.get(id);
		clientList.put(id, numCalls+1);
		if ((numCalls+1) % 10 ==0){
			return numCalls+1 ;
		} 
		else{
			return 0;
		}
	}

	@Override
	public String sayHello2(Accounting_itf client) throws RemoteException {
   
        int clientId = client.getId();
        int numCalls=0;

		if(!clientList.containsKey(clientId)){
            System.out.printf("client %d is not registered\n", clientId);
            return null;
		}



        if ((numCalls = incrementNumCalls(clientId)) > 0){
                client.numberOfCalls(numCalls);
        }
        
        return message;

	}

	}

