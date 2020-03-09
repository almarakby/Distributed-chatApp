import java.rmi.server.*;
import java.rmi.registry.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
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

public class ChatServer implements Join_itf, Leave_itf, SendMessage_itf, ChatHistory_itf {

	private static ConcurrentHashMap<String, RecieveMessage_itf> clientList = new ConcurrentHashMap<String, RecieveMessage_itf>();
	private static List<Triplet<String, String, String>> messageHistory = Collections.synchronizedList(new ArrayList<Triplet<String, String, String>>());
	private static Random randomGenerator = new Random();

	private static File chatHistoryFile = new File("chatHistory.txt");

	private static BufferedReader chatHistoryReader;

	private static BufferedWriter chatHistoryWriter;

	public static void main(String[] args) {

		if (!chatHistoryFile.exists()) {
			try{
				chatHistoryFile.createNewFile();
			}
			catch (Exception e) {
				System.err.println("Error on server :" + e);
				e.printStackTrace();
			}
		}


		try{
			chatHistoryReader =  new BufferedReader(new FileReader(chatHistoryFile));
			chatHistoryWriter = new BufferedWriter(new PrintWriter(new FileWriter(chatHistoryFile, true)));
			loadChatHistory();

			ChatServer joinService = new ChatServer();
			Join_itf joinServeice_stub = (Join_itf) UnicastRemoteObject.exportObject(joinService, 0);

			ChatServer leaveService = new ChatServer();
			Leave_itf leaveServeice_stub = (Leave_itf) UnicastRemoteObject.exportObject(leaveService, 0);

			ChatServer sendMessageService = new ChatServer();
			SendMessage_itf sendMessageService_stub = (SendMessage_itf) UnicastRemoteObject
					.exportObject(sendMessageService, 0);

			ChatServer chatHistoryService = new ChatServer();
			ChatHistory_itf chatHistoryService_stub = (ChatHistory_itf) UnicastRemoteObject
					.exportObject(chatHistoryService, 0);

			Registry registry = LocateRegistry.getRegistry();
			registry.bind("joinService", joinServeice_stub);
			registry.bind("leaveService", leaveServeice_stub);
			registry.bind("sendMessageService", sendMessageService_stub);
			registry.bind("chatHistoryService", chatHistoryService_stub);
			System.out.println("Server ready");

		} catch (Exception e) {
			System.err.println("Error on server :" + e);
			e.printStackTrace();
		}
	}

	private static void loadChatHistory() {
		//todo: readline, split on ":", add to chat history
		
		String  line;
		String[] parsedMessage;
		try{
			while((line = chatHistoryReader.readLine()) != null){

				parsedMessage = line.split(":");
					
				if(parsedMessage.length <3){
					continue;
				}

				messageHistory.add(new Triplet<String,String,String>(parsedMessage[0].replaceAll("\\s", ""),
																	parsedMessage[1].replaceAll("\\s", ""),
																	parsedMessage[2].replaceAll("\\s", "")));
			

			}
		}
		catch(Exception e){
			System.err.println("Error on server :" + e);
			e.printStackTrace();
		}
	}

	public static void redirectMessage(String from, String to, String message) {

		if (!(to.equals("all"))) {

			try {
				clientList.get(to).recieveMessage(from, message);
			} catch (Exception e) {
				System.err.println("Error on server :" + e);
				e.printStackTrace();
			}
		}

		else {

			Iterator clientListIterator = clientList.entrySet().iterator();

			synchronized (clientList) {

				while (clientListIterator.hasNext()) {

					Map.Entry<String, RecieveMessage_itf> client = (Map.Entry<String, RecieveMessage_itf>) clientListIterator
							.next();

					if (client.getKey().equals(from))
						continue;
					else {
						try {
							client.getValue().recieveMessage(from, message);
						} catch (Exception e) {
							System.err.println("Error on server :" + e);
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public static int generateId() {
		return randomGenerator.nextInt(100);
	}

	@Override
	public boolean sendMessage(String from, String to, String message) throws RemoteException,IOException {

		if (!clientList.containsKey(from)) {
			System.out.printf("client %s is not registered\n", from);
			return false;
		}

		if (!clientList.containsKey(to)) {
			if (to.equals("all") == false) {
				System.out.printf("client %s is not in the chat room\n", to);
				return false;
			}
		}

		if (to.equals("all")) {

			messageHistory.add(new Triplet<String, String, String>(from, "all", message));
		} else {
			messageHistory.add(new Triplet<String, String, String>(from, to, message));

		}

		chatHistoryWriter.append(from);
		chatHistoryWriter.append(":");
		chatHistoryWriter.append(to);
		chatHistoryWriter.append(":");
		chatHistoryWriter.append(message);
		chatHistoryWriter.newLine();
		chatHistoryWriter.flush();
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

	@Override
	public List<Pair<String, String>> getChatHistory(String name) throws RemoteException {
		
		if(!clientList.containsKey(name)){
			System.out.printf("client %s is not registered\n", name);
			return null;	
		}
		 List<Pair<String,String>> messageHistoryRetreived =  Collections.synchronizedList(new ArrayList<Pair<String,String>>());

		 for(Triplet<String,String,String> messageTriplet: messageHistory){
			if(messageTriplet.getP2().equals(name) || messageTriplet.getP2().equals("all") ){
				messageHistoryRetreived.add(new Pair<String,String>(messageTriplet.getP1(),messageTriplet.getP3()));
			}
		}

		return messageHistoryRetreived;
	}

	}



