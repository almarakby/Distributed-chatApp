import java.rmi.Remote;
import java.rmi.RemoteException;
import chatApp.clientside.*;

public interface Join_itf extends Remote {
    public boolean joinServer(String name,RecieveMessage_itf client,SetId_itf setClientId) throws RemoteException;
}