/*
 *
 */
package arrow.businesstraceability.control.helper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Vetoed;

import arrow.framework.util.StringUtils;
import arrow.framework.util.i18n.Messages;


/**
 * About @Vetoed:.
 *
 * @author ducva
 */
@Vetoed
public class ScreenContext implements Serializable {

    /** The Constant OWNER_NAME. */
    public static final String OWNER_NAME = "OWNER";

    /** The Constant COMPANY_MANAGEMENT_FORM_CODE. */
    public static final String COMPANY_MANAGEMENT_FORM_CODE = "020";

    /** The Constant DAILY_REPORT_REGISTER. */
    public static final String DAILY_REPORT_REGISTER = "010";

    /** The Constant DAILY_REPORT_HISTORY_SEARCH_FORM_CODE. */
    public static final String DAILY_REPORT_HISTORY_SEARCH_FORM_CODE = "011";

    /** The Constant DAILY_REPORT_SEARCH_EMPLOYEE_FORM_CODE. */
    public static final String DAILY_REPORT_SEARCH_EMPLOYEE_FORM_CODE = "012";

    /** The Constant EMPLOYEE_MANAGEMENT_FORM_CODE. */
    public static final String EMPLOYEE_MANAGEMENT_FORM_CODE = "030";

    /** The Constant EMPLOYEE_EDIT_PROFILE. */
    public static final String EMPLOYEE_EDIT_PROFILE = "031";

    /** The Constant EMPLOYEE_CHANGE_PASSWORD. */
    public static final String EMPLOYEE_CHANGE_PASSWORD = "032";

    /** The Constant SUMMARY_REPORT_FORM_CODE. */
    public static final String SUMMARY_REPORT_FORM_CODE = "040";

    /** The Constant NOTIFICATION_FORM_CODE. */
    public static final String NOTIFICATION_FORM_CODE = "050";

    /** The Constant NOTIFICATION_DASHBOARD. */
    public static final String NOTIFICATION_DASHBOARD = "051";

    /** The Constant RECOVER_PASSWORD_SENDMAIL_FORM. */
    public static final String RECOVER_PASSWORD_SENDMAIL_FORM = "060";

    /** The Constant RECOVER_PASSWORD_FORM. */
    public static final String RECOVER_PASSWORD_FORM = "061";

    /** The Constant ADDRESSPOINT_MANAGEMENT. */
    public static final String ADDRESSPOINT_MANAGEMENT_CODE = "070";

    /** The Constant UPLOAD_CSV. */
    public static final String UPLOAD_CSV = "080";

    /** The Constant SEARCH_NAME_CARD_DATA_PROC_1. */
    public static final String SEARCH_NAME_CARD_DATA_PROC_1 = "PROC1";

    /** The Constant INPUT_ACCOUNTING_DATA. */
    public static final String INPUT_ACCOUNTING_DATA = "PROC3";

    /** The Constant INPUT_FINANCIAL_DATA. */
    public static final String INPUT_FINANCIAL_DATA = "PROC4";

    /** The Constant INPUT_CUSTOMER_DATA. */
    public static final String INPUT_CUSTOMER_DATA = "PROC5";

    /** The Constant WEB_INF_PAGES. */
    private static final String WEB_INF_PAGES = "/WEB-INF/pages/";

    /** The Constant DEFAULT_SUFFIX. */
    private static final String DEFAULT_SUFFIX = ".xhtml";

    /** The Constant BASS_EXPORT. */
    public static final String BASS_EXPORT = "090";

    /** The Constant SANSAN_IMPORT. */
    public static final String SANSAN_IMPORT = "100";

    /** The Constant VERIFY_DATA_SANSAN_AFTER_IMPORT. */
    public static final String VERIFY_DATA_SANSAN_AFTER_IMPORT = "110";

    /** The Constant MAINTENANCE_ACCOUNTING_DATA. */
    public static final String MAINTENANCE_ACCOUNTING_DATA = "001";

    /** The Constant MAP_FORM_CODE_AND_PATH. */
    static final Map<String, String> MAP_FORM_CODE_AND_PATH = new HashMap<String, String>();


    /** The current form code. */
    private String currentFormCode;

    /** The current form path. */
    private String currentFormPath;

    /** The current form name. */
    private String currentFormName;


