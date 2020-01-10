/*
 * TCSS 342
 * 
 * ArrayMinHeap
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

import java.util.Collection;
import java.util.Comparator;

/**
 * An array implementation of the <code>Heap</code> interface.
 * 
 * @author Simon Gray
 * @author Alan Fowler
 * @version 1.1
 * 
 * @param <E>
 */
public class ArrayMinHeap<E> implements Heap<E> {

    /**
     * The initial heap size.
     */
    private static final int DEFAULT_HEAP_SIZE = 100;

    /**
     * A flag to indicate no child node exists at a particular index.
     */
    private static final int NO_CHILD = -1;

    /**
     * The index of the top node in the heap.
     */
    private static final int TOP_OF_HEAP = 0;

    /**
     * The current size of the heap.
     */
    private int mySize;

    // left child is in position 2 * i + 1
    // right child is in position 2 * i + 2
    // parent is in position (i - 1) / 2
    /**
     * The backing store for this heap.
     */
    private E[] myHeap;

    /**
     * The Comparator used to order this Heap.
     */
    private final Comparator<? super E> myComparator;

    /**
     * Construct an empty minheap. The ordering of elements in the minheap is
     * defined by <code>theComparator</code>.
     * 
     * @param theComparator the Comparator used to order this Heap.
     * 
     * @throws NullPointerException if compare is null.
     */
    public ArrayMinHeap(final Comparator<? super E> theComparator) {
        if (theComparator == null) {
            throw new NullPointerException();
        }
        mySize = 0;
        myHeap = (E[]) new Object[DEFAULT_HEAP_SIZE];
        myComparator = theComparator;
    }

    /**
     * Construct a minheap with the elements from <code>theCollection</code> using
     * the ordering defined by <code>theComparator</code>.
     * 
     * @param theCollection the elements to place in this Heap
     * @param theComparator the Comparator used to order this Heap
     * 
     * @throws NullPointerException if either argument is null.
     */
    public ArrayMinHeap(final Collection<? extends E> theCollection,
                        final Comparator<? super E> theComparator) {
        if ((theComparator == null) || (theCollection == null)) {
            throw new NullPointerException();
        }

        myComparator = theComparator;
        buildHeap(theCollection);
    }

    /**
     * Insert the given element into the heap.
     * 
     * @param theElement The element to add to the heap.
     * @throws NullPointerException if element is null
     */
    @Override
    public void add(final E theElement) {
        if (theElement == null) {
            throw new NullPointerException();
        }

        // insert the new element as the new rightmost leaf
        if (mySize == myHeap.length) {
            resize();
        }
        myHeap[mySize] = theElement;
        mySize++;
        int child = mySize - 1;
        int parent = (child - 1) / 2;

        // while the new element has a parent and the new element
        // is less than its parent
        while ((child != TOP_OF_HEAP)
                        && (myComparator.compare(myHeap[child], myHeap[parent]) < 0)) {
            // swap the parent and child elements
            final E temp = myHeap[parent];
            myHeap[parent] = myHeap[child];
            myHeap[child] = temp;
            child = parent;
            parent = (child - 1) / 2;
        }
    }

    /**
     * Remove and return the top element of the heap.
     * 
     * @return The top element of the heap.
     * @throws EmptyHeapException if the heap is empty.
     */
    @Override
    public E top() {
        if (isEmpty()) {
            throw new EmptyHeapException();
        }

        final E oldTop = myHeap[TOP_OF_HEAP];
        myHeap[TOP_OF_HEAP] = myHeap[mySize - 1];
        myHeap[mySize - 1] = null;
        mySize--;
        fixHeap(TOP_OF_HEAP);
        return oldTop;
    }

    /**
     * Removes all of the elements from this heap.
     * 
     * @post size is 0
     */
    @Override
    public void clear() {
        for (int i = 0; i < mySize; i++) {
            myHeap[i] = null;
        }
        mySize = 0;
    }

    /**
     * Looks at the object at the top of this heap without removing it from the
     * heap.
     * 
     * @return The top element of the heap.
     * @throws EmptyHeapException if the heap is empty.
     */
    @Override
    public E peek() {
        if (this.isEmpty()) {
            throw new EmptyHeapException();
        }
        return myHeap[TOP_OF_HEAP];
    }

    /**
     * Fix the heap rooted at parent.
     * 
     * pre: parent is in the range 0 to size - 1 (NOT VALIDATED)
     * post: the heap rooted at parent is a valid minheap
     * 
     * @param theParent the root of the current subtree
     */
    private void fixHeap(final int theParent) {
        int current = theParent;
        int child = smallestChild(current);

        // while the parent has a child and the parent's element
        // is greater than the element in either child
        while ((child != NO_CHILD)
                        && (myComparator.compare(myHeap[child], myHeap[current]) < 0)) {
            // invariant: the parent's children are roots of
            // valid heaps
            // swap the parent's element with the element of the
            // smaller of its children
            final E temp = myHeap[current];
            myHeap[current] = myHeap[child];
            myHeap[child] = temp;
            // invariant: the parent and its children are a
            // now valid heap
            // move parent to the swapped child
            current = child;
            child = smallestChild(current);
        }
        // loop postcondition: the binary tree rooted at node is
        // a valid heap
    }

    /**
     * Return the number of elements stored in this heap.
     * 
     * @return The number of elements in this heap.
     */
    @Override
    public int size() {
        return this.mySize;
    }

    /**
     * Determine if this heap is empty.
     * 
     * @return true if this heap is empty (size() == 0), otherwise return false.
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Doubles the capacity of the backing store.
     */
    private void resize() {
        final E[] tempHeap = (E[]) new Object[myHeap.length * 2];
        System.arraycopy(myHeap, 0, tempHeap, 0, myHeap.length);
        myHeap = tempHeap;
    }

    /**
     * Return index of the smaller of parent's two children. If parent is a leaf
     * (and has no children), return the constant NO_CHILD.
     * 
     * @param theParent the node to check
     * @return the index of the smaller child of theParent
     */
    private int smallestChild(final int theParent) {

        // see if the parent is actually a leaf
        if (theParent > (mySize / 2) - 1) {
            return NO_CHILD;
        }

        // parent has at least one child
        final int leftChild = 2 * theParent + 1;
        final int rightChild = leftChild + 1;

        // if parent has no right child, return left child
        if (rightChild >= mySize) {
            return leftChild;
        }

        // parent has two children, return the smaller of them
        if (myComparator.compare(myHeap[leftChild], myHeap[rightChild]) < 0) {
            return leftChild;
        }
        return rightChild;
    }

    /**
     * Construct a valid minheap from a collection of elements.
     * 
     * @pre c is not null (NOT VALIDATED)
     * @post heap is a valid minheap
     * 
     * @param theCollection the collection from which to build this Heap
     */
    private void buildHeap(final Collection<? extends E> theCollection) {
        // move the elements of c into a complete binary tree
        // rooted at top
        myHeap = (E[]) theCollection.toArray();
        mySize = theCollection.size();
        // let cursor be the rightmost interior node
        int cursor = (mySize / 2) - 1;

        // invariant: the children of cursor are valid heaps
        // while there are heaps to fix
        while (cursor >= TOP_OF_HEAP) {
            fixHeap(cursor); // fix the heap rooted at cursor
            cursor--; // move cursor to the next
            // rightmost unvisited interior
            // node
        }
    }
}
