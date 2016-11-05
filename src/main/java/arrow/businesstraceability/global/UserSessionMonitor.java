package arrow.businesstraceability.global;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.event.PhaseEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;

import arrow.businesstraceability.auth.event.Login;
import arrow.businesstraceability.auth.event.Logout;
import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.framework.faces.event.qualifier.Before;
import arrow.framework.faces.event.qualifier.RestoreView;
import arrow.framework.logging.BaseLogger;


/**
 * The Class UserSessionMonitor.
 */
@ApplicationScoped
public class UserSessionMonitor implements Serializable {

    /** The Constant KICKEDOUT. */
    public static final String KICKEDOUT = "KICKEDOUT";

    /** The logger. */
    @Inject
    BaseLogger logger;

    /** The user info map. */
    // User_code => User Info
    private Map<Integer, HttpSession> userInfoMap = new TreeMap<>();

    /**
     * Reset.
     */
    public void reset() {
        this.userInfoMap = new TreeMap<>();
    }

    /**
     * Contains user.
     *
     * @param userCode the user code
     * @return true, if successful
     */
    public boolean containsUser(final int userCode) {
        return this.userInfoMap.containsKey(userCode);
    }

    /**
     * Debug Utils: to kick user from debug/test1.jsf
     *
     * @param userCode the user code
     */
    public void kickUser(final int userCode) {
        final HttpSession prevSession = this.userInfoMap.remove(userCode);
        if (prevSession != null) {
            prevSession.removeAttribute(AuthenticationConstants.USER_SESSION_KEY);
        }
    }

    /**
     * Observer logout event.
     *
     * @param loggingOutUser the logging out user
     */
    public void observerLogoutEvent(@Observes @Logout final UserCredential loggingOutUser) {
        if ((loggingOutUser != null) && this.containsUser(loggingOutUser.getUserCode())) {
            Faces.getSession(false).removeAttribute(AuthenticationConstants.USER_SESSION_KEY);
            this.userInfoMap.remove(loggingOutUser.getUserCode());
        }
    }

    /**
     * Observer login event.
     *
     * @param loggedInUser the logged in user
     */
    public void observerLoginEvent(@Observes @Login final UserCredential loggedInUser) {
        HttpSession currentSession = Faces.getSession(false);
        if (this.containsUser(loggedInUser.getUserCode())) {
            HttpSession prevSession = this.userInfoMap.remove(loggedInUser.getUserCode());
            if (!prevSession.equals(currentSession)) {
                try {
                    prevSession.removeAttribute(AuthenticationConstants.USER_SESSION_KEY);
                    prevSession.setAttribute(UserSessionMonitor.KICKEDOUT, true);
                } catch (IllegalStateException ise) {
                    // ignore
                    this.logger.debug("Not found session:", ise);
                }
            }
        }

        currentSession = Faces.getSession(false);
        this.userInfoMap.put(loggedInUser.getUserCode(), currentSession);
        currentSession.setAttribute(AuthenticationConstants.USER_SESSION_KEY, loggedInUser);
        currentSession.removeAttribute(UserSessionMonitor.KICKEDOUT);
    }

    /**
     * Observer restore view.
     *
     * @param event the event
     */
    public void observerRestoreView(@Observes @Before @RestoreView final PhaseEvent event) {
        HttpSession currentSesion = (HttpSession) event.getFacesContext().getExternalContext().getSession(false);
        HttpServletRequest request = (HttpServletRequest) event.getFacesContext().getExternalContext().getRequest();

        // Process if on ipad devices
        if (request.getRequestURI().contains("ipad.xhtml")) {
            if (currentSesion.getAttribute(UserSessionMonitor.KICKEDOUT) != null) {

                // remove the attr, then when refresh will not come here.
                currentSesion.removeAttribute(UserSessionMonitor.KICKEDOUT);
                event.getFacesContext().setViewRoot(event.getFacesContext().getApplication().getViewHandler()
                    .createView(event.getFacesContext(), "errorpages/kicked_out.html"));
                event.getFacesContext().getPartialViewContext().setRenderAll(true);
                event.getFacesContext().renderResponse();
            }
        }
    }
}
