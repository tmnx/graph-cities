/*
 * TCSS 342
 * 
 * AdjMatrixDiGraph
 * 
 * Added: toString(), getVertices() to Simon Gray's original code
 * Formatted code to meet course coding conventions.
 */

package structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An implementation of the <code>Graph</code> interface for a directed graph
 * using an adjacency matrix to indicate the presence/absence of edges
 * connecting vertices in the graph.
 * 
 * @author Simon Gray
 * @author Alan Fowler
 * @version 1.1
 * 
 * @param <T>
 */
public class AdjMatrixDiGraph<T> implements Graph<T> {

    /**
     * The size of the digraph.
     */
    protected static final int SIZE = 10;

    /**
     * The number of vertices in the digraph.
     */
    protected int myNumberOfVertices;

    /**
     * The number of edges in the digraph.
     */
    protected int myNumberOfEdges;

    /**
     * adjMatrix[i][j] = 1; an edge exists FROM vertex i TO vertex j
     * adjMatrix[i][j] = 0; NO edge exists from vertex i to vertex j.
     */
    protected int[][] myAdjMatrix;

    /**
     * Stores the vertices that are part of this graph. There is no requirement
     * that the vertices be in adjacent cells of the array; as vertices are
     * deleted, some gaps may appear.
     */
    protected Vertex<T>[] myVertices;

    /**
     * v1Pos and v2Pos represent a position in adjMatrix. This class and
     * subclasses use it to access an edge.
     */
    protected int myV1Pos;
    
    /**
     * v1Pos and v2Pos represent a position in adjMatrix. This class and
     * subclasses use it to access an edge.
     */
    protected int myV2Pos;

    /**
     * Constructor. Create an empty instance of a directed graph.
     */
    public AdjMatrixDiGraph() {
        myNumberOfVertices = 0;
        myNumberOfEdges = 0;
        myAdjMatrix = new int[AdjMatrixDiGraph.SIZE][AdjMatrixDiGraph.SIZE];
        myVertices = new Vertex[AdjMatrixDiGraph.SIZE];
    }

    /**
     * Add an edge connecting vertex <code>v1</code> to <code>v2</code>. This
     * edge must not already be in the graph. In an undirected graph, this edge
     * is bidirectional.
     * 
     * @param v1 The source vertex; must not be <code>null</code> and must be a
     *            vertex in this graph.
     * @param v2 The destination vertex; must not be <code>null</code> and must
     *            be a vertex in this graph.
     * @throws IllegalArgumentException if <code>v1</code> or <code>v2</code>
     *             are <code>null</code>, are not in this graph or if the edge
     *             already exists in the graph.
     */
    @Override
    public void addEdge(final Vertex<T> v1, final Vertex<T> v2) {
        myV1Pos = getVerticesIndexFor(v1);
        myV2Pos = getVerticesIndexFor(v2);

        if (myV1Pos == -1 || myV2Pos == -1) {
            throw new IllegalArgumentException("vertex not found");
        }
        // avoid adding duplicate edges
        if (this.myAdjMatrix[myV1Pos][myV2Pos] == 0) {
            this.myAdjMatrix[myV1Pos][myV2Pos] = 1;
            this.myNumberOfEdges++;
        } else {
            throw new IllegalArgumentException("duplicate edge " + v1 + " " + v2);
        }
    }

    /**
     * Remove the edge from <code>v1</code> to <code>v2</code> from this graph.
     * 
     * @param v1 The source vertex of the edge to remove; must not be
     *            <code>null</code> and must be a vertex in this graph.
     * @param v2 The destination vertex of the edge to remove; must not be
     *            <code>null</code> and must be a vertex in this graph.
     * @throws IllegalArgumentException if <code>v1</code> or <code>v2</code>
     *             are <code>null</code>, are not in this graph, or the edge
     *             doesn't exist.
     */
    @Override
    public void removeEdge(final Vertex<T> v1, final Vertex<T> v2) {
        myV1Pos = getVerticesIndexFor(v1);
        myV2Pos = getVerticesIndexFor(v2);

        if (myV1Pos == -1 || myV2Pos == -1) {
            throw new IllegalArgumentException("vertex not found");
        }
        if (this.myAdjMatrix[myV1Pos][myV2Pos] == 1) {
            this.myAdjMatrix[myV1Pos][myV2Pos] = 0;
            this.myNumberOfEdges--;
        } else {
            throw new IllegalArgumentException("edge not found");
        }
    }

