
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

public class ChatClient  {

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
    String name,message;
	Scanner  inputScanner = new Scanner(System.in); 

	// System.out.println(register);
	
	// Get remote object reference
    Registry registry = LocateRegistry.getRegistry(host); 
    
	Join_itf joinChat = (Join_itf) registry.lookup("joinService");
	Leave_itf leaveChat = (Leave_itf) registry.lookup("leaveService");
	SendMessage_itf interact = (SendMessage_itf) registry.lookup("sendMessageService"); //cant find a name for this service  
	ChatHistory_itf chatHistory = (ChatHistory_itf) registry.lookup("chatHistoryService"); 
		
	System.out.println("please enter your name");
	name = inputScanner.nextLine();
	
	Client clientInfo = new Client(name);
	RecieveMessage_itf clientInfo_stub = (RecieveMessage_itf) UnicastRemoteObject.exportObject(clientInfo,0);
	// SetId_itf cleintId_stub = (SetId_itf) UnicastRemoteObject.exportObject(clientInfo,0);

	while(true){
		if(joinChat.joinServer(name,clientInfo_stub) != true){
			System.out.println("user name is already taken, please choose another user name");
			continue;
			// System.exit(1);
			}
		break;    
	}

	// int id = clientInfo.getId();
	System.out.println("welcome to the chat, to exit enter \"quitchat\"");
	System.out.println("to send a message to a all chat room members, please enter \"all:you message\"");
	System.out.println("to send a message to a a specific chat room members, please enter \"member name:you message\"");
	System.out.println("to retreive chat history, please enter \"chathistory\"");;
	while(true){
		

	message = inputScanner.nextLine();
	// System.out.println("debugging: "+message);
	if (message.equals("quitchat")){
		leaveChat.leaveChat(name);
		System.exit(0);
		// break;
		}

	if (message.equals("chathistory")){
		
		//todo: do you add message to the history or not ? 
		List<Pair<String, String>> retreivedChatHistory = chatHistory.getChatHistory(name);
		
		if(retreivedChatHistory == null){
			System.out.println("you are not registered in the chat room, please register first");
		}
		else{
			for(Pair<String,String> nameMessagePair: retreivedChatHistory){
				Client.updateChatdisplay(nameMessagePair.getP1(), nameMessagePair.getP2());
			}
		}
		continue;
	}

	
	String[] parsedMessage = message.split(":");
	if (parsedMessage.length < 2) {
		System.out.println("Please enter the correct format for sending a message");
		name = inputScanner.nextLine();
		continue;
	}
	parsedMessage[0] = parsedMessage[0].replaceAll("\\s", "");


	if(parsedMessage[0].equals("all")){
		interact.sendMessage(name,"all",parsedMessage[1]);

	}
	else if(parsedMessage[0].equals("all") == false){

		if(interact.sendMessage(name,parsedMessage[0],parsedMessage[1]) != true){
			System.out.println("the user is not in the chat room");
			}
		}
		else{
			// System.out.println("Please enter the correct format for sending a message");
			continue;
		}
		}
	
		
	} catch (Exception e)  {
		System.err.println("Error on client: " + e);
	}

	}

}