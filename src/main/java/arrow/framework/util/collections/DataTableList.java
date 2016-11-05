package arrow.framework.util.collections;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;


/**
 * List interface that support filtering, sorting, and two-way conversion from data to string key to facilitate client
 * side selection.
 *
 * @author HUGH
 * @param <T> the generic type
 */
public interface DataTableList<T> extends List<T> {

    /**
     * Apply filters.
     *
     * @param filters the filters
     */
    public void applyFilters(Map<String, String> filters);

    /**
     * Apply sorters.
     *
     * @param sorters the sorters
     */
    public void applySorters(final List<Pair<String, SortOrder>> sorters);

    /**
     * Gets the data for key.
     *
     * @param key the key
     * @return the data for key
     */
    public T getDataForKey(final String key);

    /**
     * Gets the key for data.
     *
     * @param object the object
     * @return the key for data
     */
    public Object getKeyForData(final T object);

}
