package chatApp.serverside;

/*
 * Pair.java: Helper class to create tuples, 
 * It has the worst naming convetion, I know sorry for that.
 *  */

public class Pair<T1,T2>{

    private T1 p1;
    private T2 p2;

    public Pair(T1 p1,T2 p2){
        this.p1 = p1;
        this.p2 = p2;
    }


}