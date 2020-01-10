/*
 * TCSS 342
 * 
 * Tuple
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

/**
 * A tuple consists of two elements. The elements may be of different types. If
 * both elements are of the same data type then class Pair may be more
 * appropriate.
 * 
 * @author Alan Fowler
 * @version 1.1
 * 
 * @param <T> The data type of the first element
 * @param <U> The data type of the second element
 * @see Pair
 */
public class Tuple<T, U> {

    /**
     * The first element in this Tuple.
     */
    private T myFirstElement;

    /**
     * The second element in this Tuple.
     */
    private U mySecondElement;

    /**
     * Construct an instance of a <tt>Tuple</tt> initialized to the given
     * elements.
     * 
     * @param element1 the first element of this tuple
     * @param element2 the second element of this tuple
     * @throws IllegalArgumentException if either <tt>element_1</tt> or
     *             <tt>element_2</tt> is <tt>null</tt>.
     */
    public Tuple(final T element1, final U element2) throws IllegalArgumentException {
        if ((element1 == null) || (element2 == null)) {
            throw new IllegalArgumentException();
        }
        myFirstElement = element1;
        mySecondElement = element2;
    }

    /**
     * Return the value of the first element of this tuple.
     * 
     * @return the first element of this tuple
     */
    public T getFirstElement() {
        return myFirstElement;
    }

    /**
     * Return the value of the second element of this tuple.
     * 
     * @return the second element of this tuple
     */
    public U getSecondElement() {
        return mySecondElement;
    }

    /**
     * Set the first element of this tuple to the new value.
     * 
     * @param theElement the new value for the first element of this tuple
     * @throws IllegalArgumentException if <tt>newFirst</tt> is <tt>null</tt>.
     */
    public void setFirstElement(final T theElement) throws IllegalArgumentException {
        if (theElement == null) {
            throw new IllegalArgumentException();
        }
        myFirstElement = theElement;
    }

    /**
     * Set the second element of this tuple to the new value.
     * 
     * @param theElement the new value for the second element of this tuple
     * @throws IllegalArgumentException if <tt>newFirst</tt> is <tt>null</tt>.
     */
    public void setSecondElement(final U theElement) throws IllegalArgumentException {
        if (theElement == null) {
            throw new IllegalArgumentException();
        }
        mySecondElement = theElement;
    }

}
