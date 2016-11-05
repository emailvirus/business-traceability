package arrow.businesstraceability.control.exception;

import arrow.framework.exception.ArrException;
import arrow.framework.persistence.transaction.annotation.BaseApplicationException;

/**
 * The Class TransactionRollbackException.
 */
@BaseApplicationException(rollback = true)
public class TransactionRollbackException extends ArrException {

    /**
     * Instantiates a new transaction rollback exception.
     *
     * @param exception the exception
     */
    public TransactionRollbackException(final Exception exception) {
        super(exception);
    }

    /**
     * Instantiates a new transaction rollback exception.
     *
     * @param msg the msg
     */
    public TransactionRollbackException(final String msg) {
        super(msg);
    }
}
