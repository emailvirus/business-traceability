package arrow.framework.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.ObjectUtils;

/**
 * The Class CollectionUtils.
 */
public class CollectionUtils {

    /**
     * Checks if is empty.
     *
     * @param collection the collection
     * @return true, if is empty
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return (collection == null) || (collection.size() == 0);
    }

    /**
     * Checks if is not empty.
     *
     * @param collection the collection
     * @return true, if is not empty
     */
    public static boolean isNotEmpty(final Collection<?> collection) {
        return !CollectionUtils.isEmpty(collection);
    }

    /**
     * Copy list.
     *
     * @param <T> the generic type
     * @param list the list
     * @return the list
     */
    public static <T> List<T> copy(final List<T> list) {
        final List<T> result = new ArrayList<T>();
        result.addAll(list);
        return result;
    }

    /**
     * Compare Collections: considered equal if they are of the same size, contain the same members in the same
     * iteration order.
     *
     * @param firstMap the first map
     * @param secondMap the second map
     * @return true, if successful
     */
    public static boolean areEquals(final Collection<?> firstMap, final Collection<?> secondMap) {
        if (firstMap == secondMap) {
            return true;
        }

        if ((firstMap == null) ^ (secondMap == null)) {
            return false;
        }

        if (firstMap.equals(secondMap)) {
            return true;
        }

        if (firstMap.size() != secondMap.size()) {
            return false;
        }

        final Iterator<?> ib = secondMap.iterator();
        for (final Iterator<?> ia = firstMap.iterator(); ia.hasNext();) {
            if (!ObjectUtils.equals(ia.next(), ib.next())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Compare Maps: considered equal if they are of the same size, and contain the same key-value mappings, regardless
     * of iteration order.
     *
     * @param firstMap the first map
     * @param secondMap the second map
     * @return true, if successful
     */
    public static boolean areEquals(final Map<?, ?> firstMap, final Map<?, ?> secondMap) {
        if (firstMap == secondMap) {
            return true;
        }

        if ((firstMap == null) ^ (secondMap == null)) {
            return false;
        }

        if (firstMap.equals(secondMap)) {
            return true;
        }

        if (firstMap.size() != secondMap.size()) {
            return false;
        }

        for (final Entry<?, ?> entry : firstMap.entrySet()) {
            if (!ObjectUtils.equals(entry.getValue(), secondMap.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Filter source list by filterPredicate.
     *
     * @param <T> the generic type
     * @param srcList the src list
     * @param filterPredicate the filter predicate
     * @return resultList (Copy item from source List)
     */
    public static <T> List<T> filter(final List<T> srcList, final Predicate filterPredicate) {
        final List<T> temp = CollectionUtils.copy(srcList);
        org.apache.commons.collections.CollectionUtils.filter(temp, filterPredicate);
        return temp;
    }
}
