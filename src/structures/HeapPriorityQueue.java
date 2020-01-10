/*
 * TCSS 342
 * 
 * HeapPriorityQueue
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

import java.util.Collection;
import java.util.Comparator;

/**
 * Provides "highest priority element in the queue is the first element out"
 * (HPIFO) functionality.
 * 
 * @author Alan Fowler
 * @version 1.1
 * 
 * @param <E> the data type of the elements stored in this priority queue
 */
public class HeapPriorityQueue<E> implements PriorityQueue<E> {

    /**
     * The backing store for this priority queue.
     */
    private final ArrayMinHeap<E> myData;

    /**
     * The Comparator used to order the elements in this priority queue.
     */
    private final Comparator<? super E> myComparator;

    /**
     * Constructs an empty priority queue. The ordering of elements in the
     * priority queue is defined by <code>the_comparator</code>.
     * 
     * @param theComparator the Comparator used to order the elements of this
     *            priority queue
     * @throws IllegalArgumentException if the_comparator is null.
     */
    public HeapPriorityQueue(final Comparator<? super E> theComparator)
        throws IllegalArgumentException {

        if (theComparator == null) {
            throw new IllegalArgumentException();
        }
        myComparator = theComparator;
        myData = new ArrayMinHeap<E>(myComparator);
    }

    /**
     * Constructs a priority queue with the elements from
     * <code>the_collection</code> using the ordering defined by
     * <code>the_comparator</code>.
     * 
     * @param theCollection the Collection used to populate this priority queue
     * @param theComparator the Comparator used to order this priority queue
     * @throws IllegalArgumentException if either argument is null.
     */
    public HeapPriorityQueue(final Collection<? extends E> theCollection,
                             final Comparator<? super E> theComparator)
        throws IllegalArgumentException {

        if ((theComparator == null) || (theCollection == null)) {
            throw new IllegalArgumentException();
        }
        myComparator = theComparator;
        myData = new ArrayMinHeap<E>(myComparator);
        for (final E nextElement : theCollection) {
            myData.add(nextElement);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        myData.clear();
    }

    /** {@inheritDoc} */
    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new EmptyPriorityQueueException();
        }
        return myData.top();
    }

    /** {@inheritDoc} */
    @Override
    public void enqueue(final E theElement) {
        myData.add(theElement);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return myData.isEmpty();
    }

    /** {@inheritDoc} */
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyPriorityQueueException();
        }
        return myData.peek();
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return myData.size();
    }

}
