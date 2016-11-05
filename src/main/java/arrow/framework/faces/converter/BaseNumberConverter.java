package arrow.framework.faces.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.NumberConverter;

import org.omnifaces.util.Components;

import arrow.framework.faces.util.ConverterUtils;
import arrow.framework.util.i18n.Messages;


/**
 * The Class BaseNumberConverter.
 */
public class BaseNumberConverter extends NumberConverter {

    /** The Constant TWO_VALUE. */
    public static final NumberFormat TWO_VALUE = new DecimalFormat("00");

    /** The Constant INPUT_REQUIRED_MESSAGE. */
    public static final String INPUT_REQUIRED_MESSAGE = "converter.inputRequired";

    /** The Constant INVALID_NUMBER_MESSAGE. */
    public static final String INVALID_NUMBER_MESSAGE = "converter.invalidNumber";

    /** The Constant NUMBER_NOT_WITHIN_RANGE_MESSAGE. */
    public static final String NUMBER_NOT_WITHIN_RANGE_MESSAGE = "converter.numberNotWithinRange";

    /** The Constant TOO_MANY_DECIMAL_MESSAGE. */
    public static final String TOO_MANY_DECIMAL_MESSAGE = "converter.tooManyDecimals";

    /**
     * Checks if is convert to long type.
     *
     * @param convertTo the convert to
     * @return true, if is convert to long type
     */
    private static boolean isConvertToLongType(final String convertTo) {
        return (convertTo != null) && convertTo.trim().equalsIgnoreCase("Long");
    }

    /**
     * Checks if is convert to integer type.
     *
     * @param convertTo the convert to
     * @return true, if is convert to integer type
     */
    private static boolean isConvertToIntegerType(final String convertTo) {
        return (convertTo != null) && convertTo.trim().equalsIgnoreCase("Integer");
    }

    /**
     * Subclasses need to provide specific implementation of this method. By default this method assumes that the value
     * is valid.
     *
     * @param component the component
     * @param value the value
     * @return the number
     */
    protected Number validate(final UIComponent component, final Number value) {
        return value;
    }

    /* (non-Javadoc)
     * @see javax.faces.convert.NumberConverter<br />
     * #getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
     */
    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String input) {
        if ((input == null) || (input.trim().length() == 0)) {
            return this.getAsObjectForEmptyValue(component);
        }


        final String convertTo = Components.getAttribute(component, "convertTo");
        try {
            final DecimalFormatSymbols dfs = new DecimalFormatSymbols(context.getViewRoot().getLocale());
            final String noGSInput = input.replace(((Character) dfs.getGroupingSeparator()).toString(), "");
            final double doubleValue = Double.parseDouble(noGSInput);

            if (BaseNumberConverter.isConvertToLongType(convertTo)) {
                if ((doubleValue > Long.MAX_VALUE) || (doubleValue < Long.MIN_VALUE)) {
                    throw ConverterUtils.throwConverterException(component,
                            BaseNumberConverter.NUMBER_NOT_WITHIN_RANGE_MESSAGE);
                }

                Long.parseLong(noGSInput);
            } else if (BaseNumberConverter.isConvertToIntegerType(convertTo)) {
                if ((doubleValue > Integer.MAX_VALUE) || (doubleValue < Integer.MIN_VALUE)) {
                    throw ConverterUtils.throwConverterException(component,
                            BaseNumberConverter.NUMBER_NOT_WITHIN_RANGE_MESSAGE);
                }

                Integer.parseInt(noGSInput);
            }
        } catch (final NumberFormatException e) {
            throw ConverterUtils.throwConverterException(component, e,
                    Messages.get(BaseNumberConverter.INVALID_NUMBER_MESSAGE));
        }

        Number converted = null;
        Number rounded = null;

        try {
            converted = (Number) super.getAsObject(context, component, input);
            rounded = (Number) super.getAsObject(context, component, super.getAsString(context, component, converted));
        } catch (final ConverterException e) {
            throw ConverterUtils.throwConverterException(component, e,
                    Messages.get(BaseNumberConverter.INVALID_NUMBER_MESSAGE));
        }

        if (!converted.equals(rounded)) {
            throw ConverterUtils.throwConverterException(component, BaseNumberConverter.TOO_MANY_DECIMAL_MESSAGE);
        }

        if (BaseNumberConverter.isConvertToLongType(convertTo)) {
            return converted.longValue();
        } else if (BaseNumberConverter.isConvertToIntegerType(convertTo)) {
            return this.validate(component, converted.intValue());
        }
        else {
            return this.validate(component, converted.doubleValue());
        }
    }

    /**
     * Gets the as object for empty value.
     *
     * @param component the component
     * @return the as object for empty value
     */
    private Object getAsObjectForEmptyValue(final UIComponent component) {
        final Boolean required = Components.getAttribute(component, "required");
        final String convertTo = Components.getAttribute(component, "convertTo");

        if ((required != null) && required) {
            throw ConverterUtils.throwConverterException(component, BaseNumberConverter.INPUT_REQUIRED_MESSAGE);
        }

        Number value = null;

        if (BaseNumberConverter.isConvertToLongType(convertTo)) {
            value = Long.valueOf(0);
        } else if (BaseNumberConverter.isConvertToIntegerType(convertTo)) {
            value = Integer.valueOf(0);
        } else {
            value = Double.valueOf(0);
        }

        return this.validate(component, value);
    }
}
