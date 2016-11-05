package arrow.businesstraceability.control.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

import arrow.businesstraceability.config.SysConfig;
import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.control.helper.AuthenticationData;
import arrow.businesstraceability.control.helper.ScreenContext;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Position_mst;
import arrow.businesstraceability.persistence.entity.User_login;
import arrow.businesstraceability.util.ArrowStringUtils;
import arrow.framework.logging.BaseLogger;

/**
 * The Class UserCredential.
 */
@Named
@SessionScoped
public class UserCredential implements Serializable {

    public static final short HQ_OFFICER_AUTHORITY = 1;

    public static final short MANAGER_POSITION = 101;

    public static final short STAFF_POSITION = 102;

    public static final short OFFICER_POSITION = 103;

    public static final short SYSTEM_POSITION = 104;

    public static final short BRANCH_STAFF_ROLE = 0;

    public static final short HQ_STAFF_ROLE = 1;

    public static final short BRANCH_OFFICER_ROLE = 2;

    public static final short HQ_OFFICER_ROLE = 3;

    public static final short BRANCH_MANAGER_ROLE = 4;

    public static final short HQ_MANAGER_ROLE = 5;

    public static final short BRANCH_SYSTEM_ROLE = 6;

    public static final short HQ_SYSTEM_ROLE = 7;

    public static final short UNACCESS = 0;

    public static final short READONLY = 1;

    public static final short SUBMISSION = 2;

    public static final short ADMIN = 3;

    private static Map<String, short[]> mapFormAndRequiredPermission = new HashMap<>();

