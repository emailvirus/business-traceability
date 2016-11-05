package arrow.businesstraceability.bean;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.util.cdi.qualifier.RequestParam;


/**
 * The Class ViewControlManagedBean.
 */
@Named
@RequestScoped
public class ViewControlManagedBean {
    
    /** The path. */
    @Inject
    @RequestParam
    private String path;

    /** The session id. */
    @Inject
    @RequestParam
    private String sessionId;

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
     * Get view path.
     *
     * @return the view path
     */
    public String getViewPath() {
        if (StringUtils.isNotEmpty(this.path)) {
            return "WEB-INF/pages/recover_password.xhtml";
        }
        else {
            return "WEB-INF/includes/login.xhtml";
        }
    }

}
