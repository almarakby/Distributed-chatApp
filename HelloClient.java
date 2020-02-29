

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class HelloClient  {

 public String getName() throws RemoteException{
	 return "Hugo";
 }
  public static void main(String [] args) {
	
	try {
	  if (args.length < 1) {
	   System.out.println("Usage: java HelloClient <rmiregistry host>");
	   return;}


	String host = args[0];

	// Get remote object reference
	Registry registry = LocateRegistry.getRegistry(host); 
	Hello h = (Hello) registry.lookup("HelloService");
	
	//create getname remote object
	Client clientInfo = new Client("Mohammed");
	Info_itf clientInfo_stub = (Info_itf) UnicastRemoteObject.exportObject(clientInfo,0);
	// Remote method invocation
	// String res = h.sayHello1("Mohammed");

	String res = h.sayHello2(clientInfo_stub);
	System.out.println(res);

	} catch (Exception e)  {
		System.err.println("Error on client: " + e);
	}
  }
}