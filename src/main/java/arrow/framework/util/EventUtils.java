package arrow.framework.util;

import java.util.concurrent.Callable;

import javax.enterprise.util.AnnotationLiteral;

import arrow.framework.async.AsyncExecutor;
import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.util.cdi.CDIUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class EventUtils.
 */
public final class EventUtils {

    /** The Constant log. */
    private static final BaseLogger LOG = BaseLoggerProducer.getLogger(EventUtils.class);

    /**
     * Fire async event.
     *
     * @param data the data
     * @param eventName the event name
     */
    public static void fireAsyncEvent(final Object data, final AnnotationLiteral<?> eventName) {
        EventUtils.LOG.debug("Before fire event async");
        AsyncExecutor.executeAsynchronously_WITHOUT_Transaction(new Callable<Boolean>() {

            /* (non-Javadoc)
             * @see java.util.concurrent.Callable#call()
             */
            @Override
            public Boolean call() throws Exception {
                EventUtils.LOG.debug("Fire event");
                CDIUtils.getBeanManager().fireEvent(data, eventName);
                EventUtils.LOG.debug("End of fire event");
                return true;
            }
        });

        EventUtils.LOG.debug("Out of fire event");
    }
}
