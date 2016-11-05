package arrow.businesstraceability.control.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import arrow.businesstraceability.control.exception.TransactionRollbackException;
import arrow.businesstraceability.control.helper.ScreenContext;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.DailyReportService;
import arrow.businesstraceability.control.service.SummaryReportService;
import arrow.businesstraceability.persistence.dto.Daily_report_DTO;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Branch_position;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Monthly_report_revision;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.util.DateUtils;
import arrow.framework.util.cdi.qualifier.RequestParam;
import arrow.framework.util.collections.CollectionUtils;


/**
 * The Class RegisterActivitySearchEmployeeBean.
 */
@Named
@ViewScoped
public class RegisterActivitySearchEmployeeBean extends AbstractBean {

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

    /** The daily report service. */
    @Inject
    private DailyReportService dailyReportService;

    @Inject
    private SummaryReportService summaryReportService;

    /** The screen bean. */
    @Inject
    private ScreenBean screenBean;

    /** The all report. */
    private List<Daily_report> allReport;

    /** The selected report. */
    private Daily_report_DTO selectedReport;

    /** The selected employee. */
    private Employee_mst selectedEmployee;

    /** The all address point. */
    private List<Addresspoint_mst> allAddressPoint;

    /** The selected date. */
    private Date selectedDate;

    /** The list branch. */
    private List<Addresspoint_mst> listBranch;

    /** The select branch. */
    private String selectBranch;

    /** The has report selected. */
    // check select report and render list employee
    private boolean hasReportSelected;

    /** The user credential. */
    @Inject
    private UserCredential userCredential;

    /**
     * To toggle all business detail of all rows.
     */
    private boolean showAllBusinessDetail;

    /** The all active employee. */
    private List<Employee_mst> allActiveEmployee;

    /** The list. */
    private List<Boolean> list;

    /** The is include retired employee. */
    private boolean isIncludeRetiredEmployee;

