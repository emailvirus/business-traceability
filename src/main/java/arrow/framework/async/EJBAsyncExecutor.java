package arrow.framework.async;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import arrow.framework.util.ExceptionUtils;
import arrow.framework.util.cdi.Instance;



/**
 * The Class EJBAsyncExecutor.
 */
@Stateless
public class EJBAsyncExecutor implements Serializable {

    /**
     * Execute asynchronously without transaction.
     *
     * @param <T> the generic type
     * @param action the action
     * @return the future
     */
    public static <T> Future<T> executeAsynchronouslyWithoutTransaction(final Callable<T> action) {
        return Instance.get(EJBAsyncExecutor.class).executeWithoutTransaction(action);
    }

    /**
     * Execute asynchronously with transaction.
     *
     * @param <T> the generic type
     * @param action the action
     * @return the future
     */
    public static <T> Future<T> executeAsynchronouslyWithTransaction(final Callable<T> action) {
        return Instance.get(EJBAsyncExecutor.class).executeWithTransaction(action);
    }


    /**
     * Execute without transaction.
     *
     * @param <T> the generic type
     * @param action the action
     * @return the future
     */
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public <T> Future<T> executeWithoutTransaction(final Callable<T> action) {
        return EJBAsyncExecutor.execute(action);
    }

    /**
     * Execute with transaction.
     *
     * @param <T> the generic type
     * @param action the action
     * @return the future
     */
    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public <T> Future<T> executeWithTransaction(final Callable<T> action) {
        return EJBAsyncExecutor.execute(action);
    }


    /**
     * Execute.
     *
     * @param <T> the generic type
     * @param action the action
     * @return the future
     */
    // helper
    private static <T> Future<T> execute(final Callable<T> action) {
        try {
            return new AsyncResult<T>(action.call());
        } catch (final Exception e) {
            throw ExceptionUtils.castToOrWrapWithRuntimeException(e);
        }
    }
}
