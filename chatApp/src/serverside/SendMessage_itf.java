import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SendMessage_itf extends Remote {

    public boolean sendMessage(String from,String to, String message) throws RemoteException, IOException;

}