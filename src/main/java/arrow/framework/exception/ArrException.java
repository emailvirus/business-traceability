package arrow.framework.exception;

/**
 * By Default this kind of exception will not rollback transaction.
 *
 * @author michael
 */
public class ArrException extends Exception {

    /** The original exception. */
    protected final Exception originalException;

    /**
     * Instantiates a new arr exception.
     *
     * @param exp the exp
     */
    public ArrException(final Exception exp) {
        super(exp);
        this.originalException = exp;
    }

    /**
     * Instantiates a new arr exception.
     *
     * @param msg the msg
     */
    public ArrException(final String msg) {
        super(msg);
        this.originalException = null;
    }

    public Exception getOriginalException() {
        return this.originalException;
    }

}
