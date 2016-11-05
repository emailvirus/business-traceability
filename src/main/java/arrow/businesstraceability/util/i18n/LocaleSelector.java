/*
 * package: arrow.framework.util.i18n file: LocaleSelector.java time: Jun 27, 2014
 * @author michael
 */
package arrow.businesstraceability.util.i18n;

import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import arrow.businesstraceability.config.SysConfig;
import arrow.businesstraceability.control.bean.ScreenBean;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.framework.util.cdi.Instance;
import arrow.framework.util.i18n.ILocaleSelector;


/**
 * The Class LocaleSelector.
 */
@Named
@SessionScoped
public class LocaleSelector implements ILocaleSelector {

    /** The current user. */
    @Inject
    private UserCredential currentUser;

    @Inject
    protected ScreenBean screenBean;

    /**
     * Gets the all supported locales.
     *
     * @return the all supported locales
     */
    public List<Locale> getAllSupportedLocales() {
        return Instance.get(SysConfig.class).getSupportedLocales();
    }

    /**
     * Sets the locale.
     *
     * @param lang the new locale
     */
    public void setLocale(final String lang) {
        Locale locale = Locale.forLanguageTag(lang);
        this.setLocale(locale);
        this.screenBean.switchToHome();
    }

    /**
     * Gets the locale.
     *
     * @return the locale
     */
    @Override
    public Locale getLocale() {
        if ((this.currentUser != null) && this.currentUser.isLoggedIn()) {
            return this.currentUser.getCurrentLocale();
        }
        return SysConfig.getDefaultLocale();
    }

    /**
     * Sets the locale.
     *
     * @param locale the new locale
     */
    public void setLocale(final Locale locale) {
        if (this.currentUser != null) {
            this.currentUser.setCurrentLocale(locale);
            this.currentUser.setSelectedLocale(locale.getLanguage());
            Faces.setLocale(locale);
        }
    }

}