    static {

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.DAILY_REPORT_REGISTER, new short[] {
            UserCredential.SUBMISSION, UserCredential.SUBMISSION, UserCredential.SUBMISSION, UserCredential.SUBMISSION,
            UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.SUBMISSION, UserCredential.SUBMISSION});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.DAILY_REPORT_HISTORY_SEARCH_FORM_CODE,
            new short[] {UserCredential.SUBMISSION, UserCredential.SUBMISSION, UserCredential.SUBMISSION,
                UserCredential.SUBMISSION, UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.SUBMISSION,
                UserCredential.SUBMISSION});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.DAILY_REPORT_SEARCH_EMPLOYEE_FORM_CODE,
            new short[] {UserCredential.SUBMISSION, UserCredential.SUBMISSION, UserCredential.SUBMISSION,
                UserCredential.SUBMISSION, UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.SUBMISSION,
                UserCredential.SUBMISSION});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.COMPANY_MANAGEMENT_FORM_CODE, new short[] {
            UserCredential.SUBMISSION, UserCredential.SUBMISSION, UserCredential.SUBMISSION, UserCredential.SUBMISSION,
            UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.SUBMISSION, UserCredential.SUBMISSION});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.EMPLOYEE_MANAGEMENT_FORM_CODE, new short[] {
            UserCredential.UNACCESS, UserCredential.ADMIN, UserCredential.UNACCESS, UserCredential.UNACCESS,
            UserCredential.UNACCESS, UserCredential.ADMIN, UserCredential.UNACCESS, UserCredential.ADMIN});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.ADDRESSPOINT_MANAGEMENT_CODE, new short[] {
            UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS,
            UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.ADMIN});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.SUMMARY_REPORT_FORM_CODE, new short[] {
            UserCredential.SUBMISSION, UserCredential.SUBMISSION, UserCredential.SUBMISSION, UserCredential.SUBMISSION,
            UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.SUBMISSION, UserCredential.SUBMISSION});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.NOTIFICATION_FORM_CODE, new short[] {
            UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS,
            UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.UNACCESS, UserCredential.UNACCESS});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.NOTIFICATION_DASHBOARD, new short[] {
            UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS,
            UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.UNACCESS, UserCredential.UNACCESS});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.SEARCH_NAME_CARD_DATA_PROC_1, new short[] {
            UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.ADMIN,
            UserCredential.UNACCESS, UserCredential.ADMIN, UserCredential.UNACCESS, UserCredential.ADMIN});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.MAINTENANCE_ACCOUNTING_DATA, new short[] {
            UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS,
            UserCredential.UNACCESS, UserCredential.ADMIN, UserCredential.UNACCESS, UserCredential.ADMIN});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.UPLOAD_CSV, new short[] {UserCredential.UNACCESS,
            UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS,
            UserCredential.ADMIN, UserCredential.UNACCESS, UserCredential.ADMIN});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.BASS_EXPORT, new short[] {
            UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS, UserCredential.UNACCESS,
            UserCredential.UNACCESS, UserCredential.ADMIN, UserCredential.UNACCESS, UserCredential.ADMIN});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.SANSAN_IMPORT, new short[] {UserCredential.ADMIN,
            UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.ADMIN,
            UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.ADMIN});

        UserCredential.mapFormAndRequiredPermission.put(ScreenContext.VERIFY_DATA_SANSAN_AFTER_IMPORT, new short[] {
            UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.ADMIN,
            UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.ADMIN, UserCredential.ADMIN});
    }

    /**
     * Map between Form Code and Permissions Permissions are: View, Submission, Admin.
     */
    private final Map<String, boolean[]> permissionMap = new HashMap<String, boolean[]>();

    @Inject
    BaseLogger logger;

    private AuthenticationData authData;

    private Locale currentLocale;

    private Position_mst userPosition;

    private Addresspoint_mst userAddress;

    private User_login userLogin;

    private Employee_mst userEmployee;

    @Inject
    private EntityManager emMain;

    private Preferences preferences;

    private boolean isRecoverPassword;

    /**
     * Basically, user has a permission against a screen if his permission level >= required permission level of the
     * screen.
     *
     * @param formCode the form code
     * @param permissionLevel the permission level
     * @return true, if is allow view
     */
    public static boolean isAllowView(final String formCode, final short permissionLevel) {
        return UserCredential.mapFormAndRequiredPermission.get(formCode)[permissionLevel] >= UserCredential.READONLY;
    }

    /**
     * Checks if is allow submission.
     *
     * @param formCode the form code
     * @param permissionLevel the permission level
     * @return true, if is allow submission
     */
    public static boolean isAllowSubmission(final String formCode, final short permissionLevel) {
        return UserCredential.mapFormAndRequiredPermission.get(formCode)[permissionLevel] >= UserCredential.SUBMISSION;
    }

    /**
     * Checks if is allow management.
     *
     * @param formCode the form code
     * @param permissionLevel the permission level
     * @return true, if is allow management
     */
    public static boolean isAllowManagement(final String formCode, final short permissionLevel) {
        return UserCredential.mapFormAndRequiredPermission.get(formCode)[permissionLevel] >= UserCredential.ADMIN;
    }

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {

        // just make sure that the preferences is never null.
        this.preferences = new Preferences();
        this.preferences.setTheme(SysConfig.DEFAULT_THEME);
    }

    /**
     * Change theme.
     */
    public void changeTheme() {}

    /**
     * Checks if is logged in.
     *
     * @return true, if is logged in
     */
    public boolean isLoggedIn() {
        HttpSession currentSession = Faces.getSession(false);
        if (currentSession != null) {
            return currentSession.getAttribute(AuthenticationConstants.USER_SESSION_KEY) != null;
        }
        return false;
    }

    /**
     * Update auth data.
     *
     * @param authenticationData the authentication data
     */
    public void updateAuthData(final AuthenticationData authenticationData) {
        this.authData = authenticationData;
        this.userPosition = this.authData != null ? this.authData.getEmployee().getPosition_mst() : null;
        this.userAddress = this.authData != null ? this.authData.getEmployee().getAddresspoint_mst() : null;
        this.userLogin = this.authData != null ? User_login.find(this.authData.getUsercode()) : null;
        this.userEmployee = this.authData != null ? this.authData.getEmployee() : null;
        if (this.authData != null) {
            this.updatePermissionMap(this.getPermissionLevel(this.userEmployee));
            // Instance.get(DaskboardBean.class).toString();

            // try to save locale information follow User.
            // this.setCurrentLocale(SysConfig.getDefaultLocale());
            if (this.userLogin.getUl_locale() != null) {
                if ("en".equalsIgnoreCase(this.userLogin.getUl_locale())) {
                    this.setCurrentLocale(Locale.ENGLISH);
                } else {
                    this.setCurrentLocale(Locale.JAPANESE);
                }
            } else {
                this.setCurrentLocale(SysConfig.getDefaultLocale());
            }
        } else {
            // logout
            this.permissionMap.clear();
        }
    }


    /**
     * Sets the selected locale.
     *
     * @param locale the new selected locale
     */
    @SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT",
        justification = "EntityManager.merge() will throw exceptions if failed, "
            + "so we don't need to check return value here")
    public void setSelectedLocale(final String locale) {
        User_login userSelectedLocale = User_login.find(this.authData.getUsercode());
        userSelectedLocale.setUl_locale(locale);
        try {
            this.emMain.merge(userSelectedLocale);
        } catch (PersistenceException pe) {
            this.logger.debug("Error on Update Locale:", pe);
        }
    }

    /**
     * Checks if is recover password.
     *
     * @return true, if is recover password
     */
    public boolean isRecoverPassword() {
        return this.isRecoverPassword;
    }

    /**
     * Sets the recover password.
     *
     * @param recoverPassword the new recover password
     */
    public void setRecoverPassword(final boolean recoverPassword) {
        this.isRecoverPassword = recoverPassword;
    }

    /**
     * Gets the employee name.
     *
     * @return the employee name
     */
    public String getEmployeeName() {
        return (this.authData != null) && (this.authData.getEmployee() != null) ? this.authData.getEmployee()
            .getEmp_name_DIRECT() : ArrowStringUtils.EMPTY_STRING;
    }

    /**
     * Gets the position name.
     *
     * @return the position name
     */
    public String getPositionName() {
        return this.userPosition != null ? this.userPosition.getPos_name_DIRECT()
            : org.apache.commons.lang3.StringUtils.EMPTY;
    }

    /**
     * Gets the user employee.
     *
     * @return the user employee
     */
    public Employee_mst getUserEmployee() {
        return this.userEmployee;
    }

    /**
     * Sets the user employee.
     *
     * @param userEmployee the new user employee
     */
    public void setUserEmployee(final Employee_mst userEmployee) {
        this.userEmployee = userEmployee;
    }

    /**
     * Gets the adp name.
     *
     * @return the adp name
     */
    public String getAdpName() {
        return this.userAddress != null ? this.userAddress.getAdp_name_DIRECT()
            : org.apache.commons.lang3.StringUtils.EMPTY;
    }

    /**
     * Gets the user code.
     *
     * @return the user code
     */
    public int getUserCode() {
        return this.authData != null ? this.authData.getUsercode() : 0;
    }

    /**
     * Gets the last login date.
     *
     * @return the last login date
     */
    public Date getLastLoginDate() {
        return this.userLogin != null ? this.userLogin.getUl_login_time_DIRECT() : null;
    }

    /**
     * Checks if is admin.
     *
     * @return true, if is admin
     */
    public boolean isAdmin() {
        return this.userEmployee != null ? this.userEmployee.getEmp_system_authority_DIRECT() : false;
    }

    /**
     * Checks if is allow delete.
     *
     * @return true, if is allow delete
     */
    public boolean isAllowDelete() {
        return this.isAdmin();
    }

    /**
     * Init the permissions for current user. <li>View: View and readonly <li>Submission: View and can create new/edit
     * data <li>Admin: View, create new/edit data, can delete data
     *
     * @param isAdmin
     */
    private void updatePermissionMap(final short permissionLevel) {
        this.permissionMap.put(ScreenContext.DAILY_REPORT_REGISTER,
            this.getPermissions(ScreenContext.DAILY_REPORT_REGISTER, permissionLevel));
        this.permissionMap.put(ScreenContext.DAILY_REPORT_HISTORY_SEARCH_FORM_CODE,
            this.getPermissions(ScreenContext.DAILY_REPORT_HISTORY_SEARCH_FORM_CODE, permissionLevel));
        this.permissionMap.put(ScreenContext.COMPANY_MANAGEMENT_FORM_CODE,
            this.getPermissions(ScreenContext.COMPANY_MANAGEMENT_FORM_CODE, permissionLevel));
        this.permissionMap.put(ScreenContext.EMPLOYEE_MANAGEMENT_FORM_CODE,
            this.getPermissions(ScreenContext.EMPLOYEE_MANAGEMENT_FORM_CODE, permissionLevel));
        this.permissionMap.put(ScreenContext.ADDRESSPOINT_MANAGEMENT_CODE,
            this.getPermissions(ScreenContext.ADDRESSPOINT_MANAGEMENT_CODE, permissionLevel));
        this.permissionMap.put(ScreenContext.SUMMARY_REPORT_FORM_CODE,
            this.getPermissions(ScreenContext.SUMMARY_REPORT_FORM_CODE, permissionLevel));
        this.permissionMap.put(ScreenContext.DAILY_REPORT_SEARCH_EMPLOYEE_FORM_CODE,
            this.getPermissions(ScreenContext.DAILY_REPORT_SEARCH_EMPLOYEE_FORM_CODE, permissionLevel));
        this.permissionMap.put(ScreenContext.NOTIFICATION_FORM_CODE,
            this.getPermissions(ScreenContext.NOTIFICATION_FORM_CODE, permissionLevel));
        this.permissionMap.put(ScreenContext.SEARCH_NAME_CARD_DATA_PROC_1,
            this.getPermissions(ScreenContext.SEARCH_NAME_CARD_DATA_PROC_1, permissionLevel));
        this.permissionMap.put(ScreenContext.MAINTENANCE_ACCOUNTING_DATA,
            this.getPermissions(ScreenContext.MAINTENANCE_ACCOUNTING_DATA, permissionLevel));
        this.permissionMap
            .put(ScreenContext.UPLOAD_CSV, this.getPermissions(ScreenContext.UPLOAD_CSV, permissionLevel));
        this.permissionMap.put(ScreenContext.BASS_EXPORT,
            this.getPermissions(ScreenContext.BASS_EXPORT, permissionLevel));
        this.permissionMap.put(ScreenContext.NOTIFICATION_DASHBOARD,
            this.getPermissions(ScreenContext.NOTIFICATION_DASHBOARD, permissionLevel));
        this.permissionMap.put(ScreenContext.SANSAN_IMPORT,
            this.getPermissions(ScreenContext.SANSAN_IMPORT, permissionLevel));
        this.permissionMap.put(ScreenContext.VERIFY_DATA_SANSAN_AFTER_IMPORT,
            this.getPermissions(ScreenContext.VERIFY_DATA_SANSAN_AFTER_IMPORT, permissionLevel));
    }

    /**
     * Gets the permissions.
     *
     * @param formCode the form code
     * @param permissionLevel the permission level
     * @return the permissions
     */
    public boolean[] getPermissions(final String formCode, final short permissionLevel) {
        return new boolean[] {UserCredential.isAllowView(formCode, permissionLevel),
            UserCredential.isAllowSubmission(formCode, permissionLevel),
            UserCredential.isAllowManagement(formCode, permissionLevel)};
    }

    /**
     * Checks for view permission.
     *
     * @param formCode the form code
     * @return true, if successful
     */
    public boolean hasViewPermission(final String formCode) {
        return this.permissionMap.get(formCode) != null ? this.permissionMap.get(formCode)[0] : false;
    }

    /**
     * Gets the permission level.
     *
     * @param employee the employee
     * @return the permission level
     */
    public short getPermissionLevel(final Employee_mst employee) {
        if (UserCredential.STAFF_POSITION == employee.getEmp_poscode()) {
            if (employee.getEmp_settle_authority() == UserCredential.HQ_OFFICER_AUTHORITY) {
                return UserCredential.HQ_STAFF_ROLE;
            } else {
                return UserCredential.BRANCH_STAFF_ROLE;
            }
        } else if (UserCredential.OFFICER_POSITION == employee.getEmp_poscode()) {
            if (employee.getEmp_settle_authority() == UserCredential.HQ_OFFICER_AUTHORITY) {
                return UserCredential.HQ_OFFICER_ROLE;
            } else {
                return UserCredential.BRANCH_OFFICER_ROLE;
            }

        } else if (UserCredential.MANAGER_POSITION == employee.getEmp_poscode()) {
            if (employee.getEmp_settle_authority() == UserCredential.HQ_OFFICER_AUTHORITY) {
                return UserCredential.HQ_MANAGER_ROLE;
            } else {
                return UserCredential.BRANCH_MANAGER_ROLE;
            }
        } else if (UserCredential.SYSTEM_POSITION == employee.getEmp_poscode()) {
            if (employee.getEmp_settle_authority() == UserCredential.HQ_OFFICER_AUTHORITY) {
                return UserCredential.HQ_SYSTEM_ROLE;
            } else {
                return UserCredential.BRANCH_SYSTEM_ROLE;
            }
        }
        return 0;
    }

    /**
     * Checks if is head quarter officer.
     *
     * @return true, if is head quarter officer
     */
    public boolean isHeadQuarterOfficer() {
        return ((this.userEmployee != null) && (this.userEmployee.getEmp_settle_authority_DIRECT() == UserCredential.HQ_OFFICER_AUTHORITY));
    }

    /**
     * Gets the current locale.
     *
     * @return the current locale
     */
    public Locale getCurrentLocale() {
        return this.currentLocale;
    }

    /**
     * Sets the current locale.
     *
     * @param currentLocale the new current locale
     */
    public void setCurrentLocale(final Locale currentLocale) {
        this.currentLocale = currentLocale;
        Faces.setLocale(currentLocale);
    }

    /**
     * Gets the preferences.
     *
     * @return the preferences
     */
    public Preferences getPreferences() {
        return this.preferences;
    }

    /**
     * Sets the preferences.
     *
     * @param preferences the new preferences
     */
    public void setPreferences(final Preferences preferences) {
        this.preferences = preferences;
    }

    public static class Preferences {
        private String theme;

        /**
         * Gets the theme.
         *
         * @return the theme
         */
        public String getTheme() {
            return this.theme;
        }

        /**
         * Sets the theme.
         *
         * @param pheme the new theme
         */
        public void setTheme(final String pheme) {
            this.theme = pheme;
        }

    }

    public boolean isManagerHQ() {
        return this.getUserEmployee().isManager() && this.isHeadQuarterOfficer();
    }

}
