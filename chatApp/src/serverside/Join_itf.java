import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Join_itf extends Remote {
    public boolean joinServer(String name,RecieveMessage_itf client) throws RemoteException;
}