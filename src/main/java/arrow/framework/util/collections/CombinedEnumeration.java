package arrow.framework.util.collections;


import java.util.Enumeration;
import java.util.NoSuchElementException;


/**
 * The Class CombinedEnumeration.
 *
 * @param <T> the generic type
 */
public class CombinedEnumeration<T> implements Enumeration<T> {

    /** The enumerations. */
    private final Enumeration<T>[] enumerations;

    /** The loc. */
    private int loc = 0;

    /**
     * Instantiates a new combined enumeration.
     *
     * @param enumerations the enumerations
     */
    public CombinedEnumeration(final Enumeration<T>[] enumerations) {
        this.enumerations = enumerations;
    }

    /* (non-Javadoc)
     * @see java.util.Enumeration#hasMoreElements()
     */
    @Override
    public boolean hasMoreElements() {
        for (int i = this.loc; i < this.enumerations.length; i++) {
            if (this.enumerations[i].hasMoreElements()) {
                return true;
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.util.Enumeration#nextElement()
     */
    @Override
    public T nextElement() {
        while (this.isCurrentEnumerationAvailable()) {
            if (this.currentHasMoreElements()) {
                return this.currentNextElement();
            }
            else {
                this.nextEnumeration();
            }
        }
        throw new NoSuchElementException();
    }


    /**
     * Next enumeration.
     */
    // private helpers
    private void nextEnumeration() {
        this.loc++;
    }

    /**
     * Checks if is current enumeration available.
     *
     * @return true, if is current enumeration available
     */
    private boolean isCurrentEnumerationAvailable() {
        return this.loc < this.enumerations.length;
    }

    /**
     * Current next element.
     *
     * @return the t
     */
    private T currentNextElement() {
        return this.enumerations[this.loc].nextElement();
    }

    /**
     * Current has more elements.
     *
     * @return true, if successful
     */
    private boolean currentHasMoreElements() {
        return this.enumerations[this.loc].hasMoreElements();
    }
}
