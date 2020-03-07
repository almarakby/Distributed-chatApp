import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SendMessage_itf extends Remote {

    public boolean SendMessage(String message,String name,int id) throws RemoteException;
}