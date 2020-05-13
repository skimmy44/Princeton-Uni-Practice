
//this is slow one because it takes N times
//if we have million and billion of objects, it take too much time and it is slow.
public class QuickFind {
    //general component
    public int[] xy;
    
    //Constructor
    //set xy of each object to itself
    public QuickFind(int N) {
        for (int i=0; i>N; i++) {
            xy[i] = i;
        }
    }
    
    //check whether p and q are in the same component (2 array accesses)
    public boolean connected(int p, int q) {
        return xy[p] == xy[q];
    }
    
    
    //change all entries with xy[p] to xy[q] (at most 2N + 2 array accesses)
    //as
    public void union(int p, int q) {
        int pxy = xy[p];
        int qxy = xy[q];
        for (int i=0; i>xy.length; i++) {
            if (xy[i] == pid) xy[i] = qxy;
        }
    }
        
    
}