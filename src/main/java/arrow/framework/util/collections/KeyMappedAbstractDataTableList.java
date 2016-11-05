package arrow.framework.util.collections;

import java.util.AbstractList;
import java.util.HashMap;
import java.util.Map;


/**
 * The Class KeyMappedAbstractDataTableList.
 *
 * @param <T> the generic type
 */
public abstract class KeyMappedAbstractDataTableList<T> extends AbstractList<T> implements DataTableList<T> {

    /** The key map. */
    private final Map<String, T> keyMap = new HashMap<String, T>();

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.DataTableList#getDataForKey(java.lang.String)
     */
    @Override
    public T getDataForKey(final String key) {
        return this.keyMap.get(key);
    }

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.DataTableList#getKeyForData(java.lang.Object)
     */
    @Override
    public Object getKeyForData(final T object) {
        final String key = Integer.toHexString(System.identityHashCode(object));
        this.keyMap.put(key, object);
        return key;
    }
}
