package arrow.framework.util;

import java.util.HashSet;
import java.util.Set;


/**
 * The Class ExceptionUtils.
 */
public class ExceptionUtils {

    /**
     * Unwrap.
     *
     * @param ta the t
     * @param unwrapTypes the unwrap types
     * @return the throwable
     */
    @SafeVarargs
    public static Throwable unwrap(Throwable ta, final Class<? extends Throwable>... unwrapTypes) {
        final Set<Throwable> wrappers = new HashSet<Throwable>();

        while (ExceptionUtils.needUnwrap(ta, wrappers, unwrapTypes)) {
            wrappers.add(ta);
            ta = ta.getCause();
        }

        return ta;
    }

    /**
     * Cast to or wrap with runtime exception.
     *
     * @param ta the t
     * @return the runtime exception
     */
    public static RuntimeException castToOrWrapWithRuntimeException(final Throwable ta) {
        if (ta instanceof RuntimeException) {
            return (RuntimeException) ta;
        }
        else {
            return new RuntimeException(ta);
        }
    }


    /**
     * Need unwrap.
     *
     * @param ta the throwable
     * @param wrappers the wrappers
     * @param unwrapTypes the unwrap types
     * @return true, if successful
     */
    // helper for unwrap method
    @SafeVarargs
    private static boolean needUnwrap(final Throwable ta, final Set<Throwable> wrappers,
            final Class<? extends Throwable>... unwrapTypes) {
        final Throwable cause = ta.getCause();
        if (cause == null) {
            return false;
        }
        if (wrappers.contains(cause)) {
            return false;
        }

        for (final Class<? extends Throwable> type : unwrapTypes) {
            if (type.isInstance(ta)) {
                return true;
            }
        }

        return false;
    }
}
