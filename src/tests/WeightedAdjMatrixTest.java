
package tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import structures.BFSSearcher;
import structures.DFSSearcher;
import structures.Vertex;
import structures.WeightedAdjMatrixGraph;

public class WeightedAdjMatrixTest extends TestCase {
    
    private WeightedAdjMatrixGraph<String> myGraph1 = null;
    private WeightedAdjMatrixGraph<String> myGraph2 = null;

    private Vertex<String> v1, v2, v3, v4, v5, v6;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myGraph1 = new WeightedAdjMatrixGraph<String>();
        myGraph2 = new WeightedAdjMatrixGraph<String>();
        v1 = new Vertex("v1");
        myGraph2.addVertex(v1);
        v2 = new Vertex("v2");
        myGraph2.addVertex(v2);
        v3 = new Vertex("v3");
        myGraph2.addVertex(v3);
        v4 = new Vertex("v4");
    }

    @Override
    protected void tearDown() throws Exception {
        myGraph1 = null;
        super.tearDown();
    }

    public void testAddVertices() {
        assertEquals(3, myGraph2.getNumberOfVertices());
        assertEquals(0, myGraph1.getNumberOfEdges());
    }

    public void testSetWeightConstraint() {
        try {
            myGraph2.addEdge(v1, -1, v2); // v1 -> v2
            fail("exception should have been thrown for illegal weight");
        } catch (final IllegalArgumentException e) {
            // do nothing - this is good news
        }
        try {
            myGraph2.addEdge(v1, 1, v2); // v1 -> v2
            myGraph2.setEdgeWeight(v1, -1, v2);
            fail("exception should have been thrown for illegal weight");
        } catch (final IllegalArgumentException e) {
            // do nothing - this is good news
        }
    }

    public void testAddVertexWithEdges() {
        v5 = new Vertex<String>("v5");
        myGraph2.addVertex(v5);
        myGraph2.addEdge(v5, 4.0, v2);
        v6 = new Vertex<String>("v6");
        myGraph2.addVertex(v6);
        myGraph2.addEdge(v6, v3);
        assertEquals(5, myGraph2.getNumberOfVertices());
        assertEquals(2, myGraph2.getNumberOfEdges());
        assertEquals(4.0, myGraph2.getEdgeWeight(v5, v2), .05);
        assertEquals(1.0, myGraph2.getEdgeWeight(v6, v3), .05);
    }

    public void testAddEdges() {
        myGraph2.addEdge(v1, 5.0, v2);
        myGraph2.addVertex(v4);
        myGraph2.addEdge(v1, v4);
        assertEquals(4, myGraph2.getNumberOfVertices());
        assertEquals(2, myGraph2.getNumberOfEdges());
        assertEquals(5.0, myGraph2.getEdgeWeight(v1, v2), .05);
        assertEquals(1.0, myGraph2.getEdgeWeight(v1, v4), .05);
    }

    public void testNeighbors() {
        myGraph2.addEdge(v1, 5.0, v2);
        myGraph2.addVertex(v4);
        myGraph2.addEdge(v1, v4);
        myGraph2.addEdge(v2, v3);
        List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v1);
        assertTrue(neighbors.size() == 2);
        assertTrue(neighbors.contains(v2));
        assertTrue(neighbors.contains(v4));

        v5 = new Vertex<String>("v5");
        myGraph2.addVertex(v5);
        neighbors = (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v5);
        assertTrue(neighbors.size() == 0);
    }

    public void testAddEdgeDuplicate() {
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

    public void testRemoveEdgeExists() {
        myGraph2.addEdge(v1, 5.0, v2);
        myGraph2.addVertex(v4);
        myGraph2.addEdge(v1, v4);
        myGraph2.addEdge(v2, 10.0, v3);
        assertTrue(myGraph2.getNumberOfVertices() == 4);
        assertTrue(myGraph2.getNumberOfEdges() == 3);
        myGraph2.removeEdge(v1, v2);
        assertTrue(myGraph2.getNumberOfVertices() == 4);
        assertTrue(myGraph2.getNumberOfEdges() == 2);
        final List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v1);
        assertTrue(neighbors.size() == 1);
        assertTrue(neighbors.contains(v4));
    }

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

    public void testRemoveVertex() {
        myGraph2.addEdge(v1, 5.0, v2);
        myGraph2.addVertex(v4);
        myGraph2.addEdge(v1, v4);
        myGraph2.addEdge(v2, 10.0, v3);
        v5 = new Vertex<String>("v5");
        myGraph2.addVertex(v5);
        myGraph2.addEdge(v5, 4.0, v2);
        assertTrue(myGraph2.getNumberOfVertices() == 5);
        assertEquals(myGraph2.getNumberOfEdges(), 4);
        myGraph2.removeVertex(v2);
        assertEquals(4, myGraph2.getNumberOfVertices());
        assertEquals(1, myGraph2.getNumberOfEdges());
        List<Vertex<String>> neighbors =
                        (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(v4));
        neighbors = (ArrayList<Vertex<String>>) myGraph2.getNeighbors(v3);
        assertEquals(0, neighbors.size());
    }

    public void testPath() {
        myGraph2.addEdge(v1, 5.0, v2);
        myGraph2.addVertex(v4);
        myGraph2.addEdge(v1, v4);
        myGraph2.addEdge(v2, 10.0, v3);
        v5 = new Vertex("v5");
        myGraph2.addVertex(v5);
        myGraph2.addEdge(v5, 4.0, v2);
        v6 = new Vertex("v6");
        myGraph2.addVertex(v6);
        myGraph2.addEdge(v6, 3.0, v3);
        myGraph2.addEdge(v4, 2.0, v6);
        final DFSSearcher<String> dfs = new DFSSearcher<String>(myGraph2);
        assertTrue(dfs.containsPath(v1, v3));

        v2 = new Vertex("oops");
        myGraph2.addVertex(v2);
        assertFalse(dfs.containsPath(v1, v2));
    }

    public void testPathLength() {
        myGraph1.addVertex(v1);
        myGraph1.addVertex(v2);
        myGraph1.addVertex(v3);
        myGraph1.addEdge(v1, v2); // v1 <-> v2
        myGraph1.addEdge(v2, v3); // v2 <-> v3
        v4 = new Vertex<String>("v4");
        myGraph1.addVertex(v4);
        myGraph1.addEdge(v4, v2); // v3 -> v4
        v5 = new Vertex<String>("v5");
        myGraph1.addVertex(v5);
        myGraph1.addEdge(v3, v5); // v3 -> v5
        v2 = new Vertex<String>("Disconnected");
        myGraph1.addVertex(v2);
        final DFSSearcher<String> dfs = new DFSSearcher<String>(myGraph1);
        assertTrue(dfs.containsPath(v1, v3));
        assertEquals(2, dfs.getPathLength(v1, v3));
        assertEquals(0, dfs.getPathLength(v3, v2));
        assertFalse(dfs.containsPath(v3, v2));

        // Verify that BFSSearcher also works
        final BFSSearcher<String> bfs = new BFSSearcher<String>(myGraph1);
        assertTrue(bfs.containsPath(v1, v3));
        assertEquals(2, bfs.getPathLength(v1, v3));
        assertEquals(0, bfs.getPathLength(v3, v2));
        assertFalse(bfs.containsPath(v3, v2));
    }

    public void testGetPath() {
        myGraph1.addVertex(v1);
        myGraph1.addVertex(v2);
        myGraph1.addVertex(v3);
        myGraph1.addEdge(v1, v2); // v1 <-> v2
        myGraph1.addEdge(v2, v3); // v2 <-> v3
        v4 = new Vertex<String>("v4");
        myGraph1.addVertex(v4);
        myGraph1.addEdge(v4, v2); // v4 <-> v2
        v5 = new Vertex<String>("v5");
        myGraph1.addVertex(v5);
        myGraph1.addEdge(v3, v5); // v3 <-> v5
        final Vertex<String> disconnected = new Vertex<String>("Disconnected");
        myGraph1.addVertex(disconnected);
        final DFSSearcher<String> dfs = new DFSSearcher<String>(myGraph1);
        assertTrue(dfs.containsPath(v1, v3));
        assertEquals(2, dfs.getPathLength(v1, v3));
        assertEquals(0, dfs.getPathLength(v3, disconnected));
        assertFalse(dfs.containsPath(v3, disconnected));

        // Verify that BFSSearcher also works
        final BFSSearcher<String> bfs = new BFSSearcher<String>(myGraph1);
        assertTrue(bfs.containsPath(v1, v3));
        assertEquals(2, bfs.getPathLength(v1, v3));
        assertEquals(0, bfs.getPathLength(v3, disconnected));
        assertFalse(bfs.containsPath(v3, disconnected));

        List<Vertex<String>> thepath = (ArrayList<Vertex<String>>) dfs.getPath(v1, v5);
        assertEquals(4, thepath.size());
        assertTrue(thepath.contains(v1));
        assertTrue(thepath.contains(v2));
        assertTrue(thepath.contains(v3));
        assertTrue(thepath.contains(v5));

        // BFSSearcher<String> bfs = new BFSSearcher<String>( graph1 );
        thepath = (ArrayList<Vertex<String>>) bfs.getPath(v1, v5);
        assertEquals(4, thepath.size());
        assertTrue(thepath.contains(v1));
        assertTrue(thepath.contains(v2));
        assertTrue(thepath.contains(v3));
        assertTrue(thepath.contains(v5));
    }

    // now include tests for exceptional conditions
    // now include tests for exceptional conditions
    public void testAddNullVertex() {
        try {
            myGraph2.addEdge(v1, null);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }

        // now try it with null as the origin vertex
        try {
            myGraph2.addEdge(null, v1);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }
    }

    public void testAddVertexNotInGraph() {
        v4 = new Vertex("A");

        try {
            myGraph2.addEdge(v1, v4);
            Assert.fail("Not good - found a non-existent edge");
        } catch (final IllegalArgumentException e) {
        }
        try {
            myGraph2.addEdge(v4, v1);
            Assert.fail("ot good - found a non-existent edge");
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
        // neigjbbor is null
        try {
            myGraph1.getNeighbors(v1);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }

        // neighbor not in the graph
        try {
            myGraph1.getNeighbors(new Vertex<String>("Poo"));
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }
    }

    public void testRemoveNullAndMissingVertex() {
        v1 = null;
        // neighbor is null
        try {
            myGraph1.removeVertex(v1);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }

        // neighbor not in the graph
        try {
            myGraph1.removeVertex(new Vertex<String>("Poo"));
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }
    }

    public void testRemoveNullAndMissingEdge() {
        // there are four combinations, at least
        v1 = new Vertex<String>("A");
        myGraph1.addVertex(v1);
        v2 = new Vertex<String>("Poo");
        // null argument - bad!
        try {
            myGraph1.removeEdge(v1, null);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }

        // null argument - bad!
        try {
            myGraph1.removeEdge(null, v1);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }

        // v2 not in the graph
        try {
            myGraph1.removeEdge(v1, v2);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }

        try {
            myGraph1.removeEdge(v2, v1);
            Assert.fail("exception should have been thrown");
        } catch (final IllegalArgumentException e) {
        }
    }

    public void testMinimalPathAlgorithm() {
        Vertex<String> a, b, c, d, e, f, g, h;
        a = new Vertex<String>("A");
        b = new Vertex<String>("B");
        myGraph1.addVertex(a);
        myGraph1.addVertex(b);
        myGraph1.addEdge(a, 3, b);
        f = new Vertex<String>("F");
        myGraph1.addVertex(f);
        myGraph1.addEdge(a, 2, f);
        g = new Vertex<String>("G");
        myGraph1.addVertex(g);
        myGraph1.addEdge(f, 4, g);
        d = new Vertex<String>("D");
        myGraph1.addVertex(d);
        myGraph1.addEdge(b, 2, d);
        c = new Vertex<String>("C");
        myGraph1.addVertex(c);
        myGraph1.addEdge(b, 1, c);
        myGraph1.addEdge(d, 1, c);
        myGraph1.addEdge(g, 2, d);
        e = new Vertex<String>("E");
        myGraph1.addVertex(e);
        h = new Vertex<String>("H");
        myGraph1.addVertex(h);
        myGraph1.addEdge(g, 2, h);
        myGraph1.addEdge(h, 1, e);
        myGraph1.addEdge(c, 8, e);
        final double path = myGraph1.minimalPath(a, e);
        assertEquals(9.0, path, .05);

        // graph1.setEdgeWeight(c, 2, e);
        // path = graph1.minimalPath( a, e );
        // assertEquals( 6.0, path, .05 );
    }

}
