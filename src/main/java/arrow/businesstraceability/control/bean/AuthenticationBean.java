package arrow.businesstraceability.control.bean;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.auth.event.Login;
import arrow.businesstraceability.auth.event.Logout;
import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.control.helper.AuthenticationData;
import arrow.businesstraceability.control.helper.ScreenContext;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.AuthenticationService;
import arrow.framework.bean.AbstractBean;
import arrow.framework.exception.ArrException;
import arrow.framework.util.StringUtils;
import arrow.framework.util.cdi.CDIUtils;


/**
 * The Class AuthenticationBean.
 */
@Named
@ViewScoped
public class AuthenticationBean extends AbstractBean {

    /** The auth service. */
    @Inject
    private AuthenticationService authService;

    /** The user credential. */
    @Inject
    private UserCredential userCredential;

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /** The current faces context. */
    @Inject
    private FacesContext currentFacesContext;

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(final String username) {
        this.username = StringUtils.trim(username);
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(final String password) {
        this.password = StringUtils.trim(password);
    }

    /**
     * Logout.
     */
    public void logout() {
        CDIUtils.getBeanManager().fireEvent(this.userCredential, Logout.LITERAL);
        this.userCredential.updateAuthData(null);
    }

    /**
     * Login action.
     *
     * @throws ArrException the arr exception
     */
    public void login() throws ArrException {

        // clear all screen used on latest login
        this.screenBean.switchToHome();

        // Just validate the credential

        final ServiceResult<AuthenticationData> result = this.authService.login(this.username, this.password);
        if (!result.isSuccessful()) {
            result.showMessages(this.currentFacesContext);
        } else {
            this.userCredential.updateAuthData(result.getData());
            CDIUtils.getBeanManager().fireEvent(this.userCredential, Login.LITERAL);

            if (result.getData().isFirstLogin()) {
                this.screenBean.switchScreen(ScreenContext.EMPLOYEE_CHANGE_PASSWORD);
            }
        }
    }

    /**
     * Gets the session time out.
     *
     * @return the session time out
     */
    public int getSessionTimeOut() {
        return AuthenticationConstants.SESSION_TIMEOUT;
    }

    /**
     * Keep alive.
     */
    public void keepAlive() {
        //
    }

}