    /**
     * Remove vertex <code>v</code> and all edges incident on <code>v</code>
     * from this graph.
     * 
     * @param theVertex The vertex to remove; must not be <code>null</code> and must be
     *            a vertex in this graph.
     * @throws IllegalArgumentException if <code>v1</code> is <code>null</code>
     *             or is not in this graph.
     */
    @Override
    public void removeVertex(final Vertex<T> theVertex) {
        final int pos = getVerticesIndexFor(theVertex);
        if (pos == -1) {
            throw new IllegalArgumentException("vertex not found");
        }

        this.myNumberOfVertices--;
        this.myVertices[pos] = null;

        // now we need to go through the adjacency matrix and
        // remove all edges incident on v. We do this by walking 
        // along the row and column for v in the adjacency matrix
        for (int i = 0; i < myVertices.length; i++) {
            if (this.myAdjMatrix[pos][i] == 1) { // row check
                this.myAdjMatrix[pos][i] = 0;
                this.myNumberOfEdges--;
            }
            if (this.myAdjMatrix[i][pos] == 1) { // column check
                this.myAdjMatrix[i][pos] = 0;
                this.myNumberOfEdges--;
            }
        }
    }

    /**
     * Insert Vertex <code>v</code> into this graph.
     * 
     * @param theVertex The <code>Vertex</code> to add to the graph; can't already be in
     *            the graph
     * @throws IllegalArgumentException if <code>v</code> is already in the
     *             graph
     */
    @Override
    public void addVertex(final Vertex<T> theVertex) {
        final int posNeighborVertex = getVerticesIndexFor(theVertex);

        if (posNeighborVertex != -1) {
            throw new IllegalArgumentException("duplicate vertex " + theVertex);
        }
        final int posNewVertex = getFreeVertexPosition();
        myVertices[posNewVertex] = theVertex;
        this.myNumberOfVertices++;
    }

    /**
     * Get the neighbors of Vertex <code>v</code> in this graph.
     * 
     * @param theVertex Vertex The vertex whose neighbors we want; must not be
     *            <code>null</code> and must be a vertex in this graph.
     * @return Collection The vertices incident on <code>v</code>
     * @throws IllegalArgumentException if <code>element</code> is already in
     *             the graph or if <code>neighbor</code> is <code>null</code>.
     */
    @Override
    public List<Vertex<T>> getNeighbors(final Vertex<T> theVertex) {
        final int pos = getVerticesIndexFor(theVertex);
        if (pos == -1) {
            throw new IllegalArgumentException("vertex not found");
        }

        final List<Vertex<T>> neighbors = new ArrayList<Vertex<T>>();
        for (int i = 0; i < myVertices.length; i++) {
            if (this.myAdjMatrix[pos][i] == 1) {
                neighbors.add(myVertices[i]);
            }
        }

        return neighbors;
    }

    /**
     * Get the number of edges in this graph.
     * 
     * @return int The number of edges in this graph
     */
    @Override
    public int getNumberOfEdges() {
        return this.myNumberOfEdges;
    }

    /**
     * Get the number of vertices in this graph.
     * 
     * @return int The number of vertices in this graph
     */
    @Override
    public int getNumberOfVertices() {
        return this.myNumberOfVertices;
    }

    /**
     * Find the first free position in vertices.
     * 
     * @return int Index of the the first free position in vertices or -1 if
     *         there are none.
     */
    protected int getFreeVertexPosition() {
        int result = -1;
        for (int i = 0; i < myVertices.length; i++) {
            if (myVertices[i] == null) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Return the position of theVertex in myVertices.
     * 
     * @param theVertex Vertex Get <code>theVertex</code>'s index in the myVertices array.
     * @return The position of theVertex in myVertices, or -1 if it isn't there.
     */
    protected int getVerticesIndexFor(final Vertex<T> theVertex) {
        if (theVertex == null) {
            throw new IllegalArgumentException("null vertex");
        }
        int result = -1;
        for (int i = 0; i < myVertices.length; i++) {
            if (myVertices[i] != null && myVertices[i].equals(theVertex)) {
                result =  i;
                break;
            }
        }
        return result;
    }

    // Methods added for TCSS 342

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Adjacency:\n");
        for (int index = 0; index < myNumberOfVertices; index++) {
            sb.append(String.format("%15s ", myVertices[index]));
            sb.append(Arrays.toString(myAdjMatrix[index]));
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * @return a copy of the vertex array.
     */
    @Override
    public Vertex<T>[] getVertices() {
        return Arrays.copyOf(myVertices, myNumberOfVertices);
    }
}
