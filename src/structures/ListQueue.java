/*
 * TCSS 342
 * 
 * ListQueue
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

import java.util.LinkedList;
import java.util.List;

/**
 * Queue ADT implemented using the Adapter design pattern and
 * java.util.LinkedList as the storage data structure. This version of Queue
 * assumes an upper bound on the number of elements that can be stored in the
 * queue.
 * 
 * @author Simon Gray
 * @author Alan Fowler
 * @version 1.1
 * 
 * @param <E>
 */
public class ListQueue<E> implements Queue<E> {
    
    /**
     * The list backing store for this Queue.
     */
    private List<E> myQueue;

    /**
     * The capacity of this queue.
     */
    private int myCapacity;

    /**
     * Default constructor. Create an empty queue with the default capacity.
     */
    public ListQueue() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Create a queue with a capacity of <tt>maxElements</tt>.
     * 
     * @param theMaxElements int must be greater than 0.
     * @throws IllegalArgumentException if <tt>maxElement</tt> is less than or
     *             equal to 0.
     */
    public ListQueue(final int theMaxElements) {
        this.myQueue = new LinkedList<E>();
        if (theMaxElements <= 0) {
            throw new IllegalArgumentException();
        }
        this.myCapacity = theMaxElements;
    }

    /**
     * Return the upper bound on the number of elements this Queue can store.
     * 
     * @return the capacity of this queue.
     */
    @Override
    public int capacity() {
        return this.myCapacity;
    }

    /**
     * Empty the queue of all elements.
     */
    @Override
    public void clear() {
        this.myQueue.clear();
    }

    /**
     * Remove and return the element at the front of the queue.
     * 
     * @return this queue's front element
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public E dequeue() {
        if (this.myQueue.isEmpty()) {
            throw new EmptyQueueException("The queue is empty");
        }
        return this.myQueue.remove(0);
    }

    /**
     * Add <tt>element</tt> to the end of the queue.
     * 
     * 
     * @param theElement the element to enqueue
     * @throws FullQueueException if the queue is full
     */
    @Override
    public void enqueue(final E theElement) {
        if (this.isFull()) {
            throw new FullQueueException("The queue is full");
        }
        myQueue.add(myQueue.size(), theElement);
    }

    /**
     * Return the element at the front of this queue. This operation does not
     * change the state of this queue.
     * 
     * @return the element at the front of this queue
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public E peek() {
        if (myQueue.isEmpty()) {
            throw new EmptyQueueException("The queue is empty");
        }
        return myQueue.get(0);
    }

    /**
     * Determine if this queue has any elements.
     * 
     * @return <tt>true</tt> if this queue has no elements (<tt>size() == 0</tt>
     *         ); <tt>false</tt> otherwise.
     */
    @Override
    public boolean isEmpty() {
        return myQueue.isEmpty();
    }

    /**
     * Determine if this queue has room for more elements.
     * 
     * @return <tt>true</tt> if this queue has room for more elements (
     *         <tt>size() == capacity()</tt>); <tt>false</tt> otherwise.
     */
    @Override
    public boolean isFull() {
        return myQueue.size() == myCapacity;
    }

    /**
     * Determine the number of elements stored in this queue.
     * 
     * @return the number of elements in this queue
     */
    @Override
    public int size() {
        return this.myQueue.size();
    }
}
