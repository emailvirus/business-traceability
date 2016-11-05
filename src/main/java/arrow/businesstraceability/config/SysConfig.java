/*
 * package: arrow.businesstraceability.config file: SysConfig.java time: Jun 27, 2014
 * @author michael
 */
package arrow.businesstraceability.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;

import com.google.common.base.Joiner;

import arrow.businesstraceability.util.SelectItemGenerator;
import arrow.businesstraceability.util.SelectItemGenerator.ListType;
import arrow.framework.faces.util.FacesELUtils;
import arrow.framework.faces.util.LabelKeySelectItem;
import arrow.framework.logging.BaseLogger;


/**
 * The Class SysConfig.
 */
@Named
@ApplicationScoped
public class SysConfig {

    /** The Constant HOURS_SELECT_ITEMS. */
    // to increase performance
    private static final List<SelectItem> HOURS_SELECT_ITEMS;

    /** The Constant MINUTES_SELECT_ITEMS. */
    private static final List<SelectItem> MINUTES_SELECT_ITEMS;

    /** The Constant MONTHS_SELECT_ITEMS. */
    private static final List<SelectItem> MONTHS_SELECT_ITEMS;

    /** The Constant YEARS_SELECT_ITEMS. */
    private static final List<SelectItem> YEARS_SELECT_ITEMS;

    /** The Constant ON_OFF_SELECT_ITEMS. */
    private static final List<SelectItem> ON_OFF_SELECT_ITEMS;

    private static final String[] DOCS_FILE_EXTENTIONS = new String[] {"pdf", "doc", "docx", "xls", "xlsx"};

    public static final String SEPARATOR = "|";

    private static final String UPLOAD_TEMP_DIR_PARAM_NAME = "UPLOAD_ACCOUNTING_TEMP_DIR";

    public static final String UPLOAD_ACCOUNTING_DIR = "UPLOAD_ACCOUNTING_DIR";

    // 100MB
    public static final int DOCUMENT_FILE_SIZE_ALLOWED = 102400000;

    // One file
    public static final int DOCUMENT_FILE_LIMIT = 1;

    /** The Constant DEFAULT_THEME. */
    public static final String DEFAULT_THEME = "humanity";

    static {
        HOURS_SELECT_ITEMS = SysConfig.buildHourSelectItems();
        MINUTES_SELECT_ITEMS = SysConfig.buildMinuteSelectItems();
        MONTHS_SELECT_ITEMS = SysConfig.buildMonthSelectItems();
        YEARS_SELECT_ITEMS = SysConfig.buildYearSelectItems();
        ON_OFF_SELECT_ITEMS = SysConfig.buildOnOffSelectItems();
    }

    /**
     * Builds the hour select items.
     *
     * @return the list
     */
    private static List<SelectItem> buildHourSelectItems() {
        final List<SelectItem> selectItemList = new ArrayList<SelectItem>();

        // normally, sales persons start working from 07 or 08 AM, so we make it as first items to
        // save
        // a bit time.
        selectItemList.add(new SelectItem(7, "07"));
        selectItemList.add(new SelectItem(8, "08"));
        selectItemList.add(new SelectItem(9, "09"));
        selectItemList.add(new SelectItem(10, "10"));
        selectItemList.add(new SelectItem(11, "11"));
        selectItemList.add(new SelectItem(12, "12"));
        selectItemList.add(new SelectItem(13, "13"));
        selectItemList.add(new SelectItem(14, "14"));
        selectItemList.add(new SelectItem(15, "15"));
        selectItemList.add(new SelectItem(16, "16"));
        selectItemList.add(new SelectItem(17, "17"));
        selectItemList.add(new SelectItem(18, "18"));
        selectItemList.add(new SelectItem(19, "19"));
        selectItemList.add(new SelectItem(20, "20"));
        selectItemList.add(new SelectItem(21, "21"));
        selectItemList.add(new SelectItem(22, "22"));
        selectItemList.add(new SelectItem(23, "23"));
        selectItemList.add(new SelectItem(1, "01"));
        selectItemList.add(new SelectItem(2, "02"));
        selectItemList.add(new SelectItem(3, "03"));
        selectItemList.add(new SelectItem(4, "04"));
        selectItemList.add(new SelectItem(5, "05"));
        selectItemList.add(new SelectItem(6, "06"));
        return selectItemList;
    }

    /**
     * Builds the minute select items.
     *
     * @return the list
     */
    private static List<SelectItem> buildMinuteSelectItems() {
        final List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        selectItemList.add(new SelectItem(0, "00"));
        selectItemList.add(new SelectItem(10, "10"));
        selectItemList.add(new SelectItem(20, "20"));
        selectItemList.add(new SelectItem(30, "30"));
        selectItemList.add(new SelectItem(40, "40"));
        selectItemList.add(new SelectItem(50, "50"));
        return selectItemList;
    }

