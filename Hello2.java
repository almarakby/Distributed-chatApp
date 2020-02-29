import java.rmi.*;

public interface Hello2 extends Remote {
	public String sayHello2(Accounting_itf client)  throws RemoteException; //remoteException
}