package arrow.framework.faces.message;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import arrow.framework.faces.util.UIComponentUtils;
import arrow.framework.util.collections.CollectionUtils;
import arrow.framework.util.i18n.Messages;

import arrow.businesstraceability.control.bean.ErrorCodeClientIdMappingManager;


/**
 * The Class Message.
 */
public abstract class Message {

    /** The Constant DETAIL_SUFFIX. */
    protected static final String DETAIL_SUFFIX = ".DETAIL";

    /** The message code. */
    protected final String messageCode;

    /** The summary key. */
    protected final String summaryKey;

    //

    /** The detail key. */
    protected final String detailKey;

    /** The params. */
    protected Object[] params;

    //
    /**
     * Instantiates a new message.
     *
     * @param messageCode the message code
     * @param summaryKey the summary key
     * @param detailKey the detail key
     * @param params the params
     */
    public Message(final String messageCode, final String summaryKey, final String detailKey, final Object... params) {
        this.messageCode = messageCode;
        this.detailKey = detailKey;
        this.summaryKey = summaryKey;
        this.params = params;
    }

    /**
     * Convert severity.
     *
     * @param severity the severity
     * @return the faces message. severity
     */
    private static FacesMessage.Severity convertSeverity(final Severity severity) {
        if (severity == Severity.ERROR) {
            return FacesMessage.SEVERITY_ERROR;
        }
        if (severity == Severity.WARNING) {
            return FacesMessage.SEVERITY_WARN;
        }
        if (severity == Severity.INFO) {
            return FacesMessage.SEVERITY_INFO;
        }

        throw new IllegalArgumentException("Unsupported Severity Type");
    }

    /**
     * Gets the severity.
     *
     * @return the severity
     */
    public abstract Severity getSeverity();

    /**
     * Gets the message code.
     *
     * @return the message code
     */
    public String getMessageCode() {
        return this.messageCode;
    }

    /**
     * Creates the face message.
     *
     * @return the faces message
     */
    public FacesMessage createFaceMessage() {

        final String translatedMsg = Messages.get(this.summaryKey, this.params);

        // If .DETAIL key wasn't defined in properties, then use summary key instead.
        return new FacesMessage(Message.convertSeverity(this.getSeverity()), translatedMsg, translatedMsg);
    }

    /**
     * Shortcut to {@see Message.show}
     */
    public void show() {
        this.show(FacesContext.getCurrentInstance());
    }

    /**
     * Show.
     *
     * @param facesContext the faces context
     */
    public void show(final FacesContext facesContext) {
        final ErrorCodeClientIdMappingManager errorCodeClientIdMappingManager =
                facesContext.getApplication().evaluateExpressionGet(facesContext, "#{errorCodeClientIdMappingManager}",
                        ErrorCodeClientIdMappingManager.class);

        final Map<String, List<String>> messageCodeClientIdsMap =
                errorCodeClientIdMappingManager.getMessageCodeClientIdsMap();

        final FacesMessage faceMessage = this.createFaceMessage();

        if (messageCodeClientIdsMap != null) {
            final List<String> clientIds = messageCodeClientIdsMap.get(this.messageCode);
            if (CollectionUtils.isNotEmpty(clientIds)) {
                for (final String clientId : clientIds) {
                    facesContext.addMessage(clientId, faceMessage);
                    final UIInput inputComponent = UIComponentUtils.findComponentById(UIInput.class, clientId);
                    if (inputComponent != null) {
                        inputComponent.setValid(false);
                    }
                }
            } else {
                facesContext.addMessage(null, faceMessage);
            }
        }
    }

    /**
     * Gets the params.
     *
     * @return the params
     */
    public Object[] getParams() {
        return this.params;
    }

    /**
     * Sets the params.
     *
     * @param params the new params
     */
    public void setParams(final Object[] params) {
        this.params = params;
    }

    /**
     * The Enum Severity.
     */
    public static enum Severity {

        /**
         * The error.
         */
        ERROR,
        /**
         * The warning.
         */
        WARNING,
        /**
         * The info.
         */
        INFO;
    }
}
