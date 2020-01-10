/*
 * DisplayCostsMain TCSS 342
 */

package application;

import java.util.Scanner;
import structures.Vertex;
import structures.WeightedAdjMatrixGraph;

/**
 * A program to display path costs in a weighted directed graph.
 * 
 * @author Alan Fowler
 * @author Minh Nguyen
 * @version 1.1
 */
public final class DisplayCostsMain {

    /**
     * A value the user can enter to quit the program.
     */
    private static final int QUIT_OPTION = 0;

    /**
     * The graph used in this program.
     */
    private final WeightedAdjMatrixGraph<String> myGraph;

    /**
     * Private constructor to inhibit external instantiation.
     */
    private DisplayCostsMain() {
        myGraph = new WeightedAdjMatrixGraph<String>();
    }

    /**
     * The start point for the program.
     * 
     * @param theArgs command line arguments - ignored
     */
    public static void main(final String[] theArgs) {
        new DisplayCostsMain().start();
    }

    /**
     * Calls various methods to provide program functionality.
     */
    private void start() {
        FileIO.createGraphFromFile(myGraph);
        final Vertex<String>[] vertices = myGraph.getVertices();

        // report basic statistics correctly
        System.out.println("The number of vertices in the graph : " 
                            + myGraph.getNumberOfVertices());
        System.out.println("The number of edges in the graph : " 
                            + myGraph.getNumberOfEdges());

        // find diameter
        System.out.println("The diameter of this graph is : " + getDiameter());

        // creates a Scanner for keyboard input
        final Scanner console = new Scanner(System.in);
        boolean runAgain = true;

        // displays an introduction
        displayIntro();

        while (runAgain) { // loops until the user chooses to quit

            int from = 0;
            int to = 0;

            // get the user's choice for a start city
            from =
                            promptForChoice(console, "\nChoose a city to start at " + "(1 - "
                                                     + myGraph.getNumberOfVertices()
                                                     + ") or enter " + QUIT_OPTION
                                                     + " to quit the program : ");

            // perform some processing based on the menu choice
            if (from == QUIT_OPTION) {
                runAgain = false;
            } else {
                
                System.out.println(vertices[from - 1] + " has degree " 
                                   + myGraph.getNeighbors(vertices[from - 1]).size() + ".");

                // get the user's choice for an end city
                to =
                                promptForChoice(console, "\nChoose a city to end at (1 - "
                                                         + myGraph.getNumberOfVertices()
                                                         + ") or " + QUIT_OPTION
                                                         + " to quit : ");
            }
            if (to == QUIT_OPTION) {
                runAgain = false;
            } else {
                displayPathLength(vertices[from - 1], vertices[to - 1]);
            }
        }
        System.out.println("\nThanks for trying this program. Have a nice day.");
    }

    /**
     * Displays an introduction to the program.
     */
    public void displayIntro() {
        System.out.println("\nThis program reports the length of "
                           + "the shortest path between two cities.");
        System.out.println("The program will repeat until the user chooses to quit.");
        System.out.println("\nThe cities are:");
        int city = 1;
        for (final Vertex<String> name : myGraph.getVertices()) {
            System.out.printf("%-3d%s\n", city++, name);
        }
    }
    
    /**
     * Calculate the diameter of the graph.
     * The diameter of the graph is the longest simple path
     * distance between any pairs of vertices in the graph.
     * 
     * @return the diameter of the graph.
     */
    private double getDiameter() {
        
        // Get all the shortest paths' weights in the graph
        final double[][] shortestPaths = 
                        WeightedAdjMatrixGraph.floydShortestPaths(myGraph);
        double diameter = 0.0;
        
        // Look at all shortest paths
        // to find the greatest length of any of these shortest paths
        for (int row = 0; row < myGraph.getNumberOfVertices(); row++) {
            for (int col = 0; col < myGraph.getNumberOfVertices(); col++) {
                
                final double currentPath = shortestPaths[row][col];
                // If the current path is greater than current "diameter", update diameter
                if (currentPath > diameter) {
                    diameter = currentPath;
                }
            }
        }
        
        return diameter;
    }

    /**
     * Prompts for a menu choice in the range 1 to QUIT_OPTION.
     * 
     * @param theConsole a Scanner used to capture user input
     * @param thePrompt a prompt to the user
     * @return the number entered by the user
     */
    private int promptForChoice(final Scanner theConsole, final String thePrompt) {
        int choice = getInt(theConsole, thePrompt);
        while (choice < 0 || choice > myGraph.getNumberOfVertices()) {
            System.out.println("Invalid selection. Please try again.");
            choice = getInt(theConsole, thePrompt);
        }
        return choice;
    }

    /**
     * Prompts for an integer until an integer is entered.
     * 
     * This method is adopted from getInt() on page 315 of
     * "Building Java Programs" by Reges and Stepp
     * 
     * @param theConsole a Scanner used to capture user input
     * @param thePrompt a prompt to the user
     * @return the integer entered by the user
     */
    private int getInt(final Scanner theConsole, final String thePrompt) {
        System.out.print(thePrompt);
        while (!theConsole.hasNextInt()) {
            theConsole.next();
            System.out.println("Enter an integer. Please try again.");
            System.out.print(thePrompt);
        }
        return theConsole.nextInt();
    }

    /**
     * Displays the length of the path from thePoint1 to thePoint2.
     * 
     * @param thePoint1 the start point for the path
     * @param thePoint2 the end point for the path
     */
    private void displayPathLength(final Vertex<String> thePoint1,
                                   final Vertex<String> thePoint2) {
        
        // Get the minimum cost path from point 1 to point 2
        final double pathLength = myGraph.minimalPath(thePoint1, thePoint2);
        
        System.out.printf("The distance from " + thePoint1 + " to " + thePoint2 + " is: %,.1f",
                          pathLength);
    }

}
