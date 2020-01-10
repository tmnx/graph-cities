
package tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import structures.AdjMatrixGraph;
import structures.BFSSearcher;
import structures.Vertex;



public class GraphTest extends TestCase {
    
    private AdjMatrixGraph<String> myGraph = null;

    private AdjMatrixGraph<String> myGraph2 = null;

    private AdjMatrixGraph<String> myBFSGraph = null;

    private Vertex<String> v1;
    
    private Vertex<String> v2;
    
    private Vertex<String> v3;
    
    private Vertex<String> v4;
    
    private Vertex<String> v5;
    
    private Vertex<String> v6;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myGraph = new AdjMatrixGraph<String>();
        myGraph2 = new AdjMatrixGraph<String>();
        v1 = new Vertex("v1");
        myGraph2.addVertex(v1);
        v2 = new Vertex("v2");
        myGraph2.addVertex(v2);
        v3 = new Vertex("v3");
        myGraph2.addVertex(v3);

        myBFSGraph = new AdjMatrixGraph<String>();
        v1 = new Vertex<String>("v1");
        myBFSGraph.addVertex(v1);
        v2 = new Vertex<String>("v2");
        myBFSGraph.addVertex(v2);
        myBFSGraph.addEdge(v1, v2);
        v3 = new Vertex<String>("v3");
        myBFSGraph.addVertex(v3);
        myBFSGraph.addEdge(v1, v3);
        v4 = new Vertex<String>("v4");
        myBFSGraph.addVertex(v4);
        myBFSGraph.addEdge(v4, v2);
        v5 = new Vertex<String>("v5");
        myBFSGraph.addVertex(v5);
        myBFSGraph.addEdge(v5, v3);
        v6 = new Vertex<String>("v6");
        myBFSGraph.addVertex(v6);
        myBFSGraph.addEdge(v6, v4);
        myBFSGraph.addEdge(v6, v5);
    }

    @Override
    protected void tearDown() throws Exception {
        myGraph = null;
        myGraph2 = null;
        myBFSGraph = null;
        super.tearDown();
    }

    public void testConstructor() {
        assertEquals(0, myGraph.getNumberOfVertices());
        assertEquals(0, myGraph.getNumberOfEdges());
    }

    /**
     * Verify that we can add vertices to a graph and get the correct Vertex
     * count.
     */
    public void testAddVertices() {
        assertEquals(3, myGraph2.getNumberOfVertices());
    }

    /**
     * Verify that we can add vertices and to a graph and get the correct Vertex
     * and Edge count.
     */
    public void testAddVerticesWithEdges() {
        myGraph2.addEdge(v1, v2); // A <-> B
        myGraph2.addEdge(v3, v2); // C <-> B
        assertEquals(3, myGraph2.getNumberOfVertices());
        assertEquals(2, myGraph2.getNumberOfEdges());
    }

    /**
     * Verify that we can get the neighbors of a vertex. That is, the adjacent
     * vertices.
     */
    public void testNeighbors() {
        myGraph2.addEdge(v1, v2); // A <-> B
        // graph2.addEdge( v2, v1 ); // B <-> A DUPLICATE
        myGraph2.addEdge(v2, v3); // B <-> C
        assertEquals(3, myGraph2.getNumberOfVertices());
        assertEquals(2, myGraph2.getNumberOfEdges());
        List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v1);
        assertEquals(1, neighbors.size());

        neighbors = (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v2);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(v1));
        assertTrue(neighbors.contains(v3));
    }

    /**
     * Verify that removing a nonexistent edge does not detach any connected
     * vertices.
     */
    public void testRemoveEdgeNotExist() {
        myGraph2.addEdge(v1, v2); // A <-> B
        myGraph2.addEdge(v2, v3); // B <-> C

        try {
            myGraph2.removeEdge(v1, v3); // remove nonexistent edge
            fail("failed to detect deleted non-existent edge");
        } catch (final IllegalArgumentException e) {
        }

        assertEquals(3, myGraph2.getNumberOfVertices());
        assertEquals(2, myGraph2.getNumberOfEdges());
        // verify that v1 still has its neightbors
        List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(v2));

        neighbors = (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v2);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(v1));
        assertTrue(neighbors.contains(v3));
    }

    /**
     * Verify that removing an edge updates the associated vertices. Make sure
     * we can add the edge back.
     */
    public void testRemoveEdgeExists() {
        myGraph2.addEdge(v1, v2); // A <-> B
        myGraph2.addEdge(v1, v3); // A <-> C
        assertEquals(3, myGraph2.getNumberOfVertices());
        assertEquals(2, myGraph2.getNumberOfEdges());

        // now remove an edge
        myGraph2.removeEdge(v1, v3);
        assertEquals(1, myGraph2.getNumberOfEdges());
        List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(v2));

        // now add the edge back
        myGraph2.addEdge(v1, v3); // A <-> C
        neighbors = (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v1);
        assertTrue(neighbors.size() == 2);
        assertTrue(neighbors.contains(v2));
        assertTrue(neighbors.contains(v3));
        assertEquals(3, myGraph2.getNumberOfVertices());
        assertEquals(2, myGraph2.getNumberOfEdges());
    }

    // now include tests for exceptional conditions
    public void testAddNullVertex() {
        try {
            myGraph2.addEdge(v1, null);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }

        // now try it with null as the origin vertex
        try {
            myGraph.addEdge(null, v1);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }
    }

    public void testAddVertexNotInGraph() {
        v2 = new Vertex<String>("Poo");
        try {
            myGraph.addEdge(v1, v2);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }
        try {
            myGraph.addEdge(v2, v1);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }
    }

    /**
     * Verify that removing a vertex removes the associated edges. Make sure we
     * can add the vertex back.
     */
    public void testRemoveVertex1() {
        myGraph2.addEdge(v1, v2); // A <-> B
        myGraph2.addEdge(v2, v3); // B <-> C

        // Now remove the vertex
        myGraph2.removeVertex(v2);
        assertEquals(2, myGraph2.getNumberOfVertices());
        assertEquals(0, myGraph2.getNumberOfEdges());
        List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v1);
        assertEquals(0, neighbors.size());
        neighbors = (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v3);
        assertEquals(0, neighbors.size());

        // now add a vertex back
        myGraph2.addVertex(v2);
        myGraph2.addEdge(v1, v2);
        myGraph2.addEdge(v2, v3);
        assertEquals(3, myGraph2.getNumberOfVertices());
        assertEquals(2, myGraph2.getNumberOfEdges());

        neighbors = (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v2);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(v1));
        assertTrue(neighbors.contains(v3));

        neighbors = (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(v2));
    }

    /**
     * Verify that removing a vertex removes the associated edges. Make sure we
     * can add the vertex back.
     */
    public void testRemoveVertex2() {
        myGraph2.addEdge(v1, v2); // A <-> B
        myGraph2.addEdge(v2, v3); // B <-> C

        // Now remove the vertex
        myGraph2.removeVertex(v1);
        assertEquals(2, myGraph2.getNumberOfVertices());
        assertEquals(1, myGraph2.getNumberOfEdges());
        List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v2);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(v3));
        neighbors = (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v3);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(v2));
    }

    /**
     * Verify that removing a vertex removes the associated edges.
     */
    public void testRemoveVertex3() {
        myGraph2.addEdge(v1, v2); // v1 <-> v2
        myGraph2.addEdge(v3, v2); // v3 <-> v2
        myGraph2.addVertex(v4);
        myGraph2.addEdge(v4, v2); // v4 <-> v2

        // Now remove the vertex
        myGraph2.removeVertex(v2);
        assertEquals(3, myGraph2.getNumberOfVertices());
        assertEquals(0, myGraph2.getNumberOfEdges());
        List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v1);
        assertEquals(0, neighbors.size());
        neighbors = (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v3);
        assertEquals(0, neighbors.size());
        neighbors = (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v4);
        assertEquals(0, neighbors.size());

    }

    public void testDuplicateElement() {
        try {
            myGraph2.addVertex(new Vertex("v1"));
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }
    }

    /**
     * Adding duplicate edges should not change the edge count. Since this graph
     * is undirected, this test tries two forms of a duplicate edge (v1 to v2
     * and v2 to v1).
     */
    public void testDuplicateEdge() {
        myGraph2.addEdge(v1, v2);
        assertEquals(1, myGraph2.getNumberOfEdges());
        // add the duplicate edge
        try {
            myGraph2.addEdge(v2, v1);
            Assert.fail("duplicate edge exception not caught");
        } catch (final IllegalArgumentException e) {
        }
        assertEquals(1, myGraph2.getNumberOfEdges());
        try {
            myGraph2.addEdge(v1, v2);
            Assert.fail("duplicate edge exception not caught");
        } catch (final IllegalArgumentException e) {
        }
        assertEquals(1, myGraph2.getNumberOfEdges());
    }

    public void testNullAndMissingNeighbor() {
        v1 = null;
        // neighbor is null
        try {
            myGraph.getNeighbors(v1);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }

        // neighbor not in the graph
        try {
            myGraph.getNeighbors(new Vertex<String>("Poo"));
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }
    }

    public void testRemoveNullAndMissingVertex() {
        v1 = null;
        // neighbor is null
        try {
            myGraph.removeVertex(v1);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }

        // neighbor not in the graph
        try {
            myGraph.removeVertex(new Vertex<String>("Poo"));
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }
    }

    public void testRemoveNullAndMissingEdge() {
        // there are four combinations, at least
        v2 = new Vertex<String>("Poo");
        // null second argument - bad!
        try {
            myGraph.removeEdge(v1, null);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }

        // null first argument - bad!
        try {
            myGraph.removeEdge(null, v1);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }

        // v2 not in the graph - second argument
        try {
            myGraph.removeEdge(v1, v2);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }

        // v2 not in the graph - first argument
        try {
            myGraph.removeEdge(v2, v1);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }
    }

    public void testbfsSearch() {
        final BFSSearcher<String> bfsSearcher = new BFSSearcher<String>(myBFSGraph);
        final List<Vertex<String>> list = (ArrayList<Vertex<String>>) bfsSearcher.bfs(v1);
        assertEquals(6, list.size());
        assertTrue(list.get(0).equals(v1));
        assertTrue(list.get(1).equals(v2));
        assertTrue(list.get(2).equals(v3));
        assertTrue(list.get(3).equals(v4));
        assertTrue(list.get(4).equals(v5));
        assertTrue(list.get(5).equals(v6));
    }

    public void testbfsSearchContainsPathExists() {
        final BFSSearcher<String> bfsSearcher = new BFSSearcher<String>(myBFSGraph);
        assertEquals(true, bfsSearcher.containsPath(v1, v6));
    }

    public void testbfsSearchConatainsPathNotExists() {
        final Vertex<String> v7 = new Vertex<String>("v7");
        myBFSGraph.addVertex(v7);
        final BFSSearcher<String> bfsSearcher = new BFSSearcher<String>(myBFSGraph);
        assertEquals(false, bfsSearcher.containsPath(v1, v7));
    }

    public void testbfsSearchGetPathLengthExists() {
        final BFSSearcher<String> bfsSearcher = new BFSSearcher<String>(myBFSGraph);
        assertEquals(3, bfsSearcher.getPathLength(v1, v6));
        assertEquals(2, bfsSearcher.getPathLength(v1, v5));
        assertEquals(1, bfsSearcher.getPathLength(v1, v2));
    }

    public void testbfsSearchGetPathLengthNotExists() {
        final Vertex<String> v7 = new Vertex<String>("v7");
        myBFSGraph.addVertex(v7);
        final BFSSearcher<String> bfsSearcher = new BFSSearcher<String>(myBFSGraph);
        assertEquals(0, bfsSearcher.getPathLength(v1, v7));
    }

    public void testbfsSearchGetPathExists() {
        final BFSSearcher<String> bfsSearcher = new BFSSearcher<String>(myBFSGraph);
        final List<Vertex<String>> path =
                        (ArrayList<Vertex<String>>) bfsSearcher.getPath(v1, v6);
        assertEquals(4, path.size());
        assertTrue(path.contains(v1));
        assertTrue(path.contains(v2));
        assertTrue(path.contains(v4));
        assertTrue(path.contains(v6));
    }

}
