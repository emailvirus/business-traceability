package arrow.framework.util.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


/**
 * UTF-8 friendly ResourceBundle support Utility that allows having multi-byte characters inside java .property files.
 * It removes the need for Sun's native2ascii application, you can simply have UTF-8 encoded editable .property files.
 * Use: ResourceBundle bundle = Utf8ResourceBundle.getBundle("bundle_name");
 *
 * @author Tomas Varaneckas tomas.varaneckas@gmail.com
 */
public abstract class Utf8ResourceBundle {

    /**
     * Gets the unicode friendly resource bundle.
     *
     * @param baseName the base name
     * @param locale the locale
     * @return Unicode friendly resource bundle
     * @see ResourceBundle#getBundle(String)
     */
    public static final ResourceBundle getBundle(final String baseName, final Locale locale) {
        return Utf8ResourceBundle.createUtf8PropertyResourceBundle(ResourceBundle.getBundle(baseName, locale));
    }

    /**
     * Creates unicode friendly {@link PropertyResourceBundle} if possible.
     *
     * @param bundle the bundle
     * @return Unicode friendly property resource bundle
     */
    private static ResourceBundle createUtf8PropertyResourceBundle(final ResourceBundle bundle) {
        if (!(bundle instanceof PropertyResourceBundle)) {
            return bundle;
        }
        return new Utf8PropertyResourceBundle((PropertyResourceBundle) bundle);
    }

    /**
     * Resource Bundle that does the hard work.
     */
    private static final class Utf8PropertyResourceBundle extends ResourceBundle {

        /** Bundle with unicode data. */
        private final PropertyResourceBundle bundle;

        /**
         * Initializing constructor.
         *
         * @param bundle the bundle
         */
        private Utf8PropertyResourceBundle(final PropertyResourceBundle bundle) {
            this.bundle = bundle;
        }

        /* (non-Javadoc)
         * @see java.util.ResourceBundle#getKeys()
         */
        @Override
        public Enumeration<String> getKeys() {
            return this.bundle.getKeys();
        }

        /* (non-Javadoc)
         * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
         */
        @Override
        protected Object handleGetObject(final String key) {
            try {
                final String value = this.bundle.getString(key);
                return new String(value.getBytes("ISO-8859-1"), "UTF-8");
            } catch (final UnsupportedEncodingException e) {
                throw new RuntimeException("Encoding not supported", e);
            }
        }
    }
}
