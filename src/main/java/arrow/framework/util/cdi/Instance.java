/*
 * package: arrow.framework.util.cdi file: Instance.java time: Jun 27, 2014
 * @author michael
 */
package arrow.framework.util.cdi;

import org.apache.deltaspike.core.api.provider.BeanProvider;


/**
 * The Class Instance.
 */
public class Instance {

    /**
     * Gets the instance of class.
     *
     * @param <T> the generic type
     * @param type the type
     * @return the t
     */
    public static <T> T get(final Class<T> type) {
        return BeanProvider.getContextualReference(type);
    }
}
