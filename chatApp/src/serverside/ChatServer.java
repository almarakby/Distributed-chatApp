import java.rmi.server.*;
import java.rmi.registry.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Iterator;
import chatApp.clientside.*;

public class ChatServer implements Join_itf, Leave_itf, SendMessage_itf {

	
	private static HashMap<Integer,RecieveMessage_itf> clientList = new HashMap<Integer,RecieveMessage_itf>();// TODO: this representation ? 
	private static Random randomGenerator = new Random();
	private static ArrayList<Pair<String, String>> messageHistory = new ArrayList<Pair<String, String>>();

	public static void  main(String [] args) {
	  try {
		 
		ChatServer joinService = new ChatServer();
		Join_itf joinServeice_stub = (Join_itf) UnicastRemoteObject.exportObject(joinService,0);
		
		ChatServer leaveService = new ChatServer();
		Leave_itf leaveServeice_stub = (Leave_itf) UnicastRemoteObject.exportObject(leaveService,0);

		ChatServer sendMessageService = new ChatServer();
		SendMessage_itf sendMessageService_stub = (SendMessage_itf) UnicastRemoteObject.exportObject(sendMessageService,0);


		Registry registry= LocateRegistry.getRegistry(); 
	    registry.bind("joinService", joinServeice_stub);
	    registry.bind("leaveService", leaveServeice_stub);
	    registry.bind("sendMessageService", sendMessageService_stub);

	    System.out.println ("Server ready");

	  } catch (Exception e) {
		  System.err.println("Error on server :" + e) ;
		  e.printStackTrace();
	  }
  }

	public static void broadcastMessage(String message, int id) {
		Iterator clientListIterator = clientList.entrySet().iterator();

		while (clientListIterator.hasNext()) {
			Map.Entry<Integer,RecieveMessage_itf> client = (Map.Entry<Integer,RecieveMessage_itf>) clientListIterator.next();  

			if(client.getKey()==id) continue;		  
			else{
				client.getValue().recieveMessage(new Pair<String,String>(name,message));
			}
	  }	
	}

  public static int generateId(){
	return randomGenerator.nextInt(100);
  }
	@Override
	public boolean SendMessage(String message,String name, int id) throws RemoteException {

		// todo: 0- check if the client is in the list
		if(!clientList.containsKey(id)){
			System.out.printf("client %d is not registered\n", id);
			return false;
		}
		// todo: 1- add message to history
		messageHistory.add(new Pair<String,String>(message,name));

		

		// todo: 2- broadcast message
		broadcastMessage(message, id);


		return true;
	}

	@Override
	public void leaveChat(int id) throws RemoteException {
		clientList.remove(id);
	}

	@Override
	public boolean joinServer(String name, RecieveMessage_itf client,SetId_itf idSetter) throws RemoteException {
		
		//todo: case when more than 100 clients already existed
		//also when client leaves
		int newId = generateId();
		idSetter.setId(newId);
		clientList.put(newId,client);
		return true;
	}

	}



