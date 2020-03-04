
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

    public int getId() throws RemoteException{
        return this.id;
    } 

    public void setId(int id){
        this.id = id;
    }

    public void recieveMessage(Pair message) throws RemoteException {
            messagesHistory.put(message);
    }

   
}