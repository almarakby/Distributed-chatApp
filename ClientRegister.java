import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;


public class ClientRegister implements Registry_itf{

    private HashMap<Integer,Integer> clientList = new HashMap<Integer,Integer>();

    @Override
    public void register(Accounting_itf client) throws RemoteException {
        clientList.put(client.getId(), 0);

    }
    //todo: get this outside to the server class
    public boolean searchRegistry(int id) {
        
        for (Map.Entry<Integer,Integer> mapElement : clientList.entrySet()) {
            if((Integer) mapElement.getKey() == id){
                return true;
            }  
        }

        return false;
    }

    public int incrementNumCalls(int id){
        /* 
            increment the number of calls per client, if the number of 
            calls is in order of magnitude of 10, we return the number of calls,
            else we return 0.
        */
        int numCalls = clientList.get(id);
        clientList.put(id, numCalls+1);
        if ((numCalls+1) % 10 ==0){
            return numCalls ;
        } 
        else{
            return 0;
        }
    }



    
}