package arrow.businesstraceability.util;

import java.io.Serializable;



/**
 * The Class SimpleCache.
 *
 * @param <T> the generic type
 */
public abstract class SimpleCache<T> implements Serializable {

    /** The cached. */
    private transient T cached;

    /**
     * Gets the.
     *
     * @return the t
     */
    public T get() {
        if (this.cached == null) {
            this.cached = this.retrieve();
        }

        return this.cached;
    }


    /**
     * Refresh.
     */
    public void refresh() {
        this.cached = null;
    }


    /**
     * Retrieve.
     *
     * @return the t
     */
    protected abstract T retrieve();
}
