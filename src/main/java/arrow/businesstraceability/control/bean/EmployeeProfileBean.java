package arrow.businesstraceability.control.bean;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.EmployeeProfileService;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.User_login;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.InfoMessage;


/**
 * The Class EmployeeProfileBean.
 */
@Named
@ViewScoped
public class EmployeeProfileBean extends AbstractBean {

    /** The employee profile service. */

    @Inject
    private EmployeeProfileService employeeProfileService;

    /** The user credential. */
    @Inject
    private UserCredential userCredential;

    /** The current faces context. */
    @Inject
    private FacesContext currentFacesContext;

    /** The current username. */
    private String currentUsername;

    /** The employee bean. */
    private Employee_mst employeeBean;

    /** The old pass. */
    // For Change password
    private String oldPass;

    /** The new pass. */
    private String newPass;

    /** The confirm pass. */
    private String confirmPass;

    /** The emp_email. */
    // For edit profile
    private String empEmail;

    /** The emp_mobile. */
    private String empMobile;

    /** The emp_tel. */
    private String empTel;

    /** The auth bean. */
    @Inject
    private AuthenticationBean authBean;

    /**
     * Gets the confirm pass.
     *
     * @return the confirm pass
     */
    public String getConfirmPass() {
        return this.confirmPass;
    }

    /**
     * Sets the confirm pass.
     *
     * @param confirmPass the new confirm pass
     */
    public void setConfirmPass(final String confirmPass) {
        this.confirmPass = confirmPass;
    }

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        this.employeeBean = this.userCredential.getUserEmployee();
        this.empEmail = this.employeeBean.getEmp_mail();
        this.empMobile = this.employeeBean.getEmp_mobile();
        this.empTel = this.employeeBean.getEmp_tel();
    }

    /**
     * Save edit eployee.
     *
     * @see Save when edit profile
     */
    public void saveEditEployee() {
        final ServiceResult<Employee_mst> results = this.employeeProfileService.updateProfile(this);
        if (results.isSuccessful()) {
            this.userCredential.setUserEmployee(results.getData());
            InfoMessage.emp_001_update_profile_successfully().show();

        } else {
            results.showMessages(this.currentFacesContext);
        }
    }

    /**
     * Change password.
     *
     * @see Change password
     */
    public void changePassword() {
        final ServiceResult<Boolean> validate =
            this.employeeProfileService.validateChangePassword(this.oldPass, this.newPass, this.confirmPass,
                this.userCredential.getUserCode());
        if (!validate.isSuccessful()) {
            validate.showMessages(this.currentFacesContext);
            return;
        }

        final ServiceResult<User_login> results = this.employeeProfileService.updatePassword(this);
        if (results.isSuccessful()) {
            InfoMessage.sys_001_change_password_first_time_login().show();
            this.authBean.logout();
        } else {
            results.showMessages(this.currentFacesContext);
        }
    }

    /**
     * Gets the employee bean.
     *
     * @return the employee bean
     */
    public Employee_mst getEmployeeBean() {
        return this.employeeBean;
    }

    /**
     * Sets the employee bean.
     *
     * @param employeeBean the new employee bean
     */
    public void setEmployeeBean(final Employee_mst employeeBean) {
        this.employeeBean = employeeBean;
    }

    /**
     * Gets the old pass.
     *
     * @return the old pass
     */
    public String getOldPass() {
        return this.oldPass;
    }

    /**
     * Sets the old pass.
     *
     * @param oldPass the new old pass
     */
    public void setOldPass(final String oldPass) {
        this.oldPass = oldPass;
    }

    /**
     * Gets the new pass.
     *
     * @return the new pass
     */
    public String getNewPass() {
        return this.newPass;
    }

    /**
     * Sets the new pass.
     *
     * @param newPass the new new pass
     */
    public void setNewPass(final String newPass) {
        this.newPass = newPass;
    }

    /**
     * Gets the current username.
     *
     * @return the current username
     */
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    /**
     * Sets the current username.
     *
     * @param currentUsername the new current username
     */
    public void setCurrentUsername(final String currentUsername) {
        this.currentUsername = currentUsername;
    }

    /**
     * Gets the emp_email.
     *
     * @return the emp_email
     */
    public String getEmpEmail() {
        return this.empEmail;
    }

    /**
     * Sets the emp_email.
     *
     * @param empEmail the new emp_email
     */
    public void setEmpEmail(final String empEmail) {
        this.empEmail = empEmail;
    }

    /**
     * Gets the emp_mobile.
     *
     * @return the emp_mobile
     */
    public String getEmpMobile() {
        return this.empMobile;
    }

    /**
     * Sets the emp_mobile.
     *
     * @param empMobile the new emp_mobile
     */
    public void setEmpMobile(final String empMobile) {
        this.empMobile = empMobile;
    }

    /**
     * Gets the emp_tel.
     *
     * @return the emp_tel
     */
    public String getEmpTel() {
        return this.empTel;
    }

    /**
     * Sets the emp_tel.
     *
     * @param empTel the new emp_tel
     */
    public void setEmpTel(final String empTel) {
        this.empTel = empTel;
    }

}
