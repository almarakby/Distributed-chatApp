import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RecieveMessage_itf extends Remote {

    public void recieveMessage(String message) throws RemoteException;
}