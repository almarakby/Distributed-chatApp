

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class chatClient  {

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
    
	Join_itf joinChat = (Join_itf) registry.lookup("joinService");
	Leave_itf leaveChat = (Leave_itf) registry.lookup("leaveService");
	SendMessage_itf sendMessage = (Registry_itf) registry.lookup("sendMessageService");
	//create getname remote object
    
    
    
    System.out.println("please enter your name");
    String name;
    //TODO: scan clients name:
	Client clientInfo = new Client(name);
	RecieveMessage_itf client_stub = (RecieveMessage_itf) UnicastRemoteObject.exportObject(clientInfo,0);
	int id = joinChat.joinServer(name,client_stub);
	
	
		
	} catch (Exception e)  {
		System.err.println("Error on client: " + e);
	}
  }
}