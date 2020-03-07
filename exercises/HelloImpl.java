
import java.rmi.*;

public  class HelloImpl implements Hello {

	private String message;
 
	public HelloImpl(String s) {
		message = s ;
	}


	public String sayHello2(Info_itf client) throws RemoteException {
		System.out.println("client's name is: "+client.getName()+" we are using the Info_itf");
		return message ;
	}

	public String sayHello1(String clientName) throws RemoteException {
		System.out.println("client's name is: "+clientName);
		return message;

	}
}

