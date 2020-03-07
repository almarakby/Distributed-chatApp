package chatApp.clientside;

import java.rmi.RemoteException;
import java.util.ArrayList;
// import  Pair.Pair;
public class Client implements GetId_itf, RecieveMessage_itf, SetId_itf {

    private String name;
    private int id;
    private ArrayList<Pair<String,String>> messagesHistory; //FIXME:  add the jar file for pair class
    
    public Client(String clientName){
        this.name = clientName;
        this.messagesHistory = new ArrayList<Pair<String,String>>();

    }    

    public String getName(){
        return this.name;
    }

    public int getId() throws RemoteException{
        return this.id;
    } 

    public void setId(int id){
        this.id = id;
    }

    public void recieveMessage(Pair message) throws RemoteException {
            this.messagesHistory.add(message);
            updateChatdisplay(ma, message);

    }
 
	public static void updateChatdisplay(String name, String message){
		System.out.println(name+message);

	}   
}