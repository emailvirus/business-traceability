package arrow.businesstraceability.control.helper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Vetoed;

import arrow.businesstraceability.constant.MobilePageId;


/**
 * The Class MobileContext.
 */
@Vetoed
public class MobileContext implements Serializable {

    /** The Constant CURRENT_REPORT. */
    public static final String CURRENT_REPORT = "CURRENT_REPORT";

    /** The page id. */
    private String pageId;

    /** The attributes. */
    private Map<String, Object> attributes = new HashMap<String, Object>();

    /** The transition effect. */
    private final String transitionEffect = "slide";

    /**
     * Instantiates a new mobile context.
     *
     * @param pageId the page id
     */
    public MobileContext(final String pageId) {
        this.pageId = pageId;
    }

    /**
     * Gets the page id.
     *
     * @return the page id
     */
    public String getPageId() {
        return this.pageId;
    }

    /**
     * Sets the page id.
     *
     * @param pageId the new page id
     */
    public void setPageId(final String pageId) {
        this.pageId = pageId;
    }

    /**
     * Builds the outcome.
     *
     * @return the string
     */
    private String buildOutcome() {
        return "pm:" + this.pageId + "?transition=" + this.transitionEffect;
    }

    /**
     * Gets the outcome.
     *
     * @return the outcome
     */
    public String getOutcome() {
        return this.buildOutcome();
    }

    /**
     * Gets the outcome.
     *
     * @param isGoBack the is go back
     * @return the outcome
     */
    public String getOutcome(final boolean isGoBack) {
        return this.buildOutcome() + (isGoBack ? "&reverse=true" : "");
    }

    /**
     * Gets the attributes.
     *
     * @return the attributes
     */
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    /**
     * Sets the attributes.
     *
     * @param attributes the attributes
     */
    public void setAttributes(final Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPath() {
        switch (this.pageId) {
            case MobilePageId.DAILY_REPORTS:
                return "/WEB-INF/ipad/pages/daily_report.xhtml";
            case MobilePageId.EDIT_DAILY_REPORT:
                return "/WEB-INF/ipad/pages/edit_daily_report.xhtml";
            case MobilePageId.ADD_COMPANY:
                return "/WEB-INF/ipad/pages/add_company.xhtml";

            default:
                return "/WEB-INF/ipad/pages/dashboard.xhtml";
        }
    }
}
