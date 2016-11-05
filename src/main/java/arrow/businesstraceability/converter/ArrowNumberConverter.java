package arrow.businesstraceability.converter;

import javax.faces.convert.NumberConverter;

public class ArrowNumberConverter extends NumberConverter {

    private static final String CURRENCY_PATTERN = "#,##0";

    public static String getCurrencyPattern() {
        return ArrowNumberConverter.CURRENCY_PATTERN;
    }

}
