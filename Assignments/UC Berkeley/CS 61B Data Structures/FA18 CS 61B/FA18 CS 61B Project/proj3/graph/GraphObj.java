package graph;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Kidong Kim
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        vertexsize = 0;
        maxvertexsize = 0;
    }

    @Override
    public int vertexSize() {
        return vertexsize;
    }

    @Override
    public int maxVertex() {
        return maxvertexsize;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        return 0;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return false;
    }

    @Override
    public boolean contains(int u, int v) {
        return false;
    }

    @Override
    public int add() {
        vertexsize++;
        maxvertexsize++;
        return 0;
    }

    @Override
    public int add(int u, int v) {
        return 0;
    }

    @Override
    public void remove(int v) {
    }

    @Override
    public void remove(int u, int v) {
    }

    @Override
    public Iteration<Integer> vertices() {
        return null;
    }

    @Override
    public Iteration<Integer> successors(int v) {
        return null;
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        return null;
    }

    @Override
    protected void checkMyVertex(int v) {
    }

    @Override
    protected int edgeId(int u, int v) {
        return 0;
    }

    /** keep track of vertex size. */
    private int vertexsize;

    /** keep track of the max vertex size. */
    private int maxvertexsize;


}
