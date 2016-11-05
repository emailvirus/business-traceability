package arrow.framework.faces.exception;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.inject.Inject;

import arrow.framework.logging.BaseLogger;

/**
 * The Class BaseExceptionHandler.
 */
public class BaseExceptionHandler extends ExceptionHandlerWrapper {

    /** The log. */
    @Inject
    private BaseLogger log;


    /** The wrapped. */
    private final javax.faces.context.ExceptionHandler wrapped;

    /**
     * Instantiates a new base exception handler.
     *
     * @param wrapped the wrapped
     */
    public BaseExceptionHandler(final javax.faces.context.ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.ExceptionHandlerWrapper#getWrapped()
     */
    @Override
    public javax.faces.context.ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.ExceptionHandlerWrapper#handle()
     */
    @Override
    public void handle() throws FacesException {
        for (final ExceptionQueuedEvent e : this.getHandledExceptionQueuedEvents()) {
            this.log.warn("Handled Exception: " + e.getContext().getException().getMessage());
        }


        boolean hasUnhandledException = false;
        for (final ExceptionQueuedEvent e : this.getUnhandledExceptionQueuedEvents()) {
            final Throwable exception = e.getContext().getException();
            final Throwable rootCause = this.getRootCause(exception);

            if (rootCause != null) {
                this.log.debug("Root cause is", rootCause);
            }
            else {
                this.log.debug("Exception is", rootCause);
            }
            hasUnhandledException = true;
        }

        if (hasUnhandledException) {
            this.getWrapped().handle();
        }
    }
}