    /**
     * Builds the month select items.
     *
     * @return the list
     */
    private static List<SelectItem> buildMonthSelectItems() {
        final List<SelectItem> selectItemList = new ArrayList<SelectItem>();

        // normally, sales persons start working from 07 or 08 AM, so we make it as first items to
        // save
        // a bit time.
        selectItemList.add(new SelectItem(1, "01"));
        selectItemList.add(new SelectItem(2, "02"));
        selectItemList.add(new SelectItem(3, "03"));
        selectItemList.add(new SelectItem(4, "04"));
        selectItemList.add(new SelectItem(5, "05"));
        selectItemList.add(new SelectItem(6, "06"));
        selectItemList.add(new SelectItem(7, "07"));
        selectItemList.add(new SelectItem(8, "08"));
        selectItemList.add(new SelectItem(9, "09"));
        selectItemList.add(new SelectItem(10, "10"));
        selectItemList.add(new SelectItem(11, "11"));
        selectItemList.add(new SelectItem(12, "12"));
        return selectItemList;
    }

    /**
     * Builds the year select items.
     *
     * @return the list
     */
    private static List<SelectItem> buildYearSelectItems() {
        final List<SelectItem> selectItemList = new ArrayList<SelectItem>();

        // normally, sales persons start working from 07 or 08 AM, so we make it as first items to
        // save
        // a bit time.
        selectItemList.add(new SelectItem(2010, "2010"));
        selectItemList.add(new SelectItem(2011, "2011"));
        selectItemList.add(new SelectItem(2012, "2012"));
        selectItemList.add(new SelectItem(2013, "2013"));
        selectItemList.add(new SelectItem(2014, "2014"));
        selectItemList.add(new SelectItem(2015, "2015"));
        selectItemList.add(new SelectItem(2016, "2016"));
        selectItemList.add(new SelectItem(2017, "2017"));
        selectItemList.add(new SelectItem(2018, "2018"));
        selectItemList.add(new SelectItem(2019, "2019"));
        selectItemList.add(new SelectItem(2020, "2020"));
        selectItemList.add(new SelectItem(2021, "2021"));

        selectItemList.add(new SelectItem(2022, "2022"));
        selectItemList.add(new SelectItem(2023, "2023"));
        selectItemList.add(new SelectItem(2024, "2024"));
        selectItemList.add(new SelectItem(2025, "2025"));
        selectItemList.add(new SelectItem(2026, "2026"));
        selectItemList.add(new SelectItem(2027, "2027"));
        selectItemList.add(new SelectItem(2028, "2028"));
        selectItemList.add(new SelectItem(2029, "2029"));
        selectItemList.add(new SelectItem(2030, "2030"));
        return selectItemList;
    }

    /**
     * Builds the on off select items.
     *
     * @return the list
     */
    private static List<SelectItem> buildOnOffSelectItems() {
        final List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        selectItemList.add(new LabelKeySelectItem(true, "ON"));
        selectItemList.add(new LabelKeySelectItem(false, "OFF"));
        return selectItemList;
    }

    /**
     * Builds the supported locale.
     *
     * @return the list
     */
    private static List<Locale> buildSupportedLocale() {
        return Faces.getSupportedLocales();
    }

    /**
     * Get default locale.
     *
     * @return the default locale
     */
    public static Locale getDefaultLocale() {

        // // this code will get default locale from browser.
        // final HttpServletRequest request = (HttpServletRequest)
        // FacesContext.getCurrentInstance().getExternalContext().getRequest();
        // final Locale locale = request.getLocale();
        // if (SysConfig.buildSupportedLocale().contains(locale)) {
        // return locale;
        // }

        // set default locale to JA
        return Faces.getDefaultLocale();
    }

    /**
     * Get hash id with key.
     *
     * @param key the key
     * @return the hash id
     */
    public static String getHashId(final String key) {
        String hashCode = String.valueOf(key.hashCode());
        if (hashCode.startsWith("-")) {
            hashCode = "-".hashCode() + hashCode.substring(1);
        }

        return "arrow_gen_id_" + hashCode;
    }

    /** The logger. */
    @Inject
    BaseLogger logger;

    /** The current rev. */
    private String currentRev = StringUtils.EMPTY;

    private String currentVersion = StringUtils.EMPTY;

