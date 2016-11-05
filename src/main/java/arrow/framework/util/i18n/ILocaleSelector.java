package arrow.framework.util.i18n;

import java.io.Serializable;
import java.util.Locale;


/**
 * The Interface ILocaleSelector.
 */
public interface ILocaleSelector extends Serializable {

    /**
     * Gets the locale.
     *
     * @return the locale
     */
    Locale getLocale();
}
