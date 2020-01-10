/*
 * FileIO TCSS 342
 */

package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import structures.Vertex;
import structures.WeightedGraph;

/**
 * Provides a static method to populate a graph from a file.
 * 
 * The file format is assumed to be: Line 1: an integer n representing the
 * number of vertices The next n lines should contain the text labels for the n
 * vertices, one per line The next n lines represent the matrix of edge costs in
 * the graph An edge weight of -1 represents that there is no such edge in the
 * graph
 * 
 * @author Alan Fowler
 * @version 1.1
 */
public final class FileIO {

    /**
     * The name of the default input file.
     */
    private static final String DEFAULT_FILE_NAME = "distances.txt";

    /**
     * Private constructor to inhibit external instantiation.
     */
    private FileIO() {
        // do not instantiate objects of this class
    }

    /**
     * Populates a graph from a file.
     * 
     * @param theGraph the graph to populate
     */
    public static void createGraphFromFile(final WeightedGraph<String> theGraph) {

        Scanner fileInput = null;
        try {
            fileInput = new Scanner(new File(DEFAULT_FILE_NAME));
        } catch (final FileNotFoundException exception) {
            exception.printStackTrace();
            System.exit(1);
        }

        // read the size
        final int size = fileInput.nextInt();
        fileInput.nextLine(); // read the EOL character

        // read the vertex labels
        final Vertex<String>[] vertices = new Vertex[size];
        for (int index = 0; index < size; index++) {
            vertices[index] = new Vertex<String>(fileInput.nextLine());
            theGraph.addVertex(vertices[index]);
        }

        // read the edge weights
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) { // read ALL data
                final int weight = fileInput.nextInt();
                if (weight > 0 && col < row) { // add a new edge - do not
                                               // duplicate
                                               // edges
                    theGraph.addEdge(vertices[row], weight, vertices[col]);
                }
            }
        }

    }

}
