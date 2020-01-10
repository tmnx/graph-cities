/*
 * TCSS 342
 * 
 * FullQueueException
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

/**
 * Thrown when there is an attempt to add an element to the a full queue.
 * 
 * @author Simon Gray
 * @author Alan Fowler
 * @version 1.1
 */
public class FullQueueException extends RuntimeException {

    /** A version ID for Serialization. */
    private static final long serialVersionUID = 2907816576465171344L;

    /**
     * Initializes the exception.
     */
    public FullQueueException() {
        super();
    }

    /**
     * Initializes the exception.
     * 
     * @param theErrorMsg the custom error message
     */
    public FullQueueException(final String theErrorMsg) {
        super(" " + theErrorMsg);
    }
}
