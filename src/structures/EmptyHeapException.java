/*
 * TCSS 342
 * 
 * EmptyHeapException
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

/**
 * Thrown when an empty heap has been accessed.
 * 
 * @author Simon Gray
 * @author Alan Fowler
 * @version 1.1
 */
public class EmptyHeapException extends RuntimeException {

    /** A version ID for Serialization. */
    private static final long serialVersionUID = 2730871658944628181L;

    /**
     * Initializes the exception.
     */
    public EmptyHeapException() {
        super();
    }

    /**
     * Initializes the exception.
     * 
     * @param theErrorMsg the custom error message
     */
    public EmptyHeapException(final String theErrorMsg) {
        super(" " + theErrorMsg);
    }
}
