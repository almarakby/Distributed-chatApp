import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Accounting_itf extends Remote {
    public void numberOfCalls(int number) throws RemoteException; 
    public  int getId() throws RemoteException;
}
