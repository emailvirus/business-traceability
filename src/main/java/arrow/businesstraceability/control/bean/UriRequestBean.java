package arrow.businesstraceability.control.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.cdi.qualifier.RequestParam;


/**
 * The Class UriRequestBean.
 */
@Named
@ViewScoped
public class UriRequestBean extends AbstractBean {

    /** The path search report. */
    @Inject
    @RequestParam
    private String pathSearchReport;

    /** The user id. */
    @Inject
    @RequestParam
    private String userId;

    /** The date search. */
    @Inject
    @RequestParam
    private String dateSearch;


    /** The user credential. */
    @Inject
    private UserCredential userCredential;

    /**
     * Gets the view path.
     *
     * @return the view path
     */
    public String getViewPath() {
        if (StringUtils.isNotEmpty(this.pathSearchReport)) {
            if ("view_history_report".equalsIgnoreCase(this.pathSearchReport) && (this.userCredential.isLoggedIn())) {
                return "WEB-INF/pages/search_employee_daily_report/search_employee_daily_report_012.xhtml";
            }
            return "WEB-INF/pages/home.xhtml";
        }
        else {
            return "WEB-INF/pages/home.xhtml";
        }
    }

    /**
     * Gets the path search report.
     *
     * @return the path search report
     */
    public String getPathSearchReport() {
        return this.pathSearchReport;
    }

    /**
     * Sets the path search report.
     *
     * @param pathSearchReport the new path search report
     */
    public void setPathSearchReport(final String pathSearchReport) {
        this.pathSearchReport = pathSearchReport;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Gets the date search.
     *
     * @return the date search
     */
    public String getDateSearch() {
        return this.dateSearch;
    }

    /**
     * Sets the date search.
     *
     * @param dateSearch the new date search
     */
    public void setDateSearch(final String dateSearch) {
        this.dateSearch = dateSearch;
    }

}
