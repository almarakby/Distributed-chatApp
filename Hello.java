import java.rmi.*;

public interface Hello extends Remote {
	public String sayHello1(String clientName) throws RemoteException;
	public String sayHello2(Info_itf client)  throws RemoteException; //remoteException
}