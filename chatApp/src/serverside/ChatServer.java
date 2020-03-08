import java.rmi.server.*;
import java.rmi.registry.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Iterator;
import java.util.List;

public class ChatServer implements Join_itf, Leave_itf, SendMessage_itf {

	
	private static ConcurrentHashMap<String,RecieveMessage_itf> clientList = new ConcurrentHashMap<String,RecieveMessage_itf>(); 
	private static List<Pair<String, String>> messageHistory = Collections.synchronizedList(new ArrayList<Pair<String, String>>());
	private static Random randomGenerator = new Random();

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

	public static void redirectMessage(String from,String to, String message) {

		if(to != null){
		
		try{
			clientList.get(to).recieveMessage(from,message);
		} catch (Exception e) {
			System.err.println("Error on server :" + e) ;
			e.printStackTrace();
		}

		return;
		}

		Iterator clientListIterator = clientList.entrySet().iterator();

		synchronized(clientList){

			while (clientListIterator.hasNext()) {
				
				Map.Entry<String,RecieveMessage_itf> client = (Map.Entry<String,RecieveMessage_itf>) clientListIterator.next();  

				if(client.getKey().equals(from)) continue;		  
				else{
					try{
					client.getValue().recieveMessage(from,message);
					} catch (Exception e) {
						System.err.println("Error on server :" + e) ;
						e.printStackTrace();
					}
				}
			}	
		}
	}

  public static int generateId(){
	return randomGenerator.nextInt(100);
  }
	
  @Override
	public boolean sendMessage(String from,String message) throws RemoteException {

		// todo: 0- check if the client is in the list
		if(!clientList.containsKey(from)){
			System.out.printf("client %s is not registered\n", from);
			return false;
		}
		// todo: 1- add message to history
		messageHistory.add(new Pair<String,String>(from,message));

		

		// todo: 2- broadcast message
		redirectMessage(from,null,message);


		return true;
	}

	@Override
	public boolean sendMessage(String from,String to, String message) throws RemoteException
	{

		if(!clientList.containsKey(from)){
			System.out.printf("client %s is not registered\n", from);
			return false;
		}


		if(!clientList.containsKey(to)){
			System.out.printf("client %s is not in the chat room\n", to);
			return false;
		}

		// todo: 1- add message to history
		messageHistory.add(new Pair<String,String>(from,message));

		

		// todo: 2- broadcast message
		redirectMessage(from,to,message);


		return true;
	}

	@Override
	public void leaveChat(String name) throws RemoteException {
		clientList.remove(name);
	}

	@Override
	public boolean joinServer(String name, RecieveMessage_itf client) throws RemoteException {
		
		//todo:also when client leaves
		// int newId = generateId();
		// idSetter.setId(newId);
		if(clientList.containsKey(name)){
			return false;
		}
		clientList.put(name,client);
		return true;
	}

	}