    /**
     * Init data for Form Code and Path Mapping.
     */
    static {

        // TOP screen
        ScreenContext.MAP_FORM_CODE_AND_PATH.put(StringUtils.EMPTY_STRING, StringUtils.EMPTY_STRING);

        // Daily Register
        // /WEB-INF/pages/daily_report/daily_report_010.xhtml
        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.DAILY_REPORT_REGISTER, "daily_report");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.DAILY_REPORT_HISTORY_SEARCH_FORM_CODE,
            "search_history_daily_report");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.DAILY_REPORT_SEARCH_EMPLOYEE_FORM_CODE,
            "search_employee_daily_report");

        // Company Management
        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.COMPANY_MANAGEMENT_FORM_CODE, "company_management");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.EMPLOYEE_MANAGEMENT_FORM_CODE, "employee_management");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.EMPLOYEE_EDIT_PROFILE, "employee_edit_profile");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.EMPLOYEE_CHANGE_PASSWORD, "employee_change_password");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.SUMMARY_REPORT_FORM_CODE, "summary_report");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.NOTIFICATION_FORM_CODE, "notification");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.NOTIFICATION_DASHBOARD, "notification");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.RECOVER_PASSWORD_SENDMAIL_FORM, "recover_password");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.RECOVER_PASSWORD_FORM, "recover_password");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.ADDRESSPOINT_MANAGEMENT_CODE, "addresspoint_management");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.UPLOAD_CSV, "upload_csv");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.SEARCH_NAME_CARD_DATA_PROC_1, "search_name_card_data");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.INPUT_ACCOUNTING_DATA, "input_data_process");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.INPUT_FINANCIAL_DATA, "input_financial_data");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.INPUT_CUSTOMER_DATA, "input_customer_data");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.BASS_EXPORT, "bass_export");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.SANSAN_IMPORT, "sansan_import");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.VERIFY_DATA_SANSAN_AFTER_IMPORT, "result_after_import");

        ScreenContext.MAP_FORM_CODE_AND_PATH.put(ScreenContext.MAINTENANCE_ACCOUNTING_DATA,
            "maintenance_accounting_data");
    }

    /**
     * Instantiates a new screen context.
     *
     * @param screenCode the screen code
     */
    public ScreenContext(final String screenCode) {
        this.currentFormPath = StringUtils.EMPTY_STRING;
        // only OK if the screen code is already defined.
        if (ScreenContext.MAP_FORM_CODE_AND_PATH.containsKey(screenCode)) {
            this.currentFormCode = screenCode;
            this.currentFormPath = this.buildCurrentFormPath();
        }
    }

    /**
     * Gets the current form path.
     *
     * @return the current form path
     */
    public String getCurrentFormPath() {
        return this.currentFormPath;
    }

    /**
     * Sets the current form path.
     *
     * @param currentFormPath the new current form path
     */
    public void setCurrentFormPath(final String currentFormPath) {
        this.currentFormPath = currentFormPath;
    }

    /**
     * Get current form name.
     *
     * @return the current form name
     */
    public String getCurrentFormName() {
        this.currentFormName = Messages.get(ScreenContext.MAP_FORM_CODE_AND_PATH.get(this.currentFormCode));
        if (ScreenContext.DAILY_REPORT_REGISTER.equalsIgnoreCase(this.currentFormCode) && (this.attributes != null)) {
            String ownerName = this.attributes.get(ScreenContext.OWNER_NAME);
            if (StringUtils.isNotEmpty(ownerName)) {
                return this.currentFormName + " - " + ownerName;
            }
        }
        return this.currentFormName;
    }

    /**
     * Sets the current form name.
     *
     * @param currentFormName the new current form name
     */
    public void setCurrentFormName(final String currentFormName) {
        this.currentFormName = currentFormName;
    }

    /**
     * Builds the path.
     *
     * @param formName the form name
     * @return the string
     */
    private String buildPath(final String formName) {
        return (new StringBuilder()).append(ScreenContext.WEB_INF_PAGES).append(formName).append("/").append(formName)
            .append("_").append(this.currentFormCode).append(ScreenContext.DEFAULT_SUFFIX).toString();
    }

    /**
     * this method will return the relative path to the XHTML file of the form code.
     *
     * @return the current form path WEB-INF/pages/form_name/form_name_(form_code).xhtml
     */
    private String buildCurrentFormPath() {
        if (StringUtils.isEmpty(this.currentFormCode)) {
            return StringUtils.EMPTY_STRING;
        }
        if (ScreenContext.MAP_FORM_CODE_AND_PATH.containsKey(this.currentFormCode)) {
            return this.buildPath(ScreenContext.MAP_FORM_CODE_AND_PATH.get(this.currentFormCode));
        }

        return StringUtils.EMPTY_STRING;
    }

    /**
     * Gets the attributes.
     *
     * @return the attributes
     */
    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    /**
     * Sets the attributes.
     *
     * @param attributes the attributes
     */
    public void setAttributes(final Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Gets the object attributes.
     *
     * @return the object attributes
     */
    public Map<String, Object> getObjectAttributes() {
        return this.objectAttributes;
    }

    /**
     * Sets the object attributes.
     *
     * @param objectAttributes the object attributes
     */
    public void setObjectAttributes(final Map<String, Object> objectAttributes) {
        this.objectAttributes = objectAttributes;
    }

    /** The attributes. */
    private Map<String, String> attributes;

    /**
     * The Class SharingDataKey.
     */
    public static class SharingDataKey {

        /**
         * Instantiates a new sharing data key.
         */
        private SharingDataKey() {
            // This method needn't implement
        }

        /** The Constant DAILY_REPORT. */
        public static final String DAILY_REPORT = "dailyReportCode";

        /** The Constant SCREEN_CODE. */
        public static final String SCREEN_CODE = "screenCode";

        /** The Constant ID_COM_ENTITY. */
        public static final String ID_COM_ENTITY = "idComEntity";

        /** The Constant CREDIT_ENTITY. */
        public static final String CREDIT_ENTITY = "creditEntity";

        /** The Constant VIEW_MODE. */
        public static final String VIEW_MODE = "viewMode";

        /** The Constant ID_SAN_COMPANY. */
        public static final String ID_INT_SAN_COMPANY = "idIntSanCompany";

        /** The Constant ACC_SAN_COMPANY. */
        public static final String ACC_SAN_COMPANY = "accSanCompany";

        /** The Constant LIST_COMPANY_PROC1. */
        public static final String LIST_COMPANY_PROC2 = "listCompanyFromProc2";

        /** The Constant KEY_WORD_ID_NAME_CARD. */
        public static final String KEY_WORD_ID_NAME_CARD = "inputIdNameCard";

        /** The Constant KEY_WORD_COMPANY_NAME. */
        public static final String KEY_WORD_COMPANY_NAME = "inputCompanyName";

        /** The Constant KEY_WORD_CUSTOMER_CODE. */
        public static final String KEY_WORD_CUSTOMER_CODE = "inputCustomerCode";

        public static final String ACTIVE_INDEX_FIND_ACCOUNTING_DATA = "tabIndexFindAccountingData";
    }

    /** The object attributes. */
    private Map<String, Object> objectAttributes;
}
