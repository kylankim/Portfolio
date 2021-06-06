
/** Disjoint sets of contiguous integers that allows (a) finding whether
 *  two integers are in the same _intSets and (b) unioning two sets together.
 *  At any given time, for a structure partitioning the integers 1 to N, 
 *  into sets, each _intSets is represented by a unique member of that
 *  _intSets, called its representative.
 *  @author Kidong Kim
 */
public class UnionFind {


    private int[] _intSets;

    /** A union-find structure consisting of the sets { 1 }, { 2 }, ... { N }.
     */
    public UnionFind(int N) {
        _intSets = new int[N+1];
    }

    /** Return the representative of the _intSets currently containing V.
     *  Assumes V is contained in one of the sets.  */
    public int find(int v) {
        if (_intSets[v] == 0 || _intSets[v] == v) {
            return v;
        }
        return _intSets[v] = find(_intSets[v]);  // FIXME
    }

    /** Return true iff U and V are in the same _intSets. */
    public boolean samePartition(int u, int v) {
        return find(u) == find(v);
    }

    /** Union U and V into a single _intSets, returning its representative. */
    public int union(int u, int v) {
        int tmp = find(v);
        _intSets[tmp] = find(u);
        return _intSets[tmp];
    }

}
