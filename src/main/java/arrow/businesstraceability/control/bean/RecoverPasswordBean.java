package arrow.businesstraceability.control.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;

import freemarker.template.TemplateException;

import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.EmployeeProfileService;
import arrow.businesstraceability.control.service.RecoverPasswordService;
import arrow.businesstraceability.persistence.entity.User_login;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.util.cdi.qualifier.RequestParam;

/**
 * The Class RecoverPasswordBean.
 */
@Named
@ViewScoped
public class RecoverPasswordBean extends AbstractBean {

    /** The user credential. */
    @Inject
    private UserCredential userCredential;

    /** The current face context. */
    @Inject
    private FacesContext currentFaceContext;

    /** The recover pass service. */
    @Inject
    private RecoverPasswordService recoverPassService;

    /** The employee service. */
    @Inject
    private EmployeeProfileService employeeService;

    /** The path. */
    @Inject
    @RequestParam
    private String path;

    /** The session id. */
    @Inject
    @RequestParam
    private String sessionId;

    /** The email. */
    private String email;

    /** The employee code. */
    private String employeeCode;

    /** The new password. */
    private String newPassword;

    /** The re new password. */
    private String reNewPassword;

    /** The is redirect home. */
    private boolean isRedirectHome;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        this.userCredential.setRecoverPassword(false);
        this.isRedirectHome = false;
    }

    /**
     * Sets the is send mail.
     */
    public void setIsSendMail() {
        this.userCredential.setRecoverPassword(true);
    }

    /**
     * Cancel send mail.
     */
    public void cancelSendMail() {
        this.userCredential.setRecoverPassword(false);
    }

    /**
     * Send info employee.
     */
    public void sendInfoEmployee() {
        try {
            final ServiceResult<User_login> results =
                    this.recoverPassService.sendEmployeeInfo(this.email, this.employeeCode);
            if (results.isSuccessful()) {
                // Message success
                InfoMessage.resetpass_001_have_sent_success().show();
            }
            else {
                results.showMessages(this.currentFaceContext);
            }
        } catch (IOException | TemplateException | MessagingException me) {
            this.log.debug(me);
            ErrorMessage.resetpass_001_have_not_send().show();
        }

    }

    /**
     * Resetting password.
     */
    public void resettingPassword() {
        final ServiceResult<User_login> validate =
                this.recoverPassService.changePassword(this.newPassword, this.reNewPassword, this.sessionId);
        if (!validate.isSuccessful()) {
            validate.showMessages(this.currentFaceContext);
            return;
        }
        else {
            InfoMessage.resetpass_002_change_password_successfully().show();
            this.isRedirectHome = true;
        }
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the new password.
     *
     * @return the new password
     */
    public String getNewPassword() {
        return this.newPassword;
    }

    /**
     * Sets the new password.
     *
     * @param newPassword the new new password
     */
    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Gets the re new password.
     *
     * @return the re new password
     */
    public String getReNewPassword() {
        return this.reNewPassword;
    }

    /**
     * Sets the re new password.
     *
     * @param reNewPassword the new re new password
     */
    public void setReNewPassword(final String reNewPassword) {
        this.reNewPassword = reNewPassword;
    }

    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Sets the path.
     *
     * @param path the new path
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * Gets the session id.
     *
     * @return the session id
     */
    public String getSessionId() {
        return this.sessionId;
    }

    /**
     * Sets the session id.
     *
     * @param sessionId the new session id
     */
    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Gets the view path.
     *
     * @return the view path
     */
    public String getViewPath() {
        if (StringUtils.isNotEmpty(this.path)) {
            if ("recover_password".equalsIgnoreCase(this.path)) {
                return "WEB-INF/pages/recover_password/recover_password_061.xhtml";
            }
            return "WEB-INF/includes/login.xhtml";
        }
        else {
            return "WEB-INF/includes/login.xhtml";
        }
    }

    /**
     * Checks if is redirect home.
     *
     * @return true, if is redirect home
     */
    public boolean isRedirectHome() {
        return this.isRedirectHome;
    }

    /**
     * Sets the redirect home.
     *
     * @param redirectHome the new redirect home
     */
    public void setRedirectHome(final boolean redirectHome) {
        this.isRedirectHome = redirectHome;
    }

    /**
     * Gets the employee code.
     *
     * @return the employee code
     */
    public String getEmployeeCode() {
        return this.employeeCode;
    }

    /**
     * Sets the employee code.
     *
     * @param employeeCode the new employee code
     */
    public void setEmployeeCode(final String employeeCode) {
        this.employeeCode = employeeCode;
    }


    /**
     * Validate recover password confirm.
     *
     * @param context the context
     * @param comp the comp
     * @param value the value
     */
    public void validateRecoverPasswordConfirm(final FacesContext context, final UIComponent comp, final Object value) {
        String passwordConfirm = (String) value;
        if (StringUtils.isEmpty(this.newPassword)) {
            return;
        }
        ServiceResult<Boolean> result =
                this.employeeService.validatePasswordAndConfirmPassword(this.newPassword, passwordConfirm);
        if (result.isNotSuccessful()) {
            List<FacesMessage> errors = new ArrayList<FacesMessage>();
            for (Message msg : result.getMessages()) {
                errors.add(msg.createFaceMessage());
            }
            throw new ValidatorException(errors);
        }
    }

    /**
     * Validate new password.
     *
     * @param context the context
     * @param comp the comp
     * @param value the value
     */
    public void validateNewPassword(final FacesContext context, final UIComponent comp, final Object value) {
        String password = (String) value;
        if (StringUtils.isEmpty(password)) {
            return;
        }
        ServiceResult<Boolean> result = this.employeeService.validatePassword(password);
        if (result.isNotSuccessful()) {
            List<FacesMessage> errors = new ArrayList<FacesMessage>();
            for (Message msg : result.getMessages()) {
                errors.add(msg.createFaceMessage());
            }
            throw new ValidatorException(errors);
        }
    }
}