    /**
     * Execute action.
     *
     * @param elExpression the el expression
     * @return the object
     */
    public Object executeAction(final String elExpression) {
        return FacesELUtils.invokeMethodExpression(elExpression);
    }

    /**
     * Gets the current revision.
     *
     * @return the current revision
     */
    public String getCurrentRevision() {
        if (arrow.framework.util.StringUtils.isEmpty(this.currentRev)) {
            // retrieve revision number if available. shouldn't do it in static initializer coz the
            // context may not be
            // available
            try {
                final BufferedReader reader =
                    new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/rev.txt"),
                        Charset.forName("UTF-8")));
                String rev = "";
                rev = reader.readLine();
                reader.close();

                this.currentRev = rev;
            } catch (final IOException | NullPointerException e) {
                this.logger.debug("Error on trying to read current version:", e);
            }
        }
        return this.currentRev;
    }

    /**
     * Gets current version.
     *
     * @return the current version
     */
    public String getCurrentVersion() {
        if (arrow.framework.util.StringUtils.isEmpty(this.currentVersion)) {
            // retrieve revision number if available. shouldn't do it in static initializer coz the
            // context may not be
            // available
            try {
                final BufferedReader reader =
                    new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/version.txt"),
                        Charset.forName("UTF-8")));
                String rev = "";
                rev = reader.readLine();
                reader.close();
                this.currentVersion = rev;
            } catch (final IOException | NullPointerException e) {
                this.logger.debug("Error on trying to read current version:", e);
            }
        }
        return this.currentVersion;
    }

    /**
     * Gets the default time zone.
     *
     * @return the default time zone
     */
    public TimeZone getDefaultTimeZone() {
        return TimeZone.getDefault();
    }

    /**
     * Gets the hour select items.
     *
     * @return the hour select items
     */
    public List<SelectItem> getHourSelectItems() {
        return SysConfig.HOURS_SELECT_ITEMS;
    }

    /**
     * Gets the minute select items.
     *
     * @return the minute select items
     */
    public List<SelectItem> getMinuteSelectItems() {
        return SysConfig.MINUTES_SELECT_ITEMS;
    }

    /**
     * Gets the on off select items.
     *
     * @return the on off select items
     */
    public List<SelectItem> getOnOffSelectItems() {
        return SysConfig.ON_OFF_SELECT_ITEMS;
    }

    /**
     * Gets the supported locales.
     *
     * @return the supported locales
     */
    public List<Locale> getSupportedLocales() {
        return SysConfig.buildSupportedLocale();
    }

    /**
     * Gets the month select items.
     *
     * @return the month select items
     */
    public List<SelectItem> getMonthSelectItems() {
        return SysConfig.MONTHS_SELECT_ITEMS;
    }

    /**
     * Gets the year select items.
     *
     * @return the year select items
     */
    public List<SelectItem> getYearSelectItems() {
        return SysConfig.YEARS_SELECT_ITEMS;
    }

    /**
     * Gets the employee status.
     *
     * @return the employee status
     */
    public List<SelectItem> getEmployeeStatus() {
        return SelectItemGenerator.getListSelectItem(ListType.EMPLOYEE_STATUS);
    }


    /** The conversation. */
    @Inject
    private Conversation conversation;

    /**
     * Conversation initialized.
     *
     * @param payload the payload
     */
    public void conversationInitialized(@Observes @Initialized(ConversationScoped.class) final ServletRequest payload) {
        this.conversation.setTimeout(3600000L); // 60 minutes
    }

    /**
     * Conversation destroyed.
     *
     * @param payload the payload
     */
    public void conversationDestroyed(@Observes @Destroyed(ConversationScoped.class) final ServletRequest payload) {}

    /**
     * Gets the start hour.
     *
     * @return the start hour
     */
    public static int getStartHour() {
        return 7;
    }

    public String getDocumentFileTypesAllowed() {
        return "/(\\.|\\/)(" + SysConfig.joinExtentions(SysConfig.DOCS_FILE_EXTENTIONS) + ")$/i";
    }

    public static String joinExtentions(final String[] extendtions) {
        return Joiner.on(SysConfig.SEPARATOR).join(extendtions);
    }

    public int getDocFileSizeLimit() {
        return SysConfig.DOCUMENT_FILE_SIZE_ALLOWED;
    }

    public static String getUploadTempDir() {
        return Faces.getInitParameter(SysConfig.UPLOAD_TEMP_DIR_PARAM_NAME);
    }

    public static String getUploadAccountingDir() {
        return Faces.getInitParameter(SysConfig.UPLOAD_ACCOUNTING_DIR);
    }

    public int getDocFileLimit() {
        return SysConfig.DOCUMENT_FILE_LIMIT;
    }
}
