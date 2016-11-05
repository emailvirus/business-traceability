package arrow.framework.faces.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;

import org.omnifaces.util.Components;

import arrow.framework.faces.util.UIComponentUtils;
import arrow.framework.util.i18n.Messages;



/**
 * The Class ValidatorUtils.
 */
public class ValidatorUtils {

    /**
     * Compose and return ValidatorException. Prefer returning ValidatorException instead of just throw this inside this
     * method to explicitly tell the Java complier that this method will throw ValidatorException. It's good in case the
     * caller method need to return a type instead of void.
     *
     * @param component the component
     * @param msgKey the msg key
     * @param params the params
     * @return the validator exception
     */
    public static ValidatorException throwValidatorException(final UIComponent component, final String msgKey,
            final Object... params) {
        final FacesMessage localizedFacesMessage = ValidatorUtils.buildLocalizedFacesMessage(component, msgKey, params);

        return ValidatorUtils.throwValidatorException(component, localizedFacesMessage);
    }

    /**
     * Builds the localized faces message.
     *
     * @param component the component
     * @param msgKey the msg key
     * @param params the params
     * @return the faces message
     */
    private static FacesMessage buildLocalizedFacesMessage(final UIComponent component, final String msgKey,
            final Object... params) {
        String label = Components.getLabel(component);
        if ((label == null) || label.trim().equals("")) {
            label = component.getId();
        }
        final String localizedMsg = label.trim() + ": " + Messages.get(msgKey, params);

        return new FacesMessage(localizedMsg);
    }

    /**
     * Throw validator exception.
     *
     * @param component the component
     * @param facesMessage the faces message
     * @return the validator exception
     */
    private static ValidatorException throwValidatorException(final UIComponent component,
            final FacesMessage facesMessage) {
        UIComponentUtils.resetInputValueIfPossible(component);
        return new ValidatorException(facesMessage);
    }
}
