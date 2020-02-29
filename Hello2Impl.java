import java.rmi.*;


public class Hello2Impl implements Hello2{

    private String message;
    private ClientRegister registeredCLients ; 
 
	public Hello2Impl(String s) {
        message = s ;
        registeredCLients = new ClientRegister();
	}
    
    
    @Override
    public String sayHello2(Accounting_itf client) throws RemoteException {
        
        int clientId = client.getId();
        int numCalls=0;
        if(registeredCLients.searchRegistry(clientId) == false){
            System.out.printf("client %d is not registered", clientId);
            return null;
        }

        if ((numCalls = registeredCLients.incrementNumCalls(clientId)) > 0){
                client.numberOfCalls(numCalls);
        }
        
        return message;

    }
    
}