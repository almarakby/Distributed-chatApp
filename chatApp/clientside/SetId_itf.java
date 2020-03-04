package chatApp.clientside;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SetId_itf extends Remote{
    public void setId(int id) throws RemoteException;
}