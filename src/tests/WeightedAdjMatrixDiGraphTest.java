
package tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import structures.BFSSearcher;
import structures.DFSSearcher;
import structures.Vertex;
import structures.WeightedAdjMatrixDiGraph;

public class WeightedAdjMatrixDiGraphTest extends TestCase {
    
    private WeightedAdjMatrixDiGraph<String> myGraph = null;

    private Vertex<String> v1, v2, v3, v4, v5, v6;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myGraph = new WeightedAdjMatrixDiGraph<String>();
        v1 = new Vertex("v1");
        myGraph.addVertex(v1);
        v2 = new Vertex("v2");
        myGraph.addVertex(v2);
        v3 = new Vertex("v3");
        myGraph.addVertex(v3);
    }

    @Override
    protected void tearDown() throws Exception {
        myGraph = null;
        super.tearDown();
    }

    public void testConstructor() {
        final WeightedAdjMatrixDiGraph<String> g = new WeightedAdjMatrixDiGraph<String>();
        assertTrue(g.getNumberOfVertices() == 0);
        assertTrue(g.getNumberOfEdges() == 0);
    }

    public void testAddVertices() {
        assertEquals(3, myGraph.getNumberOfVertices());
    }

    public void testAddVertexWithEdges() {
        myGraph.addEdge(v1, v2); // v1 -> v2
        myGraph.addEdge(v3, v2); // v3 -> v2
        assertEquals(3, myGraph.getNumberOfVertices());
        assertEquals(2, myGraph.getNumberOfEdges());
    }

    public void testAddVertexWithEdgesWeights() {
        v4 = new Vertex("v4");
        myGraph.addVertex(v4);
        v5 = new Vertex("v5");
        myGraph.addVertex(v5);
        myGraph.addEdge(v5, 4.0, v2);
        v6 = new Vertex("v6");
        myGraph.addVertex(v6);
        myGraph.addEdge(v6, v3);
        assertEquals(6, myGraph.getNumberOfVertices());
        assertEquals(2, myGraph.getNumberOfEdges());
        assertEquals(4.0, myGraph.getEdgeWeight(v5, v2), .05);
        assertEquals(1.0, myGraph.getEdgeWeight(v6, v3), .05);
    }

    public void testSetWeightConstraint() {
        try {
            myGraph.addEdge(v1, -1, v2); // v1 -> v2
            fail("exception should have been thrown for illegal weight");
        } catch (final IllegalArgumentException e) {
            // do nothing - this is good news
        }
        try {
            myGraph.addEdge(v1, 1, v2); // v1 -> v2
            myGraph.setEdgeWeight(v1, -1, v2);
            fail("exception should have been thrown for illegal weight");
        } catch (final IllegalArgumentException e) {
            // do nothing - this is good news
        }
    }

    /**
     * Verify that we can get the neighbors of a vertex. That is, the adjacent
     * vertices.
     */
    public void testNeighbors() {
        myGraph.addEdge(v1, v2); // v1 -> v2
        myGraph.addEdge(v2, v1); // v2 -> v1
        myGraph.addEdge(v2, v3); // v2 -> v3
        assertTrue(myGraph.getNumberOfVertices() == 3);
        assertTrue(myGraph.getNumberOfEdges() == 3);
        List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph.getNeighbors(v1);
        assertTrue(neighbors.size() == 1);

        neighbors = (ArrayList<Vertex<String>>) myGraph.getNeighbors(v2);
        assertTrue(neighbors.size() == 2);
        assertTrue(neighbors.contains(v1));
        assertTrue(neighbors.contains(v3));
    }

    /**
     * Verify that removing a nonexistent edge does not detach any connected
     * vertices.
     */
    public void testRemoveEdgeNotExist() {

        myGraph.addEdge(v1, v2); // v1 -> v2
        myGraph.addEdge(v2, v3); // v2 -> v3
        try {
            myGraph.removeEdge(v1, v3); // remove nonexistent edge
            fail("failed to detect deleted non-existent edge");
        } catch (final IllegalArgumentException e) {
        }

        assertTrue(myGraph.getNumberOfVertices() == 3);
        assertTrue(myGraph.getNumberOfEdges() == 2);
        // verify that v1 still has its neightbors
        List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph.getNeighbors(v1);
        assertTrue(neighbors.size() == 1);
        assertTrue(neighbors.contains(v2));

        neighbors = (ArrayList<Vertex<String>>) myGraph.getNeighbors(v2);
        assertTrue(neighbors.size() == 1);
        assertTrue(neighbors.contains(v3));
    }

    /**
     * Verify that removing an edge updates the associated vertices. Make sure
     * we can add the edge back.
     */
    public void testRemoveEdgeExists() {

        myGraph.addEdge(v1, v2); // v1 -> v2
        myGraph.addEdge(v1, v3); // v1 -> v3
        assertEquals(3, myGraph.getNumberOfVertices());
        assertEquals(2, myGraph.getNumberOfEdges());
        List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph.getNeighbors(v1);
        assertTrue(neighbors.size() == 2);
        assertTrue(neighbors.contains(v2));
        assertTrue(neighbors.contains(v3));

        // now remove an edge
        myGraph.removeEdge(v1, v3);
        assertEquals(1, myGraph.getNumberOfEdges());
        neighbors = (ArrayList<Vertex<String>>) myGraph.getNeighbors(v1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(v2));

        // now add the edge back
        myGraph.addEdge(v1, v3); // v1 -> v3
        neighbors = (ArrayList<Vertex<String>>) myGraph.getNeighbors(v1);
        assertTrue(neighbors.size() == 2);
        assertTrue(neighbors.contains(v2));
        assertTrue(neighbors.contains(v3));
        assertEquals(3, myGraph.getNumberOfVertices());
        assertEquals(2, myGraph.getNumberOfEdges());
    }

    /**
     * Verify that removing a vertex removes the associated edges. Make sure we
     * can add the vertex back.
     */
    public void testRemoveVertex() {
        myGraph.addEdge(v1, v2); // v1 -> v2
        myGraph.addEdge(v2, v3); // v2 -> v3

        // Now remove the vertex
        myGraph.removeVertex(v2);
        assertEquals(2, myGraph.getNumberOfVertices());
        assertEquals(0, myGraph.getNumberOfEdges());
        List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph.getNeighbors(v1);
        assertEquals(0, neighbors.size());

        // now add a vertex back
        myGraph.addVertex(v2);
        myGraph.addEdge(v1, v2);
        myGraph.addEdge(v2, v3);
        assertEquals(3, myGraph.getNumberOfVertices());
        assertEquals(2, myGraph.getNumberOfEdges());

        neighbors = (ArrayList<Vertex<String>>) myGraph.getNeighbors(v2);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(v3));

        neighbors = (ArrayList<Vertex<String>>) myGraph.getNeighbors(v1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(v2));
    }

    /**
     * Verify that DFSSearcher and BFSSearcer can find a path that exists in the
     * graph and does not find a path that does NOT exist.
     */

    public void testPath() {
        myGraph.addEdge(v1, v2); // v1 -> v2
        myGraph.addEdge(v2, v3); // v2 -> v3
        v4 = new Vertex<String>("v4");
        myGraph.addVertex(v4);
        myGraph.addEdge(v4, v2); // v3 -> v4
        v5 = new Vertex<String>("v5");
        myGraph.addVertex(v5);
        myGraph.addEdge(v3, v5); // v3 -> v5
        v2 = new Vertex<String>("Disconnected");
        myGraph.addVertex(v2);
        final DFSSearcher<String> dfs = new DFSSearcher<String>(myGraph);
        assertTrue(dfs.containsPath(v1, v3));
        assertEquals(2, dfs.getPathLength(v1, v3));
        assertEquals(0, dfs.getPathLength(v3, v2));
        assertFalse(dfs.containsPath(v3, v1));

        // Verify that BFSSearcher also works
        final BFSSearcher<String> bfs = new BFSSearcher<String>(myGraph);
        assertTrue(bfs.containsPath(v1, v3));
        assertEquals(2, bfs.getPathLength(v1, v3));
        assertEquals(0, bfs.getPathLength(v3, v2));
        assertFalse(bfs.containsPath(v3, v1));
    }

    /**
     * Verify that DFSSearcher gets the vertices on the path from the source to
     * the destination.
     */
    public void testGetPath() {
        myGraph.addEdge(v1, v2); // v1 -> v2
        myGraph.addEdge(v2, v3); // v2 -> v3
        v4 = new Vertex<String>("v4");
        myGraph.addVertex(v4);
        myGraph.addEdge(v4, v2); // v4 ->v2
        final Vertex<String> dest = new Vertex<String>("v5");
        myGraph.addVertex(dest);
        myGraph.addEdge(v3, dest); // v3 -> v5
        v2 = new Vertex<String>("G");
        myGraph.addVertex(v2);
        final DFSSearcher<String> dfs = new DFSSearcher<String>(myGraph);

        List<Vertex<String>> thepath = (ArrayList<Vertex<String>>) dfs.getPath(v1, dest);
        assertTrue(thepath.contains(v1));
        assertTrue(thepath.contains(v3));
        assertTrue(thepath.contains(dest));
        assertFalse(thepath.contains(v2));
        assertFalse(thepath.contains(v4));

        final BFSSearcher<String> bfs = new BFSSearcher<String>(myGraph);
        thepath = (ArrayList<Vertex<String>>) bfs.getPath(v1, dest);
        assertTrue(thepath.contains(v1));
        assertTrue(thepath.contains(v3));
        assertTrue(thepath.contains(dest));
        assertFalse(thepath.contains(v2));
        assertFalse(thepath.contains(v4));
    }
}
