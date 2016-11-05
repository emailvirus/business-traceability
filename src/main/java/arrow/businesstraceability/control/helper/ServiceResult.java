package arrow.businesstraceability.control.helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.faces.context.FacesContext;

import arrow.framework.faces.message.Message;


/**
 * The Class ServiceResult.
 *
 * @param <T> the generic type
 */
public class ServiceResult<T> {

    /** The successful. */
    private boolean successful;

    /** The data. */
    private T data;

    /** The messages. */
    private List<Message> messages;

    /** The error code. */
    private ServiceErrorCode errorCode;

    /**
     * Instantiates a new service result.
     *
     * @param successful the successful
     * @param data the data
     * @param errorCode the error code
     */
    public ServiceResult(final boolean successful, final T data, final ServiceErrorCode errorCode) {
        this.successful = successful;
        this.data = data;
        this.errorCode = errorCode;
    }

    /**
     * Instantiates a new service result.
     *
     * @param successful the successful
     * @param data the data
     * @param messages the messages
     */
    public ServiceResult(final boolean successful, final T data, final List<Message> messages) {
        this.successful = successful;
        this.data = data;
        if (messages != null) {
            this.messages = messages;
        } else {
            this.messages = Collections.emptyList();
        }
    }

    /**
     * Instantiates a new service result.
     *
     * @param successful the successful
     * @param data the data
     * @param messages the messages
     */
    public ServiceResult(final boolean successful, final T data, final Message... messages) {
        this(successful, data, Arrays.asList(messages));
    }

    /**
     * Instantiates a new service result.
     *
     * @param successful the successful
     * @param messages the messages
     */
    public ServiceResult(final boolean successful, final List<Message> messages) {
        this(successful, null, messages);
    }

    /**
     * Instantiates a new service result.
     *
     * @param successful the successful
     * @param messages the messages
     */
    public ServiceResult(final boolean successful, final Message... messages) {
        this(successful, null, Arrays.asList(messages));
    }

    /**
     * Gets the error code.
     *
     * @return the error code
     */
    public ServiceErrorCode getErrorCode() {
        return this.errorCode;
    }

    /**
     * Checks if is successful.
     *
     * @return true, if is successful
     */
    public boolean isSuccessful() {
        return this.successful;
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public T getData() {
        return this.data;
    }

    /**
     * Gets the messages.
     *
     * @return the messages
     */
    public List<Message> getMessages() {
        return this.messages;
    }

    /**
     * Gets the first message.
     *
     * @return the first message
     */
    public Message getFirstMessage() {
        return ((this.messages != null) && !this.messages.isEmpty()) ? this.messages.get(0) : null;
    }

    /**
     * Show messages with faces context.
     *
     * @param facesContext the faces context
     */
    public void showMessages(final FacesContext facesContext) {
        if ((this.messages != null) && !this.messages.isEmpty()) {
            for (final Message message : this.getMessages()) {
                message.show(facesContext);
            }
        }
    }

    /**
     * Show messages without faces context.
     */
    public void showMessages() {
        if ((this.messages != null) && !this.messages.isEmpty()) {
            for (final Message message : this.getMessages()) {
                message.show();
            }
        }
    }

    /**
     * Checks if is not successful.
     *
     * @return true, if is not successful
     */
    public boolean isNotSuccessful() {
        return !this.successful;
    }
}
