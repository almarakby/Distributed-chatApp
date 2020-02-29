

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class HelloClient  {

 public String getName() throws RemoteException{
	 return "Hugo";
 }
  public static void main(String [] args) {
	
	try {
	  if (args.length < 2) {
	   System.out.println("Usage: java HelloClient <rmiregistry host> <reg | dontreg>");
	   return;}
	
	   
	//    if( !args[1].equalsIgnoreCase("dontreg") ||  !args[1].equalsIgnoreCase("reg")){
	// 	   System.out.println("Usage: to register the client user <reg>, if not use <dontreg>");
	// 	   return;}
		   
		   
	String host = args[0];
	String register = new String(args[1]);
	// System.out.println(register);
	
	// Get remote object reference
	Registry registry = LocateRegistry.getRegistry(host); 
	Hello h = (Hello) registry.lookup("HelloService");
	Hello2 h2 = (Hello2) registry.lookup("Hello2Service");
	Registry_itf registerService = (Registry_itf) registry.lookup("registerservice");
	//create getname remote object
	Client clientInfo = new Client("Mohammed");
	clientInfo.setId(2);
	Info_itf clientInfo_stub = (Info_itf) UnicastRemoteObject.exportObject(clientInfo,0);
	// Remote method invocation
	// String res = h.sayHello1("Mohammed");
		
	String res = h.sayHello2(clientInfo_stub);

	if(register.equalsIgnoreCase("reg")){
		System.out.println(register);
		registerService.register(clientInfo);

	}

	for(int i =0;i<20;i++){
		h2.sayHello2(clientInfo);
	}

	System.out.println(res);
	
	} catch (Exception e)  {
		System.err.println("Error on client: " + e);
	}
  }
}