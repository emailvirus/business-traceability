/*
 * package: arrow.framework.util.collections file: ListUtils.java time: Jun 27, 2014
 * @author michael
 */
package arrow.framework.util.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

import arrow.framework.util.FilterUtils;


/**
 * The Class ListUtils.
 */
public class ListUtils {

    /**
     * Filter the list, keeping the sort order. the original list should be unchanged
     *
     * @param <T> the generic type
     * @param list the list
     * @param filters the filters
     * @return filtered list
     */
    public static <T> List<T> filter(final List<T> list, final Map<String, String> filters) {
        final ArrayList<T> retVal = new ArrayList<T>();
        for (final T item : list) {
            boolean failedConstraint = false;
            for (final Entry<String, String> filterItem : filters.entrySet()) {
                if (FilterUtils.itemFailedFilterConstraint(item, filterItem.getKey(), filterItem.getValue())) {
                    failedConstraint = true;
                    break;
                }
            }

            if (!failedConstraint) {
                retVal.add(item);
            }
        }


        return retVal;
    }


    /**
     * sort list in-place, with multiple sorters.
     *
     * @param <T> the generic type
     * @param list the list
     * @param multiSorts the multi sorts
     */
    public static <T> void sort(final List<T> list, final List<Pair<String, SortOrder>> multiSorts) {
        // TODO: sort list in-place, with multiple sorters
    }
}
