package arrow.businesstraceability.control.bean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.DateUtils;


/**
 * The Class DaskboardBean.
 */
@Named
@ViewScoped
public class DaskboardBean extends AbstractBean {

    /** The user credential. */
    @Inject
    UserCredential userCredential;

    /** The model. */
    private DashboardModel model;

    /**
     * Gets the model.
     *
     * @return the model
     */
    public DashboardModel getModel() {
        return this.model;
    }

    /**
     * Sets the model.
     *
     * @param model the new model
     */
    public void setModel(final DashboardModel model) {
        this.model = model;
    }

    /**
     * Init data when open screen.
     */
    @PostConstruct
    public void init() {
        this.model = new DefaultDashboardModel();
        final DashboardColumn column1 = new DefaultDashboardColumn();
        // Only Show for Head Officer
        // if (this.userCredential.isHeadQuarterOfficer())
        // {
        column1.addWidget("notification");
        // }
        this.model.addColumn(column1);
    }

    /** The message. */
    // Test push
    private String message;

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Reload message.
     */
    // testing
    public void reloadMessage() {
        this.message = "New Message updated at " + DateUtils.getCurrentDatetime();
    }

}
