/*
 * TCSS 342
 * 
 * EmptyQueueException
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

/**
 * Thrown when there is an attempt to access the front of an empty queue.
 * 
 * @author Simon Gray
 * @author Alan Fowler
 * @version 1.1
 */
public class EmptyQueueException extends RuntimeException {

    /** A version ID for Serialization. */
    private static final long serialVersionUID = 4462115152899433351L;

    /**
     * Initializes the exception.
     */
    public EmptyQueueException() {
        super();
    }

    /**
     * Initializes the exception.
     * 
     * @param theErrorMsg the custom error message
     */
    public EmptyQueueException(final String theErrorMsg) {
        super(" " + theErrorMsg);
    }
}
