/*
 * TCSS 342
 * 
 * Vertex
 * 
 * Formatted code to meet course coding conventions.
 */

package structures;

/**
 * A vertex in a graph. Every vertex has an identifying, immutable label.
 * 
 * @author Simon Gray
 * @author Alan Fowler
 * @version 1.1
 * 
 * @param <E>
 */
public class Vertex<E> {
    
    private final E myLabel;

    /**
     * Create a new vertex storing <code>theLabel</code>.
     * 
     * @param theLabel E The label to store in this vertex.
     */
    public Vertex(final E theLabel) {
        myLabel = theLabel;
    }

    /**
     * Get the label stored by this vertex.
     * 
     * @return E The label stored in this vertex.
     */
    public E getLabel() {
        return this.myLabel;
    }

    /**
     * Determine if this Vertex is equal to <code>o</code>. Comparison is based
     * on the value stored in the vertex. Overridden method from
     * <code>Object</code>.
     * 
     * @param theObject The other vertex.
     * @return boolean <code>true</code> if this vertice's label is equal to the
     *         other's label.
     * @throws ClassCastException if <code>o</code> is not a <code>Vertex</code>
     */
    @Override
    public boolean equals(final Object theObject) {
        if (!(theObject instanceof Vertex)) {
            throw new ClassCastException();
        }
        return this.myLabel.equals(((Vertex<E>) theObject).getLabel());
    }

    /**
     * Return the hashcode for this vertex. Overridden method from
     * <code>Object</code>.
     * 
     * @return int The hashcode for this vertex.
     */
    @Override
    public int hashCode() {
        return this.myLabel.hashCode();
    }

    /**
     * Return the <code>String</code> representation version of this vertex.
     * Overridden method from <code>Object</code>.
     * 
     * @return String The <code>String</code> representation of this vertex.
     */
    @Override
    public String toString() {
        return this.myLabel.toString();
    }
}
