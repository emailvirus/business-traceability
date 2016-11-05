package arrow.framework.util.collections;

import java.util.HashSet;
import java.util.Set;



/**
 * The Class ArrayUtils.
 */
public class ArrayUtils {

    /**
     * Checks if is empty.
     *
     * @param objects the objects
     * @return true, if is empty
     */
    public static boolean isEmpty(final Object[] objects) {
        return (objects == null) || (objects.length == 0);
    }

    /**
     * Checks if is not empty.
     *
     * @param objects the objects
     * @return true, if is not empty
     */
    public static boolean isNotEmpty(final Object[] objects) {
        return !ArrayUtils.isEmpty(objects);
    }


    /**
     * As set.
     *
     * @param <T> the generic type
     * @param array the array
     * @return the sets the
     */
    @SafeVarargs
    public static <T> Set<T> asSet(final T... array) {
        final Set<T> result = new HashSet<T>();
        for (final T a : array) {
            result.add(a);
        }

        return result;
    }
}
