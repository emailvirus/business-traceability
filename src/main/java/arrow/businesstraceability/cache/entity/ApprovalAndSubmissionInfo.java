package arrow.businesstraceability.cache.entity;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Info_of_Monthly_report_history.
 */
public class ApprovalAndSubmissionInfo {

    /** The number of submissions. */
    private int numberOfSubmissions;

    /** The number of reject. */
    private int numberOfReject;

    /** The number of approve. */
    private int numberOfApprove;

    /** The number of re open. */
    private int numberOfReOpen;


    /** The date submissions. */
    private Date dateSubmissions;


    /** The date reject. */
    private Date dateReject;


    /** The date approval. */
    private Date dateApproval;


    public Date getDateSubmissions() {
        return this.dateSubmissions;
    }

    public void setDateSubmissions(final Date dateSubmissions) {
        this.dateSubmissions = dateSubmissions;
    }

    public Date getDateReject() {
        return this.dateReject;
    }

    public void setDateReject(final Date dateReject) {
        this.dateReject = dateReject;
    }

    public Date getDateApproval() {
        return this.dateApproval;
    }

    public void setDateApproval(final Date dateApproval) {
        this.dateApproval = dateApproval;
    }

    public Date getDateReOpen() {
        return this.dateReOpen;
    }

    public void setDateReOpen(final Date dateReOpen) {
        this.dateReOpen = dateReOpen;
    }

    /** The date re open. */
    private Date dateReOpen;

    /** The Employee code. */
    private int employeeCode;


    /** The rejected by. */
    private String rejectedBy;

    /** The re opened by. */
    private String reOpenedBy;

    /** The approver. */
    private String approver;

    private String rejectComment;

    private String reopenComment;


    public String getReopenComment() {
        return this.reopenComment;
    }

    public String getRejectComment() {
        return this.rejectComment;
    }

    /**
     * Instantiates a new approval and submission info.
     */
    public ApprovalAndSubmissionInfo() {

    }

    /**
     * Gets the number of submissions.
     *
     * @return the number of submissions
     */
    public int getNumberOfSubmissions() {
        return this.numberOfSubmissions;
    }

    /**
     * Sets the number of submissions.
     *
     * @param numberOfSubmissions the new number of submissions
     */
    public void setNumberOfSubmissions(final int numberOfSubmissions) {
        this.numberOfSubmissions = numberOfSubmissions;
    }

    /**
     * Gets the number of reject.
     *
     * @return the number of reject
     */
    public int getNumberOfReject() {
        return this.numberOfReject;
    }

    /**
     * Sets the number of reject.
     *
     * @param numberOfReject the new number of reject
     */
    public void setNumberOfReject(final int numberOfReject) {
        this.numberOfReject = numberOfReject;
    }

    /**
     * Gets the number of approve.
     *
     * @return the number of approve
     */
    public int getNumberOfApprove() {
        return this.numberOfApprove;
    }

    /**
     * Sets the number of approve.
     *
     * @param numberOfApprove the new number of approve
     */
    public void setNumberOfApprove(final int numberOfApprove) {
        this.numberOfApprove = numberOfApprove;
    }

    /**
     * Gets the number of re open.
     *
     * @return the number of re open
     */
    public int getNumberOfReOpen() {
        return this.numberOfReOpen;
    }

    /**
     * Sets the number of re open.
     *
     * @param numberOfReOpen the new number of re open
     */
    public void setNumberOfReOpen(final int numberOfReOpen) {
        this.numberOfReOpen = numberOfReOpen;
    }

    /**
     * Gets the rejected by.
     *
     * @return the rejected by
     */
    public String getRejectedBy() {
        return this.rejectedBy;
    }

    /**
     * Sets the rejected by.
     *
     * @param rejectedBy the new rejected by
     */
    public void setRejectedBy(final String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    /**
     * Gets the re opened by.
     *
     * @return the re opened by
     */
    public String getReOpenedBy() {
        return this.reOpenedBy;
    }

    /**
     * Sets the re opened by.
     *
     * @param reOpenedBy the new re opened by
     */
    public void setReOpenedBy(final String reOpenedBy) {
        this.reOpenedBy = reOpenedBy;
    }

    /**
     * Gets the approver.
     *
     * @return the approver
     */
    public String getApprover() {
        return this.approver;
    }

    /**
     * Sets the approver.
     *
     * @param approver the new approver
     */
    public void setApprover(final String approver) {
        this.approver = approver;
    }


    /**
     * Gets the employee code.
     *
     * @return the employee code
     */
    public int getEmployeeCode() {
        return this.employeeCode;
    }

    /**
     * Sets the employee code.
     *
     * @param employeeCode the new employee code
     */
    public void setEmployeeCode(final int employeeCode) {
        this.employeeCode = employeeCode;
    }

    public void setRejectComment(final String comment) {
        this.rejectComment = comment;
    }

    public void setReopenComment(final String comment) {
        this.reopenComment = comment;

    }


}
