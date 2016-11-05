package arrow.framework.faces.exception;


/**
 * A factory for creating BaseExceptionHandler objects.
 */
public class BaseExceptionHandlerFactory extends javax.faces.context.ExceptionHandlerFactory {

    /** The parent. */
    private final javax.faces.context.ExceptionHandlerFactory parent;

    /**
     * Instantiates a new base exception handler factory.
     *
     * @param parent the parent
     */
    public BaseExceptionHandlerFactory(final javax.faces.context.ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.ExceptionHandlerFactory#getExceptionHandler()
     */
    @Override
    public BaseExceptionHandler getExceptionHandler() {
        return new BaseExceptionHandler(this.parent.getExceptionHandler());
    }
}
