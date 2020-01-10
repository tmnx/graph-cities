/*
 * TCSS 342
 * 
 * EmptyPriorityQueueException
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

/**
 * Thrown when an empty priority queue has been accessed.
 * 
 * @author Alan Fowler
 * @version 1.1
 */
public class EmptyPriorityQueueException extends RuntimeException {

    /**
     * A generated serial version UID.
     */
    private static final long serialVersionUID = -5288309290889409167L;

    /**
     * Constructs a new EmptyPriorityQueueException.
     */
    public EmptyPriorityQueueException() {
        super();
    }

    /**
     * Constructs a new EmptyPriorityQueueException using the parameter as a
     * message.
     * 
     * @param theMessage the message to include with this Exception.
     */
    public EmptyPriorityQueueException(final String theMessage) {
        super(" " + theMessage);
    }

}
