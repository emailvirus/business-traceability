package arrow.businesstraceability.control.helper;

import java.io.Serializable;

import arrow.framework.faces.message.Message;


/**
 * Created by michael on 30/10/2015.
 */
public class ServiceErrorCode implements Serializable {

    /** The Constant ERR_NOT_FOUND. */
    public static final ServiceErrorCode ERR_NOT_FOUND = new ServiceErrorCode("not_found", null);

    /** The error code. */
    private final String errorCode;

    /** The message. */
    private final Message message;

    /**
     * Instantiates a new service error code.
     *
     * @param errorCode the error code
     * @param msg the msg
     */
    private ServiceErrorCode(final String errorCode, final Message msg) {
        this.errorCode = errorCode;
        this.message = msg;
    }
}
