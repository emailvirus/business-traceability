package arrow.framework.faces.util;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.omnifaces.util.Components;

import arrow.framework.util.StringUtils;
import arrow.framework.util.i18n.Messages;


/**
 * The following class stores and converts the messages encountered during InvokeApplication phase. The messages are
 * supposed to be displayed using Primefaces' Growl component, hence the processing in the class is specific for growl
 * (for example, it uses HTML line break tag to allow to display multiple messages in the same faces message, a feature
 * that the default JSF messages don't allow).
 *
 * @author linhbn
 */
@Named
@RequestScoped
public class ApplicationMessageStorage {


    /**
     * The Class Message.
     */
    public static class Message {
        // This attribute serves as an additional piece of info. For now, we
        // don't have any further processing regarding it (the messages are all
        // displayed in groups of severities, no component specific). However,
        // later we might want to refer to the error / warning messages of a specific
        /** The client id. */
        // component, e.g. in Auto Re Render feature.
        private final String clientId;

        /**
         * Gets the client id.
         *
         * @return the client id
         */
        public String getClientId() {
            return this.clientId;
        }

        /** The component label. */
        private final String componentLabel;

        /**
         * Gets the component label.
         *
         * @return the component label
         */
        public String getComponentLabel() {
            return this.componentLabel;
        }

        /** The key. */
        private final String key;

        /**
         * Gets the key.
         *
         * @return the key
         */
        public String getKey() {
            return this.key;
        }

        /** The params. */
        private final Object[] params;

        /**
         * Gets the params.
         *
         * @return the params
         */
        public Object[] getParams() {
            return this.params;
        }

        /**
         * Instantiates a new message.
         *
         * @param key the key
         * @param params the params
         */
        public Message(final String key, final Object... params) {
            this(null, null, key, params);
        }

        /**
         * Instantiates a new message.
         *
         * @param clientId the client id
         * @param componentLabel the component label
         * @param key the key
         * @param params the params
         */
        public Message(final String clientId, final String componentLabel, final String key, final Object... params) {
            this.clientId = clientId;
            this.key = key;
            this.params = params;
            this.componentLabel = componentLabel;
        }

        /**
         * Gets the localized message.
         *
         * @return the localized message
         */
        public String getLocalizedMessage() {
            return (StringUtils.isEmpty(this.componentLabel) ? "" : this.componentLabel + ": ")
                    + Messages.get(this.key, this.params);
        }
    }

    /** The error list. */
    // SEVERITY_ERROR
    private final List<Message> errorList = new ArrayList<Message>();

    /**
     * Error.
     *
     * @param key the key
     * @param params the params
     */
    public void error(final String key, final Object... params) {
        this.errorList.add(new Message(key, params));
    }

    /**
     * Error.
     *
     * @param context the context
     * @param component the component
     * @param key the key
     * @param params the params
     */
    public void error(final FacesContext context, final UIComponent component, final String key, final Object... params) {
        this.errorList.add(new Message(component.getClientId(context), Components.getLabel(component), key, params));
    }

    /** The info list. */
    // SEVERITY_INFO
    private final List<Message> infoList = new ArrayList<Message>();

    /**
     * Info.
     *
     * @param key the key
     * @param params the params
     */
    public void info(final String key, final Object... params) {
        this.infoList.add(new Message(key, params));
    }

    /**
     * Info.
     *
     * @param context the context
     * @param component the component
     * @param key the key
     * @param params the params
     */
    public void info(final FacesContext context, final UIComponent component, final String key, final Object... params) {
        this.infoList.add(new Message(component.getClientId(context), Components.getLabel(component), key, params));
    }

    /** The warn list. */
    // SEVERITY_WARN
    private final List<Message> warnList = new ArrayList<Message>();

    /**
     * Warn.
     *
     * @param key the key
     * @param params the params
     */
    public void warn(final String key, final Object... params) {
        this.warnList.add(new Message(key, params));
    }

    /**
     * Warn.
     *
     * @param context the context
     * @param component the component
     * @param key the key
     * @param params the params
     */
    public void warn(final FacesContext context, final UIComponent component, final String key, final Object... params) {
        this.warnList.add(new Message(component.getClientId(context), Components.getLabel(component), key, params));
    }

    /** The fatal list. */
    // SEVERITY_FATAL
    private final List<Message> fatalList = new ArrayList<Message>();

    /**
     * Fatal.
     *
     * @param key the key
     * @param params the params
     */
    public void fatal(final String key, final Object... params) {
        this.fatalList.add(new Message(key, params));
    }

    /**
     * Fatal.
     *
     * @param context the context
     * @param component the component
     * @param key the key
     * @param params the params
     */
    public void fatal(final FacesContext context, final UIComponent component, final String key, final Object... params) {
        this.fatalList.add(new Message(component.getClientId(context), Components.getLabel(component), key, params));
    }

    /**
     * Push to faces context.
     *
     * @param messageList the message list
     * @param summary the summary
     * @param severity the severity
     * @return true if there's something to push into faces context. false otherwise
     */
    private boolean pushToFacesContext(final List<Message> messageList, final String summary, final Severity severity) {
        StringBuffer localizedMessage = new StringBuffer();

        for (final Message message : messageList) {
            if (localizedMessage.length() == 0) {
                // Break the messages of the same kind to separate lines.
                localizedMessage.append("<br />");
            }
            localizedMessage.append(message.getLocalizedMessage());
        }

        if (localizedMessage.length() == 0) {
            // The messages are supposed to be displayed using Primefaces'
            // Growl. Besides, it's the combination of many separate
            // messages. Hence, no need to include clientId here.
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(severity, summary, localizedMessage.toString()));
            return true;
        }

        return false;
    }

    /**
     * Push to faces context.
     *
     * @return true if having something to push into faces context. false otherwise.
     */
    public boolean pushToFacesContext() {
        boolean dirty = false;

        if (this.pushToFacesContext(this.errorList, Messages.get("errors"), FacesMessage.SEVERITY_ERROR)) {
            FacesContext.getCurrentInstance().validationFailed();
            dirty = true;
        }

        this.errorList.clear();

        if (this.pushToFacesContext(this.fatalList, Messages.get("fatalErrors"), FacesMessage.SEVERITY_FATAL)) {
            FacesContext.getCurrentInstance().validationFailed();
            dirty = true;
        }

        this.fatalList.clear();

        if (this.pushToFacesContext(this.infoList, Messages.get("info"), FacesMessage.SEVERITY_INFO)) {
            dirty = true;
        }

        this.infoList.clear();

        if (this.pushToFacesContext(this.warnList, Messages.get("warnings"), FacesMessage.SEVERITY_WARN)) {
            dirty = true;
        }

        this.warnList.clear();

        return dirty;
    }
}
