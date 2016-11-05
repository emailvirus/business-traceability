package arrow.framework.async;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.persistence.transaction.IsolatedTransactionExecutor;
import arrow.framework.util.cdi.Instance;


/**
 * The Class AsyncExecutor.
 */
@ApplicationScoped
public class AsyncExecutor {

    /** The Constant EXECUTOR_SERVICE. */
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3);

    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger(AsyncExecutor.class);

    /**
     * Execute asynchronously with isolated transaction.
     *
     * @param <T> the generic type
     * @param action the action
     * @return the future
     */
    public static <T> Future<T> executeAsynchronouslyWithIsolatedTransaction(final Callable<T> action) {
        return AsyncExecutor.EXECUTOR_SERVICE.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    return Instance.get(IsolatedTransactionExecutor.class).executeWithIsolatedTransaction(action);
                } catch (final Exception e) {
                    // The exception wouldn't be seen until the caller call Future.get(),
                    // so we print it here and rethrow, in case the caller might miss it.
                    AsyncExecutor.LOGGER.debug("Fail when calling Future.get", e);
                    throw e;
                }
            }
        });
    }


    /**
     * Executing anything WITHOUT a transaction is potentially dangerous. Be very sure of what you are trying to do when
     * calling this method
     *
     * @param <T> the generic type
     * @param action the action
     * @return the future
     */
    public static <T> Future<T> executeAsynchronously_WITHOUT_Transaction(final Callable<T> action) {
        return AsyncExecutor.EXECUTOR_SERVICE.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    return Instance.get(IsolatedTransactionExecutor.class).execute_WITHOUT_Transaction(action);
                } catch (final Exception e) {
                    // The exception wouldn't be seen until the caller call Future.get(),
                    // so we print it here and rethrow, in case the caller might miss it.
                    AsyncExecutor.LOGGER.debug("Fail when calling Future.get", e);
                    throw e;
                }
            }
        });
    }
}
