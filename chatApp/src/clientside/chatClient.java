package chatApp.clientside;


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
		   `
		   
	String host = args[0];
	String register = new String(args[1]);
	// System.out.println(register);
	
	// Get remote object reference
    Registry registry = LocateRegistry.getRegistry(host); 
    
	Join_itf joinChat = (Join_itf) registry.lookup("joinService");
	Leave_itf leaveChat = (Leave_itf) registry.lookup("leaveService");
	SendMessage_itf interact = (Registry_itf) registry.lookup("sendMessageService"); //cant fnd a name for this service  
	
		
    System.out.println("please enter your name");
    String name;
    //TODO: scan clients name:
	Client clientInfo = new Client(name);
	RecieveMessage_itf clientInfo_stub = (RecieveMessage_itf) UnicastRemoteObject.exportObject(clientInfo,0);
	SetId_itf cleintId_stub = (SetId_itf) UnicastRemoteObject.exportObject(clientInfo,0);

    if(joinChat.joinServer(name,clientInfo_stub,cleintId_stub) != true){
		System.out.println("unable to join server");
		System.exit(1);
	}    
	
	int id = clientInfo.getId();
	while(true){
	//TODO: scan client's message

	String message;
	if (message.equals("quitchat")){
		leaveChat.leaveChat(id);
		break;
		}
	if (interact.sendMessage(message,name,id) != true){
		System.out.println("message was unable to send");
		continue;
		}
	clientInfo.updateChatdisplay(name, message);
	
	}
		
	} catch (Exception e)  {
		System.err.println("Error on client: " + e);
	}

	}

}