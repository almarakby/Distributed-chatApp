import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GetId_itf extends Remote{

    public int getId()throws RemoteException;
            
}