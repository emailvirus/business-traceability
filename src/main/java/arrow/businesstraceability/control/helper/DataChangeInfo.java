package arrow.businesstraceability.control.helper;

import java.io.Serializable;
import java.util.Date;

import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Customer_info_view;

// TODO: Auto-generated Javadoc
/**
 * The Class DataChangeInfo.
 */
public class DataChangeInfo implements Serializable {

    /** The type. */
    private int type;

    /**
     * The Class DataType.
     */
    public static class DataType {

        /** The Constant COMPANY_MST. */
        public static final int COMPANY_MST = 0;

        /** The Constant Customer_info_view. */
        public static final int CUSTOM_INFO_VIEW = 1;
    }

    /**
     * The Class Action.
     */
    public static class Action {

        /** The Constant UPSERT. */
        public static final int UPSERT = 0;

        /** The Constant DELETE. */
        public static final int DELETE = 1;
    }

    /** The time stamp. */
    private Date timeStamp;

    /** The company mst. */
    private Company_mst companyMst;

    /** The daily report. */
    private Customer_info_view customerInfo;

    /** The action. */
    private int action;

    /**
     * Instantiates a new data change info.
     *
     * @param company the company
     * @param action the action
     */
    public DataChangeInfo(final Company_mst company, final int action) {
        this.companyMst = company;
        this.action = action;
        this.type = DataType.COMPANY_MST;
        this.timeStamp = company.getLast_updated_at();
    }


    /**
     * Instantiates a new data change info.
     *
     * @param customer_info_view the customer_info_view
     * @param timestamp
     * @param action the action
     */
    public DataChangeInfo(final Customer_info_view customer_info_view, final Date timestamp, final int action) {
        this.customerInfo = customer_info_view;
        this.action = action;
        this.type = DataType.CUSTOM_INFO_VIEW;
        this.timeStamp = timestamp;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public int getType() {
        return this.type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(final int type) {
        this.type = type;
    }

    /**
     * Gets the company mst.
     *
     * @return the company mst
     */
    public Company_mst getCompanyMst() {
        return this.companyMst;
    }

    /**
     * Sets the company mst.
     *
     * @param companyMst the new company mst
     */
    public void setCompanyMst(final Company_mst companyMst) {
        this.companyMst = companyMst;
    }

    /**
     * Gets the daily report.
     *
     * @return the daily report
     */
    public Customer_info_view getCustomerInfo() {
        return this.customerInfo;
    }

    /**
     * Sets the daily report.
     *
     * @param customerInfo the new daily report
     */
    public void setcustomerInfo(final Customer_info_view customerInfo) {
        this.customerInfo = customerInfo;
    }

    /**
     * Gets the action.
     *
     * @return the action
     */
    public int getAction() {
        return this.action;
    }

    /**
     * Sets the action.
     *
     * @param action the new action
     */
    public void setAction(final int action) {
        this.action = action;
    }

    /**
     * Gets the time stamp.
     *
     * @return the time stamp
     */
    public Date getTimeStamp() {
        return this.timeStamp;
    }

    /**
     * Sets the time stamp.
     *
     * @param timeStamp the new time stamp
     */
    public void setTimeStamp(final Date timeStamp) {
        this.timeStamp = timeStamp;
    }

}
