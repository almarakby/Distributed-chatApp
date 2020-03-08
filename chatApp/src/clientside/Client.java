import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client implements RecieveMessage_itf {

    private String name;
    private int id;
    private List<Pair<String,String>> messagesHistory;
    
    public Client(String clientName){
        this.name = clientName;
        this.messagesHistory = Collections.synchronizedList(new ArrayList<Pair<String,String>>());

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

    public void recieveMessage(String from, String message) throws RemoteException {
            this.messagesHistory.add(new Pair<String,String>(from,message));
            updateChatdisplay(from, message);

    }
 
	public static void updateChatdisplay(String name, String message){
		System.out.println(name+": "+message);

	}   
}