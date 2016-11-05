package arrow.framework.faces.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.convert.ConverterException;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;

/**
 * The Class ConverterUtils.
 */
public class ConverterUtils {

    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger(ConverterUtils.class);

    /**
     * Throw converter exception.
     *
     * @param component the component
     * @param msgKey the msg key
     * @param params the params
     * @return the converter exception
     */
    public static ConverterException throwConverterException(final UIComponent component, final String msgKey,
            final Object... params) {
        return ConverterUtils.throwConverterException(component,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, msgKey, msgKey));
    }

    /**
     * Throw converter exception.
     *
     * @param component the component
     * @param cause the cause
     * @param msgKey the msg key
     * @param params the params
     * @return the converter exception
     */
    public static ConverterException throwConverterException(final UIComponent component, final Throwable cause,
            final String msgKey, final Object... params) {
        return ConverterUtils.throwConverterException(component,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, msgKey, msgKey), cause);
    }

    /**
     * Throw converter exception.
     *
     * @param component the component
     * @param facesMessage the faces message
     * @param cause the cause
     * @return the converter exception
     */
    private static ConverterException throwConverterException(final UIComponent component,
            final FacesMessage facesMessage, final Throwable cause) {
        // Make sure the cause was shown out somewhere at server log.
        ConverterUtils.LOGGER.info("Exception when converting", cause);
        return ConverterUtils.throwConverterException(component, new ConverterException(facesMessage, cause));
    }

    /**
     * Throw converter exception.
     *
     * @param component the component
     * @param facesMessage the faces message
     * @return the converter exception
     */
    private static ConverterException throwConverterException(final UIComponent component,
            final FacesMessage facesMessage) {
        return ConverterUtils.throwConverterException(component, new ConverterException(facesMessage));
    }

    /**
     * Throw converter exception.
     *
     * @param component the component
     * @param exc the e
     * @return the converter exception
     */
    private static ConverterException throwConverterException(final UIComponent component,
            final ConverterException exc) {
        UIComponentUtils.resetInputValueIfPossible(component);
        return exc;
    }
}
