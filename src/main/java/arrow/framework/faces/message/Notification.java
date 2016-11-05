package arrow.framework.faces.message;


/**
 * The Class Notification.
 */
public class Notification {
    
    /**
     * Creates the error message.
     *
     * @param messageCode the message code
     * @param summaryKey the summary key
     * @param params the params
     * @return the message
     */
    public static Message createErrorMessage(final String messageCode, final String summaryKey, final Object... params) {
        return new ErrorMessage(messageCode, summaryKey, params);
    }

    /**
     * Creates the warn message.
     *
     * @param messageCode the message code
     * @param summaryKey the summary key
     * @param params the params
     * @return the message
     */
    public static Message createWarnMessage(final String messageCode, final String summaryKey, final Object... params) {
        return new WarnMessage(messageCode, summaryKey, params);
    }

    /**
     * Creates the info message.
     *
     * @param messageCode the message code
     * @param summaryKey the summary key
     * @param params the params
     * @return the message
     */
    public static Message createInfoMessage(final String messageCode, final String summaryKey, final Object... params) {
        return new InfoMessage(messageCode, summaryKey, params);
    }

}
