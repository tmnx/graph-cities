/*
 * TCSS 342
 * 
 * DFSSearcher
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Performs Depth First Search.
 * 
 * @author Simon Gray
 * @author Alan Fowler
 * @version 1.1
 * 
 * @param <T>
 */
public class DFSSearcher<T> {

    private final Graph<T> myGraph;

    private Map<Vertex<T>, Vertex<T>> myVisited;

    private boolean myPathFound;

    /**
     * 
     * @param theGraph The graph to search.
     * @throws NullArgumentException if <code>g</code> is <code>null</code>.
     */
    public DFSSearcher(final Graph<T> theGraph) {
        this.myGraph = theGraph;
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
        doDFS(v1, v2);
        return myPathFound;
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
        Vertex temp = v2;
        doDFS(v1, temp);
        int pathLength = 0;
        if (myPathFound) {
            Vertex<T> neighbor;
            while ((neighbor = myVisited.get(temp)) != null) {
                pathLength++;
                temp = neighbor;
            }
            pathLength--;
        }

        // System.out.printf( "returning %d as the pathlength\n", pathLength);
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
        doDFS(v1, temp);
        if (myPathFound) {
            thepath.add(temp);
            Vertex<T> neighbor;
            while ((neighbor = myVisited.get(temp)) != null) {
                if (!neighbor.getLabel().equals("")) {
                    thepath.add(neighbor);
                    // System.out.printf("Adding %s to the path\n", neighbor );
                }
                temp = neighbor;
            }
        }
        java.util.Collections.reverse(thepath);
        return thepath;
    }

    private void doDFS(final Vertex<T> theOrigin, final Vertex<T> theTarget) {
        myPathFound = false;
        myVisited = new HashMap<Vertex<T>, Vertex<T>>();
        myVisited.put(theOrigin, new Vertex(""));
        dfs(theOrigin, theTarget);
    }

    private void dfs(final Vertex<T> theOrigin, final Vertex<T> theTarget) {
        if (theOrigin == theTarget) {
            myPathFound = true;
            return;
        }
        final Stack<Vertex<T>> neighborsToVisit = new Stack<Vertex<T>>();
        final List<Vertex<T>> neighbors
            = (ArrayList<Vertex<T>>) myGraph.getNeighbors(theOrigin);
        // push 'em in reverse order to "leftmost" neighbor is on top
        for (int i = neighbors.size() - 1; i >= 0; i--) {
            neighborsToVisit.push(neighbors.get(i));
        }

        while (!neighborsToVisit.empty()) {
            final Vertex<T> v = neighborsToVisit.pop();
            if (myVisited.get(v) == null) {
                myVisited.put(v, theOrigin);
                dfs(v, theTarget);
            }
        }
    }
}