    /**
     * Toggle show all business detail.
     */
    public void toggleShowAllBusinessDetail() {
        this.showAllBusinessDetail = !this.showAllBusinessDetail;
    }

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        if ("view_history_report".equalsIgnoreCase(this.pathSearchReport) && (this.userCredential.isLoggedIn())) {
            Date selectDate = DateUtils.getDateTimeFromString(this.dateSearch);
            this.searchHistoryByEmployeeAndDate(Integer.parseInt(this.userId), selectDate);
        } else {
            this.selectedDate = DateUtils.getCurrentDatetime();
        }
    }

    /**
     * Search employee report.
     */
    public void searchEmployeeReport() {
        this.allReport = this.dailyReportService.searchEmployeeReport(this.selectedDate, this.selectedEmployee);
    }

    /**
     * Gets the all address point.
     *
     * @return the all address point
     */
    public List<Addresspoint_mst> getAllAddressPoint() {
        if (this.allAddressPoint == null) {
            this.allAddressPoint = this.dailyReportService.getListAddrContraintEmployee();
        }
        return this.allAddressPoint;
    }

    /**
     * Sets the all address point.
     *
     * @param allAddressPoint the new all address point
     */
    public void setAllAddressPoint(final List<Addresspoint_mst> allAddressPoint) {
        this.allAddressPoint = allAddressPoint;
    }

    /**
     * Gets the list branch.
     *
     * @return the list branch
     */
    public List<Addresspoint_mst> getListBranch() {
        if (this.listBranch == null) {
            this.listBranch = this.dailyReportService.getListPositionEmployee();
        }
        return this.listBranch;
    }

    /**
     * Sets the list branch.
     *
     * @param listBranch the new list branch
     */
    public void setListBranch(final List<Addresspoint_mst> listBranch) {
        this.listBranch = listBranch;
    }

    /**
     * Gets the select branch.
     *
     * @return the select branch
     */
    public String getSelectBranch() {
        return this.selectBranch;
    }

    /**
     * Sets the select branch.
     *
     * @param selectBranch the new select branch
     */
    public void setSelectBranch(final String selectBranch) {
        if (!selectBranch.equals(this.selectBranch)) {
            this.selectBranch = selectBranch;
            this.selectedEmployee = null;
        }
    }

    /**
     * Toggle selection.
     *
     * @param report the report
     */
    public void toggleSelection(final Daily_report report) {
        if (report.isSelected()) {
            this.hasReportSelected = true;
            this.selectedReport = new Daily_report_DTO();
            BeanCopier.copy(report, this.selectedReport);
            // this.selectedReport = report;
            for (final Daily_report item : this.getAllReport()) {
                if (!item.equals(report) && item.isSelected()) {
                    item.setSelected(false);
                    break;
                }
            }
        } else {
            this.hasReportSelected = false;
        }
    }

    /**
     * Delete employee report.
     *
     * @throws TransactionRollbackException the transaction rollback exception
     */
    public void deleteEmployeeReport() throws TransactionRollbackException {
        // find selected report
        if ((this.allReport == null) || CollectionUtils.isEmpty(this.allReport)) {
            return;
        }

        // store report is selected.
        final List<Daily_report> selectedReports = new ArrayList<Daily_report>();
        for (final Daily_report report : this.allReport) {
            if (report.isSelected()) {
                selectedReports.add(report);
                break;
            }
        }
        if (selectedReports.size() == 0) {
            ErrorMessage.daily_005_you_must_select_atleast_1_item().show();
            return;
        }

        // Check if month of selected report disabled or not
        final Monthly_report_revision monthlyReport =
            this.dailyReportService.getMonthlyReportByEmpCodeAndTimeOfReport(this.userCredential.getUserEmployee()
                .getEmp_code(), DateUtils.getMonth(selectedReports.get(0).getDai_work_date()), DateUtils
                .getYear(selectedReports.get(0).getDai_work_date()));
        if ((monthlyReport != null) && !monthlyReport.isOpenStatus()) {
            InfoMessage.daily_019_disabled_delete_daily_report_when_monthly_report_disabled().show();
            return;
        }

        final ServiceResult<Daily_report> deletedReport = this.dailyReportService.deleteReport(selectedReports.get(0));

        if (deletedReport.isSuccessful()) {
            // Reset table daily report and reset form add new report
            this.allReport.remove(selectedReports.get(0));

            InfoMessage.daily_002_delete_successfully().show();
        } else {
            deletedReport.showMessages(FacesContext.getCurrentInstance());
        }
    }

    /**
     * Filter employee.
     *
     * @param query the query
     * @return the list
     */
    public List<Employee_mst> filterEmployee(final String query) {
        if (StringUtils.isEmpty(query)) {
            return this.getAllActiveEmployee();
        }

        List<Employee_mst> list = CollectionUtils.filter(this.getAllActiveEmployee(), new Predicate() {
            @Override
            public boolean evaluate(final Object arg0) {
                // return true if the item matched with query
                final Employee_mst item = (Employee_mst) arg0;

                return item.getEmp_name().contains(query) || String.valueOf(item.getEmp_code()).contains(query);
            }
        });
        return list;
    }

    /**
     * Search history by employee and date.
     *
     * @param employeeCode the employee code
     * @param selectDate the select date
     */
    public void searchHistoryByEmployeeAndDate(final int employeeCode, final Date selectDate) {
        this.selectedDate = selectDate;
        this.selectedEmployee = this.dailyReportService.getEmployeeByEmpCode(employeeCode);
        this.allReport = this.dailyReportService.searchReportByDateAndEmpCode(employeeCode, selectDate);
        this.selectBranch = this.selectedEmployee.getEmp_adpcode();
    }

    /**
     * Render list employee.
     */
    public void renderListEmployee() {
        if (!this.isIncludeRetiredEmployee) {
            this.allActiveEmployee = this.dailyReportService.getEmployeeByAddrAndRetiredCondition(this.selectBranch);
        } else {
            this.allActiveEmployee = this.dailyReportService.getEmployeeByAddr(this.selectBranch);
        }
        this.selectedEmployee = null;
    }

    /**
     * Gets the all active employee.
     *
     * @return the all active employee
     */
    public List<Employee_mst> getAllActiveEmployee() {
        if (this.allActiveEmployee == null) {
            this.allActiveEmployee = this.dailyReportService.getAllActiveEmployee();
        }
        return this.allActiveEmployee;
    }

    /**
     * Gets the all report.
     *
     * @return the all report
     */
    public List<Daily_report> getAllReport() {
        return this.allReport;
    }

    /**
     * Sets the all report.
     *
     * @param allReport the new all report
     */
    public void setAllReport(final List<Daily_report> allReport) {
        this.allReport = allReport;
    }

    /**
     * Checks if is checks for report selected.
     *
     * @return true, if is checks for report selected
     */
    public boolean isHasReportSelected() {
        return this.hasReportSelected;
    }

    /**
     * Sets the checks for report selected.
     *
     * @param hasReportSelected the new checks for report selected
     */
    public void setHasReportSelected(final boolean hasReportSelected) {
        this.hasReportSelected = hasReportSelected;
    }

    /**
     * Gets the selected date.
     *
     * @return the selected date
     */
    public Date getSelectedDate() {
        return this.selectedDate;
    }

    /**
     * Sets the selected date.
     *
     * @param selectedDate the new selected date
     */
    public void setSelectedDate(final Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    /**
     * Gets the selected report.
     *
     * @return the selected report
     */
    public Daily_report_DTO getSelectedReport() {
        return this.selectedReport;
    }

    /**
     * Sets the selected report.
     *
     * @param selectedReport the new selected report
     */
    public void setSelectedReport(final Daily_report_DTO selectedReport) {
        this.selectedReport = selectedReport;
    }

    /**
     * Gets the selected employee.
     *
     * @return the selected employee
     */
    public Employee_mst getSelectedEmployee() {
        return this.selectedEmployee;
    }

    /**
     * Sets the selected employee.
     *
     * @param selectedEmployee the new selected employee
     */
    public void setSelectedEmployee(final Employee_mst selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
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

    /**
     * Checks if is show all business detail.
     *
     * @return true, if is show all business detail
     */
    public boolean isShowAllBusinessDetail() {
        return this.showAllBusinessDetail;
    }

    /**
     * Sets the show all business detail.
     *
     * @param showAllBusinessDetail the new show all business detail
     */
    public void setShowAllBusinessDetail(final boolean showAllBusinessDetail) {
        this.showAllBusinessDetail = showAllBusinessDetail;
    }

    /**
     * Swith to screen daily report.
     */
    public void swithToScreenDailyReport() {
        final Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(ScreenContext.SharingDataKey.DAILY_REPORT, this.selectedReport);
        this.screenBean.switchScreenWithObjectData(ScreenContext.DAILY_REPORT_REGISTER, false, attrs);
    }

    /**
     * Gets the list.
     *
     * @return the list
     */
    public List<Boolean> getList() {
        if (CollectionUtils.isEmpty(this.list)) {
            // Default initialize 15 items respectively 15 column
            this.list =
                Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
        }
        return this.list;
    }

    /**
     * On toggle.
     *
     * @param event the e
     */
    public void onToggle(final ToggleEvent event) {
        this.list.set((Integer) event.getData(), event.getVisibility() == Visibility.VISIBLE);
    }

    /**
     * Checks if is include retired employee.
     *
     * @return true, if is include retired employee
     */
    public boolean isIncludeRetiredEmployee() {
        return this.isIncludeRetiredEmployee;
    }

    /**
     * Sets the include retired employee.
     *
     * @param isIncludeRetiredEmployee the new include retired employee
     */
    public void setIncludeRetiredEmployee(final boolean isIncludeRetiredEmployee) {
        this.isIncludeRetiredEmployee = isIncludeRetiredEmployee;
        this.renderListEmployee();
    }

    /**
     * Checks if is checks for not permission edit report.
     *
     * @return true, if is checks for not permission edit report
     */
    public boolean isHasNotPermissionEditReport() {
        if (this.selectedEmployee == null) {
            return true;
        }

        // Employee selected is current user or current user is manager HQ
        if ((this.selectedEmployee.getEmp_code() == this.userCredential.getUserEmployee().getEmp_code())
            || (this.userCredential.getUserEmployee().isManager() && this.userCredential.isHeadQuarterOfficer())) {
            return false;
        }

        // Employee selected is manager HQ or manager branch of current employee selected
        Branch_position branchPosition =
            this.summaryReportService.getActiveBranchPositionByEmployeeCodeAndTargetBranch(this.userCredential
                .getUserEmployee().getEmp_code(), this.selectedEmployee.getAddresspoint_mst().getAdp_code());

        if ((branchPosition != null) && (branchPosition.isBranchLeader() || branchPosition.isBranchViceLeader())) {
            return false;
        }

        return true;
    }

}
