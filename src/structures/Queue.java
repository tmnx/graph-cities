/*
 * TCSS 342
 * 
 * Queue
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

/**
 * Interface for the Queue ADT with a fixed upper bound on the number of
 * elements that can be stored in the queue.
 * 
 * @author Simon Gray
 * @author Alan Fowler
 * @version 1.1
 * 
 * @param <E>
 */
public interface Queue<E> extends java.io.Serializable {

    /**
     * The default number of entries in a Queue.
     */
    int DEFAULT_CAPACITY = 100;

    /**
     * Return the upper bound on the number of elements this Queue can store.
     * 
     * @return the capacity of this queue.
     */
    int capacity();

    /**
     * Empty the queue of all elements.
     */
    void clear();

    /**
     * Add <tt>element</tt> to the end of the queue.
     * 
     * @param theElement The element to add to the rear of the queue
     * @throws FullQueueException if the queue is full
     */
    void enqueue(E theElement);

    /**
     * Remove and return the element at the front of the queue.
     * 
     * @return this queue's front element
     * @throws EmptyQueueException if the queue is empty
     */
    E dequeue();

    /**
     * Determine if this queue has any elements.
     * 
     * @return <tt>true</tt> if this queue has no elements (<tt>size() == 0</tt>
     *         ); <tt>false</tt> otherwise.
     */
    boolean isEmpty();

    /**
     * Determine if this queue has room for more elements.
     * 
     * @return <tt>true</tt> if this queue has room for more elements (
     *         <tt>size() == capacity()</tt>); <tt>false</tt> otherwise.
     */
    boolean isFull();

    /**
     * Return the element at the front of this queue. This operation does not
     * change the state of this queue.
     * 
     * @return the element at the front of this queue
     * @throws EmptyQueueException if the queue is empty
     */
    E peek();

    /**
     * Determine the number of elements stored in this queue.
     * 
     * @return the number of elements in this queue
     */
    int size();
}
