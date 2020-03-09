import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatHistory_itf extends Remote{
    public List<Pair<String,String>> getChatHistory(String name) throws RemoteException;
}