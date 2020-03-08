import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Leave_itf extends Remote {

    public void leaveChat(String name) throws RemoteException;
}