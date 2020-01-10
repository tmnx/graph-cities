/*
 * TCSS 342
 * 
 * Pair
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

/**
 * A pair consists of two elements of the same type. This class illustrates the
 * definition of a generic type with type parameter <tt>T</tt>.
 * 
 * @author Simon Gray
 * @author Alan Fowler
 * @version 1.1
 * 
 * @param <T>
 */
public class Pair<T> {
    
    /**
     * The first element of this Pair.
     */
    private T myFirstElement;

    /**
     * The second element of this Pair.
     */
    private T mySecondElement;

    /**
     * Default constructor.
     */
    public Pair() {
        myFirstElement = null;
        mySecondElement = null;
    }

    /**
     * Construct an instance of a <tt>Pair</tt> initialized to the given
     * elements.
     * 
     * @param e1 the first element of this pair
     * @param e2 the second element of this pair
     * @throws NullPointerException if either <tt>e1</tt> or <tt>e2</tt> is
     *             <tt>null</tt>.
     */
    public Pair(final T e1, final T e2) {
        if ((e1 == null) || (e2 == null)) {
            throw new NullPointerException();
        }
        myFirstElement = e1;
        mySecondElement = e2;
    }

    /**
     * Return the value of the first element of this pair.
     * 
     * @return the first element of this pair
     */
    public T getFirstElement() {
        return this.myFirstElement;
    }

    /**
     * Return the value of the second element of this pair.
     * 
     * @return the second element of this pair
     */
    public T getSecondElement() {
        return this.mySecondElement;
    }

    /**
     * Set the first element of this pair to the new value.
     * 
     * @param theNewFirst the new value for the first element of this pair
     * @throws NullPointerException if <tt>newFirst</tt> is <tt>null</tt>.
     */
    public void setFirstElement(final T theNewFirst) {
        if (theNewFirst == null) {
            throw new NullPointerException();
        }

        myFirstElement = theNewFirst;
    }

    /**
     * Set the second element of this pair to the new value.
     * 
     * @param theNewSecond the new value for the second element of this pair
     * @throws NullPointerException if <tt>newSecond</tt> is <tt>null</tt>.
     */
    public void setSecondElement(final T theNewSecond) {
        if (theNewSecond == null) {
            throw new NullPointerException();
        }
        mySecondElement = theNewSecond;
    }

    /**
     * Swap the two elements.
     */
    public void swapElements() {
        final T temp = this.myFirstElement;
        myFirstElement = this.mySecondElement;
        mySecondElement = temp;
    }

    /**
     * Returns a string representation of the object: this is the string
     * representation of element 1 followed by the string representation of
     * element 2.
     * 
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "< " + this.myFirstElement + ", " + this.mySecondElement + " >";
    }
}
