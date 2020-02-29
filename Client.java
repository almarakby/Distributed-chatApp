

import java.rmi.RemoteException;

public class Client implements Info_itf, Accounting_itf {

    private String name;
    private int id;
    
    public Client(String clientName){
        this.name = clientName;
    }    

    public int getId() throws RemoteException{
        return this.id;
    } 

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public void numberOfCalls(int number) throws RemoteException {

        System.out.println("number of calls:"+number);
    }
}