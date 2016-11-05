package arrow.framework.faces.converter;

import javax.faces.component.UIComponent;

import arrow.framework.faces.util.ConverterUtils;
import arrow.framework.util.i18n.Messages;


/**
 * The Class NonNegativeNumberConverter.
 */
public class NonNegativeNumberConverter extends BaseNumberConverter {

    /* (non-Javadoc)
     * @see arrow.framework.faces.converter.BaseNumberConverter<br />
     * #validate(javax.faces.component.UIComponent, java.lang.Number)
     */
    @Override
    protected Number validate(final UIComponent component, final Number value) {
        if (value.doubleValue() < 0.0) {
            throw ConverterUtils.throwConverterException(component, Messages.get("converter.valueMustBeNonNegative"));
        }

        return super.validate(component, value);
    }
}
