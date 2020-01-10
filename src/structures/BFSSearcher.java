/*
 * TCSS 342
 * 
 * AdjMatrixDiGraph
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Performs Breadth First Search.
 * 
 * @author Simon Gray
 * @author Alan Fowler
 * @version 1.1
 *
 * @param <T>
 */
public class BFSSearcher<T> {

    /**
     * A reference to the Graph in use.
     */
    private final Graph<T> myGraph;

    /**
     * The vertices visited so far in this search.
     */
    private final int[] myVisitedVertices;

    /**
     * A Mapping of vertex visit order in the search.
     */
    private Map<Vertex<T>, Vertex<T>> myVisited;

    /**
     * Initialize the search.
     * 
     * @param theGraph The graph to search.
     * @throws NullArgumentException if <code>theGraph</code> is <code>null</code>.
     */
    public BFSSearcher(final Graph<T> theGraph) {
        myGraph = theGraph;
        myVisitedVertices = new int[theGraph.getNumberOfVertices()];
    }

    /**
     * Determine if there is a path from <code>v1</code> to <code>v2</code> in
     * graph.
     * 
     * @param v1 The source vertex; must not be <code>null</code> and must be a
     *            vertex in this graph.
     * @param v2 The destination vertex; must not be <code>null</code> and must
     *            be a vertex in this graph.
     * @return <code>true</code> if there is a path from <code>v1</code> to
     *         <code>v2</code> in the graph.
     * @throws IllegalArgumentException if <code>v1</code> or <code>v2</code>
     *             are <code>null</code> or are not in this graph.
     */
    public boolean containsPath(final Vertex<T> v1, final Vertex<T> v2) {
        final List<Vertex<T>> verticesList = (ArrayList<Vertex<T>>) this.bfs(v1);
        return verticesList.contains(v2);
    }

    /**
     * Determine if the length of the path from <code>v1</code> to
     * <code>v2</code> in the graph.
     * 
     * @param v1 The source vertex; must not be <code>null</code> and must be a
     *            vertex in this graph.
     * @param v2 The destination vertex; must not be <code>null</code> and must
     *            be a vertex in this graph.
     * @return int The length of the path from <code>v1</code> to
     *         <code>v2</code>; 0 if there is no path
     * @throws IllegalArgumentException if <code>v1</code> or <code>v2</code>
     *             are <code>null</code> or are not in this graph.
     */
    public int getPathLength(final Vertex<T> v1, final Vertex<T> v2) {
        Vertex<T> temp = v2;
        int pathLength = 0;
        final List<Vertex<T>> verticesList = (ArrayList<Vertex<T>>) this.bfs(v1);

        if (!verticesList.contains(temp)) {
            return 0; // no path
        }

        Vertex<T> neighbor = myVisited.get(temp);
        while (neighbor != null) {
            pathLength++;
            temp = neighbor;
            neighbor = myVisited.get(temp);
        }
        pathLength--;
        return pathLength;
    }

    /**
     * Get the vertices on the path from <code>v1</code> to <code>v2</code> in
     * the graph.
     * 
     * @param v1 The source vertex; must not be <code>null</code> and must be a
     *            vertex in this graph.
     * @param v2 The destination vertex; must not be <code>null</code> and must
     *            be a vertex in this graph.
     * @return List A list of the vertices on the path from <code>v1</code> to
     *         <code>v2</code>. This list will be empty if there is no path.
     * @throws IllegalArgumentException if <code>v1</code> or <code>v2</code>
     *             are <code>null</code> or are not in this graph.
     */
    public List<Vertex<T>> getPath(final Vertex<T> v1, final Vertex<T> v2) {
        Vertex temp = v2;
        final List<Vertex<T>> thepath = new ArrayList<Vertex<T>>();
        final List<Vertex<T>> verticesList = (ArrayList<Vertex<T>>) this.bfs(v1);

        if (!verticesList.contains(temp)) {
            return thepath; // no path, return empty list
        }

        // a path exists; let's get it!
        thepath.add(temp);
        Vertex<T> neighbor;
        while ((neighbor = myVisited.get(temp)) != null) {
            if (!neighbor.getLabel().equals("")) {
                thepath.add(neighbor);
                // System.out.printf("Adding %s to the path\n", neighbor );
            }
            temp = neighbor;
        }

        java.util.Collections.reverse(thepath);
        return thepath;
    }

    /**
     * An implementation of Breadth First Search.
     * 
     * @param theStartVertex Vertex
     * @return List
     */
    public List<Vertex<T>> bfs(final Vertex<T> theStartVertex) {
        myVisited = new HashMap<Vertex<T>, Vertex<T>>();
        final List<Vertex<T>> verticesList = new ArrayList<Vertex<T>>();
        final Queue<Pair<Vertex<T>>> toVisitQueue = new ListQueue<Pair<Vertex<T>>>();

        verticesList.add(theStartVertex);
        myVisited.put(theStartVertex, new Vertex(""));

        // get the start vertex's neighbors and put them into the toVisitQueue
        List<Vertex<T>> neighbors
            = (ArrayList<Vertex<T>>) myGraph.getNeighbors(theStartVertex);
        for (final Vertex<T> neighbor : neighbors) {
            toVisitQueue.enqueue(new Pair<Vertex<T>>(theStartVertex, neighbor));
        }

        while (!toVisitQueue.isEmpty()) {
            final Pair<Vertex<T>> vertexPair = toVisitQueue.dequeue();
            final Vertex<T> src = vertexPair.getFirstElement();
            final Vertex<T> dest = vertexPair.getSecondElement();
            // skip over visited vertices
            if (myVisited.get(dest) == null) {
                verticesList.add(dest); // place in list to return
                myVisited.put(dest, src); // "mark" the vertex as visited

                // now queue up invisited neighbors
                neighbors = (ArrayList<Vertex<T>>) myGraph.getNeighbors(dest);
                for (final Vertex<T> neighbor : neighbors) {
                    // only queue up unvisited neighbors
                    if (myVisited.get(neighbor) == null) {
                        toVisitQueue.enqueue(new Pair<Vertex<T>>(dest, neighbor));
                    }
                }
            }
        }

        return verticesList;
    }
}
