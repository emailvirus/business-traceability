package arrow.framework.util.collections;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ObjectUtils;


/**
 * The Class MapUtils.
 */
public class MapUtils {

    /**
     * Checks if is empty.
     *
     * @param map the map
     * @return true, if is empty
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return (map == null) || (map.size() == 0);
    }

    /**
     * Checks if is not empty.
     *
     * @param map the map
     * @return true, if is not empty
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !MapUtils.isEmpty(map);
    }


    /**
     * Immutable entry.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param key the key
     * @param value the value
     * @return the entry
     */
    public static <K, V> Entry<K, V> immutableEntry(final K key, final V value) {
        return new ImmutableEntry<K, V>(key, value);
    }


    /**
     * The Class ImmutableEntry.
     *
     * @param <K> the key type
     * @param <V> the value type
     */
    private static class ImmutableEntry<K, V> implements Entry<K, V>, Serializable {

        /** The key. */
        private final K key;

        /** The value. */
        private final V value;

        /**
         * Instantiates a new immutable entry.
         *
         * @param key the key
         * @param value the value
         */
        private ImmutableEntry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        /* (non-Javadoc)
         * @see java.util.Map.Entry#getKey()
         */
        @Override
        public K getKey() {
            return this.key;
        }

        /* (non-Javadoc)
         * @see java.util.Map.Entry#getValue()
         */
        @Override
        public V getValue() {
            return this.value;
        }

        /* (non-Javadoc)
         * @see java.util.Map.Entry#setValue(java.lang.Object)
         */
        @Override
        public V setValue(final V value) {
            throw new UnsupportedOperationException();
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(final Object object) {
            if (object instanceof Entry<?, ?>) {
                final Entry<?, ?> that = (Entry<?, ?>) object;
                return ObjectUtils.equals(this.getKey(), that.getKey())
                        && ObjectUtils.equals(this.getValue(), that.getValue());
            }
            return false;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final K k = this.getKey();
            final V v = this.getValue();
            return ((k == null) ? 0 : k.hashCode()) ^ ((v == null) ? 0 : v.hashCode());
        }


        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return this.getKey() + "=" + this.getValue();
        }
    }
}
