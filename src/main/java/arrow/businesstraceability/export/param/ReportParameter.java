package arrow.businesstraceability.export.param;

import java.util.Date;
import java.util.List;

import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Monthly_report_revision;


/**
 * The Class ReportParameter.
 */
public class ReportParameter {

    /** The start date. */
    private Date startDate;

    /** The end date. */
    private Date endDate;

    /** The companies. */
    private List<Company_mst> companies;

    /** The branch code. */
    private String branchCode;

    /** The employee code. */
    private String employeeCode;

    private Monthly_report_revision monthlyReportRevision;


    /**
     * Instantiates a new report parameter.
     */
    public ReportParameter() {};

    /**
     * constructor.
     *
     * @param startDate start date
     * @param endDate end date
     * @param companies list of company
     */
    public ReportParameter(final Date startDate, final Date endDate, final List<Company_mst> companies) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.companies = companies;

    }

    /**
     * constructor.
     *
     * @param startDate start date
     * @param endDate end date
     * @param branchCode code of branch
     * @param employeeCode code of employee
     */
    public ReportParameter(final Date startDate, final Date endDate, final String branchCode,
            final String employeeCode, final Monthly_report_revision monthlyReportRevision) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.branchCode = branchCode;
        this.employeeCode = employeeCode;
        this.monthlyReportRevision = monthlyReportRevision;
    }


    /**
     * Gets the branch code.
     *
     * @return the branch code
     */
    public String getBranchCode() {
        return this.branchCode;
    }

    /**
     * Sets the branch code.
     *
     * @param branchCode the new branch code
     */
    public void setBranchCode(final String branchCode) {
        this.branchCode = branchCode;
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

    public Monthly_report_revision getMonthlyReportRevision() {
        return this.monthlyReportRevision;
    }

    public void setMonthlyReportRevision(final Monthly_report_revision monthlyReportRevision) {
        this.monthlyReportRevision = monthlyReportRevision;
    }

    /**
     * Gets the start date.
     *
     * @return the start date
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the new start date
     */
    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the new end date
     */
    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the companies.
     *
     * @return the companies
     */
    public List<Company_mst> getCompanies() {
        return this.companies;
    }

    /**
     * Sets the companies.
     *
     * @param companies the new companies
     */
    public void setCompanies(final List<Company_mst> companies) {
        this.companies = companies;
    }


}
