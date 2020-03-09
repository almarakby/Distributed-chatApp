import java.io.Serializable;

/**
 * Triplet: triplet datastructure jelper class
 * 
 */

public class Triplet<T1,T2,T3> implements Serializable{//serializable here is used to
                                                    // allow the datastructure to 
                                                    //be sent over the network,
                                                    //but I am using RMIs for that.

    private static final long serialVersionUID = 1L;
    private T1 p1;
    private T2 p2;
    private T3 p3;

    public Triplet(T1 p1,T2 p2, T3 p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public T1 getP1(){
        return this.p1;
    }

    public T2 getP2(){
        return this.p2;
    }
    public T3 getP3(){
        return this.p3;
    }


}
