package arrow.businesstraceability.converter;

import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;

public class ArrowConverterFactory {

    /**
     * Currency Converter.
     * @return
     */
    public static Converter getCurrencyConverter() {
        NumberConverter converter = new ArrowNumberConverter();
        converter.setPattern(ArrowNumberConverter.getCurrencyPattern());
        return converter;
    }
}
