package arrow.framework.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * The Class SimpleMapCache.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public abstract class SimpleMapCache<K, V> implements Serializable {

    /** The cached. */
    private transient Map<K, V> cached;

    /**
     * Gets the.
     *
     * @param key the k
     * @return the v
     */
    public V get(final K key) {
        if (this.cached == null) {
            this.cached = new HashMap<K, V>();
        }

        V value = this.cached.get(key);

        if (value == null) {
            value = this.retrieve(key);
            this.cached.put(key, value);
        }

        return value;
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
     * @param key the k
     * @return the v
     */
    protected abstract V retrieve(K key);
}
