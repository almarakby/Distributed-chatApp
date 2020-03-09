import java.io.Serializable;

// package chatApp.src.serverside;

/*
 * Pair.java: Helper class to create tuples, 
 * It has the worst naming convetion, I know sorry for that.
 *  */

public class Pair<T1,T2> implements Serializable{ //serializable here is used to
                                                  // allow the datastructure to 
                                                  //be sent over the network,
                                                  //but I am using RMIs for that.

    private static final long serialVersionUID = 1L;
    private T1 p1;
    private T2 p2;

    public Pair(T1 p1,T2 p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    public T1 getP1(){
        
        return this.p1;
    }

    public T2 getP2(){

        return this.p2;
    }
}