package arrow.businesstraceability.permission;

import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.persistence.entity.Branch_position;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Monthly_report_revision;

/**
 * The Class MonthlyReportPermission.
 */
public class MonthlyReportPermission {

    /**
     * Constructor for MonthlyReportPermission.
     *
     * @param currentUser The UserCredential.
     * @param targetEmployee Selected Employee_mst for monthly report.
     * @param monthlyReport The Monthly_report_revision.
     * @param branchPosition The Branch_position of selected Employee.
     * */
    public MonthlyReportPermission(final UserCredential currentUser, final Employee_mst targetEmployee,
            final Monthly_report_revision monthlyReport, final Branch_position branchPosition) {
        this.currentUser = currentUser;
        this.targetEmployee = targetEmployee;
        this.monthlyReport = monthlyReport;
        this.branchPosition = branchPosition;
    }

    /** The current user. */
    private UserCredential currentUser;

    /** The target employee. */
    private Employee_mst targetEmployee;

    /** The monthly report. */
    private Monthly_report_revision monthlyReport;

    /** The branch position. */
    private Branch_position branchPosition;

    /**
     * Checks for approve permission.
     *
     * @return true, if successful
     */
    public boolean hasApprovePermission() {
        return this.monthlyReport.isWaitingStatus() && this.isCurrentUserIsBranchLeaderOrViceLeader();
    }

    /**
     * Checks for re open permission.
     *
     * @return true, if successful
     */
    public boolean hasReOpenPermission() {
        return this.monthlyReport.isApprovedStatus()
                && (this.isCurrentUserIsBranchLeaderOrViceLeader() || this.isCurrentUserIsManagerHeadQuarter());
    }

    /**
     * Checks if is current user is manager head quarter.
     *
     * @return true, if is current user is manager head quarter
     */
    private boolean isCurrentUserIsManagerHeadQuarter() {
        return this.currentUser.getUserEmployee().isManager() && this.currentUser.isHeadQuarterOfficer();
    }

    /**
     * Check user have permission for submit or not.
     *
     * @return true, if successful
     */
    public boolean hasSubmitPermission() {
        return this.isTargetEmployeeSameCurrentUser() && this.monthlyReport.isOpenStatus();
    }

    /**
     * Check user have permission for submit or not.
     *
     * @return true, if successful
     */
    public boolean hasViewPermission() {
        if (this.monthlyReport.isOpenStatus()) {
            return this.isTargetEmployeeSameCurrentUser();
        }

        if (this.monthlyReport.isWaitingStatus()) {
            if (this.isTargetEmployeeSameCurrentUser()) {
                return true;
            }
            return this.isCurrentUserIsBranchLeaderOrViceLeader();
        }

        return this.monthlyReport.isApprovedStatus();
    }

    /**
     * Checks if is target employee same current user.
     *
     * @return true, if is target employee same current user
     */
    private boolean isTargetEmployeeSameCurrentUser() {
        return this.currentUser.getUserEmployee().getEmp_code() == this.targetEmployee.getEmp_code();
    }

    /**
     * Checks if is current user is branch leader or vice leader.
     *
     * @return true, if is current user is branch leader or vice leader
     */
    private boolean isCurrentUserIsBranchLeaderOrViceLeader() {
        return (this.branchPosition != null)
                && (this.branchPosition.isBranchLeader() || this.branchPosition.isBranchViceLeader());
    }

    /**
     * Gets the target employee.
     *
     * @return the target employee
     */
    public Employee_mst getTargetEmployee() {
        return this.targetEmployee;
    }

    /**
     * Sets the target employee.
     *
     * @param targetEmployee the new target employee
     */
    public void setTargetEmployee(final Employee_mst targetEmployee) {
        this.targetEmployee = targetEmployee;
    }

    /**
     * Gets the monthly report.
     *
     * @return the monthly report
     */
    public Monthly_report_revision getMonthlyReport() {
        return this.monthlyReport;
    }

    /**
     * Sets the monthly report.
     *
     * @param monthlyReport the new monthly report
     */
    public void setMonthlyReport(final Monthly_report_revision monthlyReport) {
        this.monthlyReport = monthlyReport;
    }

    /**
     * Gets the current user.
     *
     * @return the current user
     */
    public UserCredential getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Sets the current user.
     *
     * @param currentUser the new current user
     */
    public void setCurrentUser(final UserCredential currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Gets the branch position.
     *
     * @return the branch position
     */
    public Branch_position getBranchPosition() {
        return this.branchPosition;
    }

    /**
     * Sets the branch position.
     *
     * @param branchPosition the new branch position
     */
    public void setBranchPosition(final Branch_position branchPosition) {
        this.branchPosition = branchPosition;
    }
}
