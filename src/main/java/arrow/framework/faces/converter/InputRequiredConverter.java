package arrow.framework.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.omnifaces.util.Components;

import arrow.framework.faces.util.ConverterUtils;


/**
 * The Class InputRequiredConverter.
 */
public abstract class InputRequiredConverter implements Converter {

    /* (non-Javadoc)
     * @see javax.faces.convert.Converter<br />
     * #getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
     */
    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String input) {
        if ((input == null) || (input.trim().length() == 0)) {
            final Boolean required = Components.getAttribute(component, "required");

            if ((required != null) && required) {
                throw ConverterUtils.throwConverterException(component, "converter.inputRequired");
            }
        }

        return input.trim();
    }

    /* (non-Javadoc)
     * @see javax.faces.convert.Converter<br />
     * #getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return ((String) value).trim();
        }

        return value.toString();
    }
}
