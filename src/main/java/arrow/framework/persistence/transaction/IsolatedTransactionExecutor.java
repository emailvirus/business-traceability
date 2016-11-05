package arrow.framework.persistence.transaction;

import java.util.concurrent.Callable;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.omnifaces.cdi.Startup;

import arrow.framework.persistence.transaction.annotation.BaseApplicationException;


/**
 * The Class IsolatedTransactionExecutor.
 */
@ApplicationScoped
@Startup
public class IsolatedTransactionExecutor {

    /**
     * Execute with isolated transaction.
     *
     * @param <T> the generic type
     * @param action the action
     * @return the t
     * @throws Exception the exception
     */
    @Transactional(value = TxType.REQUIRES_NEW, rollbackOn = {BaseApplicationException.class})
    public <T> T executeWithIsolatedTransaction(final Callable<T> action) throws Exception {
        return action.call();
    }


    /**
     * Executing anything WITHOUT a transaction is potentially dangerous. Be very sure of what you are trying to do when
     * calling this method
     *
     * @param <T> the generic type
     * @param action the action
     * @return the t
     * @throws Exception the exception
     */
    @Transactional(TxType.NOT_SUPPORTED)
    public <T> T execute_WITHOUT_Transaction(final Callable<T> action) throws Exception {
        return action.call();
    }
}
