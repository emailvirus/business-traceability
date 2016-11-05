package arrow.framework.util.i18n;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.omnifaces.util.Faces;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.util.cdi.Instance;
import arrow.framework.util.collections.CombinedEnumeration;

/**
 * The Class BaseResourceBundle.
 */
public class BaseResourceBundle extends ResourceBundle {

    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger(BaseResourceBundle.class);

    /**
     * The bundle cache.
     */
    private final Map<Locale, List<ResourceBundle>> bundleCache = new ConcurrentHashMap<Locale, List<ResourceBundle>>();

    /**
     * Gets the bundle.
     *
     * @return a SynResourceBundle
     */
    public static ResourceBundle getBundle() {
        return ResourceBundle.getBundle(BaseResourceBundle.class.getName());
    }

    /**
     * Gets the current locale.
     *
     * @return the current locale
     */
    public static Locale getCurrentLocale() {

        // if session is valid, use default locale
        if (Instance.get(ILocaleSelector.class).getLocale() == null) {
            return Faces.getDefaultLocale();
        }

        // else get current locale
        return Instance.get(ILocaleSelector.class).getLocale();
    }

    /* (non-Javadoc)
     * @see java.util.ResourceBundle#getKeys()
     */
    @Override
    public Enumeration<String> getKeys() {
        final List<ResourceBundle> bundles = this.getBundlesForCurrentLocale();

        @SuppressWarnings("unchecked")
        final Enumeration<String>[] enumerations = new Enumeration[bundles.size()];

        int index = 0;

        for (final ResourceBundle bundle : bundles) {
            enumerations[index++] = bundle.getKeys();
        }

        return new CombinedEnumeration<>(enumerations);
    }

    // private static Locale currentLocale = SysConfig.getDefaultLocale();

    /* (non-Javadoc)
     * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
     */
    @Override
    protected Object handleGetObject(final String key) {
        for (final ResourceBundle littleBundle : this.getBundlesForCurrentLocale()) {
            try {
                return littleBundle.getObject(key);
            } catch (final MissingResourceException mre) {
                continue;
            }
        }

        BaseResourceBundle.LOGGER.debug("Failed when try to get message key:" + key);
        // TODO: to do something
        return null; // superclass is responsible for throwing MRE
    }

    // private helpers

    /* (non-Javadoc)
     * @see java.util.ResourceBundle#getLocale()
     */
    @Override
    public Locale getLocale() {
        return BaseResourceBundle.getCurrentLocale();
    }

    /**
     * Gets the bundles for current locale.
     *
     * @return the bundles for current locale
     */
    private List<ResourceBundle> getBundlesForCurrentLocale() {
        final Locale locale = BaseResourceBundle.getCurrentLocale();

        List<ResourceBundle> bundles = this.bundleCache.get(locale);
        if (bundles == null) {
            bundles = this.loadBundlesForCurrentLocale();
            this.bundleCache.put(locale, bundles);
        }

        return bundles;
    }

    /**
     * Load bundles for current locale.
     *
     * @return the list
     */
    private List<ResourceBundle> loadBundlesForCurrentLocale() {
        final Locale locale = BaseResourceBundle.getCurrentLocale();
        final List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();

        // load all available resource bundles
        bundles.add(Utf8ResourceBundle.getBundle("arrow.i18n.messages", locale));
        bundles.add(Utf8ResourceBundle.getBundle("arrow.i18n.error-messages", locale));
        bundles.add(Utf8ResourceBundle.getBundle("arrow.i18n.database_labels", locale));

        // built-in bundles
        // Bean Validation 1.1 (JSR-349)
        // bundles.add(ResourceBundle.getBundle("ValidationMessage", locale));

        // JSF 2.2 (JSR-344)
        // bundles.add(ResourceBundle.getBundle("javax.faces.Messages", locale));

        // To override built-in bundles
        // http://stackoverflow.com/questions/2668161/when-to-use-message-bundle-and-resource-bundle

        return Collections.unmodifiableList(bundles);
    }
}
