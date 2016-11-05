package arrow.businesstraceability.control.bean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import arrow.businesstraceability.cache.entity.ApprovalAndSubmissionInfo;
import arrow.businesstraceability.constant.SummaryReportConstants;
import arrow.businesstraceability.constant.SummaryReportTypes;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.SummaryReportService;
import arrow.businesstraceability.permission.MonthlyReportPermission;
import arrow.businesstraceability.persistence.entity.Branch_position;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Monthly_report_revision;
import arrow.businesstraceability.util.SelectItemGenerator;
import arrow.businesstraceability.util.SelectItemGenerator.ListType;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.util.FaceletUtils;
import arrow.framework.util.DateUtils;
import arrow.framework.util.cdi.Instance;
import arrow.framework.util.collections.CollectionUtils;


/**
 * The Class SummaryReportBean.
 */
@Named
@ViewScoped
public class SummaryReportBean extends AbstractBean {

    /** The tab index. */
    int tabIndex = 0;

    /** The report type. */
    private int reportType;

    /** The monthly date. */
    private Date monthlyDate;

    /** The start date. */
    private Date startDate;

    /** The end date. */
    private Date endDate;

    /** The monthly date visited. */
    private Date monthlyDateVisited;

    /** The start date visited. */
    private Date startDateVisited;

    /** The end date visited. */
    private Date endDateVisited;

    /** The list branch. */
    private List<SelectItem> listBranch;

    /** The branch code. */
    private String branchCode;

    /** The disable end date. */
    private boolean disableEndDate;

    /** The companies. */
    private List<Company_mst> companies;

    /** The year periodic. */
    private String yearPeriodic;

    /** The month periodic. */
    private String monthPeriodic;

    /** The year visit time. */
    private String yearVisitTime;

    /** The month visit time. */
    private String monthVisitTime;

    /** The approval and submission info. */
    private ApprovalAndSubmissionInfo approvalAndSubmissionInfo;

    /** The summary service. */
    @Inject
    private SummaryReportService summaryService;

    /** The list employees. */
    private List<Employee_mst> listEmployees;

    /** The selected employee. */
    private Employee_mst selectedEmployee;

    /** The min visit times. */
    private int minVisitTimes;

    /** The show monthly report summary. */
    private boolean showMonthlyReportSummary = false;

    /** The monthly report. */
    private Monthly_report_revision monthlyReport;

    /** The show visit time panel. */
    private boolean showVisitTimePanel = true;

    /** The show purpose panel. */
    private boolean showPurposePanel = true;

    /** The monthly report permission. */
    private MonthlyReportPermission monthlyReportPermission;

    /** The old comment. */
    private String oldComment;

    /** The comment. */
    private String comment;

    private UICommand submit;

    private boolean reopenOrRejectSuccessful = false;

    /** The current user. */
    @Inject
    private UserCredential currentUser;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        this.disableEndDate = true;

        // set selected tab
        this.reportType = SummaryReportTypes.PeriodicReportTypes.MONTHLY_REPORT;

        // Default is current date time.
        this.yearPeriodic = DateUtils.getCurrentYear();
        this.yearVisitTime = this.yearPeriodic;
        this.monthPeriodic = DateUtils.getCurrentMonth();
        this.monthVisitTime = this.monthPeriodic;
        this.minVisitTimes = 1;
        this.currentUser = this.summaryService.getCurrentUser();
    }

    /**
     * Gets the select item time report.
     *
     * @return the select item time report
     */
    public List<SelectItem> getSelectItemTimeReport() {
        return SelectItemGenerator.getListSelectItem(ListType.PERIOD_REPORT_TYPES);
    }

    /**
     * Checks if is monthly report.
     *
     * @return true, if is monthly report
     */
    public boolean isMonthlyReport() {
        return SummaryReportTypes.PeriodicReportTypes.MONTHLY_REPORT == this.reportType;
    }

    /**
     * Checks if is period report.
     *
     * @return true, if is period report
     */
    public boolean isPeriodReport() {
        return SummaryReportTypes.PeriodicReportTypes.PERIOD_REPORT == this.reportType;
    }

    /**
     * Enable end date.
     */
    public void enableEndDate() {
        this.unselectedCompany(this.companies);
        this.companies = null;
        if (this.isPeriodReport()) {
            this.disableEndDate = false;
            this.monthlyDate = null;
            this.startDate = null;
            this.endDate = null;
            this.monthlyDateVisited = null;
            this.startDateVisited = null;
            this.endDateVisited = null;

        } else {
            this.disableEndDate = true;
            this.monthlyDate = null;
            this.startDate = null;
            this.endDate = null;
            this.monthlyDateVisited = null;
            this.startDateVisited = null;
            this.endDateVisited = null;
        }
    }

    /**
     * Unselected company.
     *
     * @param companies the companies
     */
    private void unselectedCompany(final List<Company_mst> companies) {
        if (CollectionUtils.isEmpty(companies)) {
            return;
        }
        for (Company_mst comp : companies) {
            comp.setSelected(false);
        }
    }

    /**
     * Gets the company by date and visit times.
     *
     */
    public void getCompanyByDateAndVisitTimes() {
        if (this.isMonthlyReport()) {
            this.monthlyDateVisited = this.setMonthlyDate(this.yearVisitTime, this.monthVisitTime);
            this.companies =
                this.summaryService.getCompanyForVisitTimeReport(DateUtils.getFirstDayOfMonth(this.monthlyDateVisited),
                    DateUtils.getLastDayOfMonth(this.monthlyDateVisited), this.minVisitTimes);
        } else if (this.isValidDateStartDateAndEndDate(this.startDateVisited, this.endDateVisited)) {
            this.companies =
                this.summaryService.getCompanyForVisitTimeReport(this.startDateVisited, this.endDateVisited,
                    this.minVisitTimes);
        }
    }

    /**
     * Checks if is valid date start date and end date.
     *
     * @param startDateVisited the start date visited
     * @param endDateVisited the end date visited
     * @return true, if is valid date start date and end date
     */
    private boolean isValidDateStartDateAndEndDate(final Date startDateVisited, final Date endDateVisited) {
        if ((startDateVisited == null) || (endDateVisited == null)) {
            return false;
        }
        if (DateUtils.after(startDateVisited, endDateVisited)) {
            ErrorMessage.sum_003_start_date_must_be_before_end_date().show();
            return false;
        }
        return true;
    }

    /**
     * Checks if is employee monthly report.
     *
     * @return true, if is employee monthly report
     */
    public boolean isEmployeeMonthlyReport() {
        return (this.selectedEmployee != null) && (this.selectedEmployee.getEmp_code() > 0)
            && (this.monthlyDate != null) && (SummaryReportTypes.PeriodicReportTypes.MONTHLY_REPORT == this.reportType);
    }

    /**
     * Generate periodic report.
     */
    public void generatePeriodicReport() {
        // Append string and return date value
        this.monthlyDate = this.setMonthlyDate(this.yearPeriodic, this.monthPeriodic);
        if (this.isPeriodReport() && this.startDate.after(this.endDate)) {
            ErrorMessage.sum_001_date_from_must_smaller_date_to().show();
            return;
        } else if (this.selectedEmployee == null) {
            ErrorMessage.sum_006_need_select_employee_for_report().show();
            return;
        }

        if (this.isEmployeeMonthlyReport()) {

            this.startDate = DateUtils.getFirstDayOfMonth(this.monthlyDate);
            this.endDate = DateUtils.getLastDayOfMonth(this.monthlyDate);
            this.monthlyReport =
                this.summaryService.getActiveMonthlyReportByEmpCodeAndTimeOfReportOrderByVersion(this.selectedEmployee,
                    this.startDate, this.endDate);

            if (!this.monthlyReport.isPersisted()) {
                this.monthlyReportPermission =
                    new MonthlyReportPermission(this.currentUser, this.selectedEmployee, this.monthlyReport, null);

                if (!this.monthlyReportPermission.hasViewPermission()) {
                    ErrorMessage.monthlyreport_005_report_no_existed().show();
                    return;
                }
            } else {
                Branch_position branchPosition =
                    this.summaryService.getActiveBranchPositionByEmployeeCodeAndTargetBranch(this.currentUser
                        .getUserEmployee().getEmp_code(), this.selectedEmployee.getAddresspoint_mst().getAdp_code());

                this.monthlyReportPermission =
                    new MonthlyReportPermission(this.currentUser, this.selectedEmployee, this.monthlyReport,
                        branchPosition);

                if (!this.monthlyReportPermission.hasViewPermission()) {
                    ErrorMessage.monthlyreport_001_no_permission_to_view_report().show();
                    return;
                }
            }

            this.approvalAndSubmissionInfo =
                this.summaryService.getApprovalAndSubmissionInfo(this.selectedEmployee, this.startDate);

            this.setShowMonthlyReportSummary(true);
            this.oldComment = null;
            this.comment = null;
            return;
        }

        ServiceResult<Object> result =
            this.summaryService.generateStatisticReport(this.startDate, this.endDate,
                StringUtils.isEmpty(this.branchCode) ? this.selectedEmployee.getEmp_adpcode() : this.branchCode,
                this.selectedEmployee, Instance.get(UserCredential.class).getEmployeeName());
        if (result.isNotSuccessful()) {
            result.getFirstMessage().show();
        }
    }

    /**
     * Generate monthly report.
     */
    public void exportMonthlyReport() {
        try {
            Workbook fileExport =
                this.summaryService.generateMonthlyReport(DateUtils.getFirstDayOfMonth(this.monthlyDate),
                    DateUtils.getLastDayOfMonth(this.monthlyDate), this.branchCode, this.selectedEmployee,
                    this.monthlyReport);
            String fileOutputName = SummaryReportConstants.MonthlyReport.OUTPUT_FILENAME;
            FaceletUtils.sendFileToClient(fileOutputName, fileExport);
        } catch (final InvalidFormatException | URISyntaxException | IOException e) {
            super.log.debug("Fail when generate Monthly Report", e);
        }

    }


    /**
     * Generate visit time report.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InvalidFormatException the invalid format exception
     */
    public void generateVisitTimeReport() throws IOException, InvalidFormatException {
        if (CollectionUtils.isEmpty(this.companies)
            || CollectionUtils.isEmpty(this.getSelectedCompanies(this.companies))) {
            ErrorMessage.sum_002_company_must_require().show();
            return;
        }
        final List<Company_mst> selectedCompanies = this.getSelectedCompanies(this.companies);
        Workbook fileExport = null;
        if (this.isMonthlyReport()) {
            fileExport =
                this.summaryService.generateVisitTimeReport(DateUtils.getFirstDayOfMonth(this.monthlyDateVisited),
                    DateUtils.getLastDayOfMonth(this.monthlyDateVisited), selectedCompanies);
        } else {
            fileExport =
                this.summaryService.generateVisitTimeReport(this.startDateVisited, this.endDateVisited,
                    selectedCompanies);
        }
        FaceletUtils.sendFileToClient(SummaryReportConstants.VisitTimeReport.OUTPUT_FILENAME, fileExport);
    }

    /**
     * Save monthly report.
     *
     * @param saveType the save type
     */
    public void saveMonthlyReport(final int saveType) {
        ServiceResult<Monthly_report_revision> result = null;
        switch (saveType) {
            case SummaryReportConstants.OperationType.TEMPORARY_SAVE:
                result = this.summaryService.saveTemporaryMonthlyReport(this.monthlyReport);
                break;
            case SummaryReportConstants.OperationType.SUBMISSION:
                result =
                    this.summaryService.submitMonthlyReport(this.monthlyReport, this.currentUser.getUserEmployee()
                        .getEmp_code());
                break;
            case SummaryReportConstants.OperationType.APPROVAL:

                result =
                    this.summaryService.approveMonthlyReport(this.monthlyReport, this.currentUser.getUserEmployee()
                        .getEmp_code(), this.comment);
                break;
            case SummaryReportConstants.OperationType.REOPEN:
                result =
                    this.summaryService.reopenMonthlyReport(this.monthlyReport, this.currentUser.getUserEmployee()
                        .getEmp_code(), this.comment);
                break;
            case SummaryReportConstants.OperationType.REJECT:
                result =
                    this.summaryService.rejectMonthlyReport(this.monthlyReport, this.currentUser.getUserEmployee()
                        .getEmp_code(), this.comment);
                break;
            default:
                return;
        }
        result.showMessages(FacesContext.getCurrentInstance());
        if (result.isSuccessful()
            && ((saveType == SummaryReportConstants.OperationType.REJECT) 
                || (saveType == SummaryReportConstants.OperationType.REOPEN))) {
            this.reopenOrRejectSuccessful = true;
            this.actionCloseOrKeepDialog();
        }
        if (result.isSuccessful() || this.isDataWasChangedByAnotherUser(result.getMessages())) {
            this.cleanMonthlyReportState();
        }

    }

    /**
     * Action close or keep dialog.
     */
    public void actionCloseOrKeepDialog() {
        if (this.reopenOrRejectSuccessful) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgComment').hide();");
            Instance.get(PopupPanelBean.class).hide();
            this.reopenOrRejectSuccessful = false;
        }
    }

    /**
     * Checks if is data was changed by another user.
     *
     * @param messages the messages
     * @return true, if is data was changed by another user
     */
    private boolean isDataWasChangedByAnotherUser(final List<Message> messages) {
        List<String> messageCodes =
            SummaryReportConstants.MonthlyReport.buildMessagesCodeNoticeMonthlyReportWasChangedByAnotherUser();
        for (Message msg : messages) {
            if (messageCodes.contains(msg.getMessageCode())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get monthly report view is read-only or not.
     *
     * @return true, if is monthly report read only
     */
    public boolean isMonthlyReportReadOnly() {
        if ((this.selectedEmployee != null)
            && (this.selectedEmployee.getEmp_code() != this.currentUser.getUserEmployee().getEmp_code())) {
            return true;
        }
        if ((this.monthlyReport == null) || !this.monthlyReport.isPersisted()) {
            return false;
        }
        return !this.monthlyReport.isOpenStatus();
    }

    /**
     * Show confirm submit monthly report.
     */
    public void showConfirmSubmitMonthlyReport() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('confirmSubmitDialog').show();");
    }

    /**
     * Close monthly report summary view.
     */
    public void closeMonthlyReport() {
        if (!this.summaryService.checkModifiedMonthlyReport(this.monthlyReport)) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('confirmCloseDialog').show();");
            return;
        }
        this.cleanMonthlyReportState();
    }

    /**
     * Clean monthly report state.
     */
    public void cleanMonthlyReportState() {
        this.monthlyReport = null;
        this.showMonthlyReportSummary = false;
        this.comment = null;

    }

    /**
     * Gets the number days in month.
     *
     * @return the number days in month
     */
    public int getNumberDaysInMonth() {
        return DateUtils.getDaysInMonth(Integer.parseInt(this.yearPeriodic), Integer.parseInt(this.monthPeriodic));
    }

    /**
     * Gets the creator.
     *
     * @return the creator
     */
    public String getCreator() {
        return this.currentUser.getEmployeeName();
    }

    /**
     * Checks if is selected employee same current user.
     *
     * @return true, if is selected employee same current user
     */
    private boolean isSelectedEmployeeSameCurrentUser() {
        return this.currentUser.getUserEmployee().getEmp_code() == this.selectedEmployee.getEmp_code();
    }

    /**
     * Check show submit button or not.
     *
     * @return true, if is show submition button
     */
    public boolean isShowSubmitionButton() {
        return this.isSelectedEmployeeSameCurrentUser()
            && ((this.monthlyReport == null) || !this.monthlyReport.isPersisted() || this.monthlyReport.isOpenStatus());
    }

    /**
     * Check show approval button or not.
     *
     * @return true, if is show approve button
     */
    public boolean isShowApproveButton() {
        return this.currentUser.getUserEmployee().isSupervisor() && this.isCurrentUserSameBranchWithSelectedEmployee()
            && (this.monthlyReport != null) && this.monthlyReport.isWaitingStatus();
    }

    /**
     * Check show revise button or not.
     *
     * @return true, if is show revise button
     */
    public boolean isShowReviseButton() {
        return this.currentUser.getUserEmployee().isSupervisor() && this.isCurrentUserSameBranchWithSelectedEmployee()
            && (this.monthlyReport != null)
            && (this.monthlyReport.isWaitingStatus() || this.monthlyReport.isApprovedStatus());
    }

    /**
     * Checks if is current user same branch with selected employee.
     *
     * @return true, if is current user same branch with selected employee
     */
    private boolean isCurrentUserSameBranchWithSelectedEmployee() {
        return this.selectedEmployee.getAddresspoint_mst().getAdp_code()
            .equals(this.currentUser.getUserEmployee().getAddresspoint_mst().getAdp_code());
    }

    /**
     * Check disable comment of branch head or not.
     *
     * @return true, if is disabled branch head comment
     */
    public boolean isDisabledBranchHeadComment() {
        return (this.monthlyReport != null)
            && this.monthlyReport.isWaitingStatus()
            && this.currentUser.getUserEmployee().isSupervisor()
            && (this.selectedEmployee.getAddresspoint_mst().getAdp_code().equals(this.currentUser.getUserEmployee()
                .getAddresspoint_mst().getAdp_code()));
    }


    /**
     * Checks if is show comment.
     *
     * @return true, if is show comment
     */
    public boolean isShowLastComment() {
        if ((this.monthlyReport.isPersisted() && this.monthlyReport.isOpenStatus())
            || ((this.isWaitingApprove(this.monthlyReport)) && (this.monthlyReport.getBajon_bangou() > 0) 
                && (this.monthlyReportPermission
                .hasApprovePermission() || this.monthlyReportPermission.hasReOpenPermission()))) {
            return true;
        }
        return false;
    }


    private boolean isWaitingApprove(final Monthly_report_revision monthlyReport2) {
        return monthlyReport2.isWaitingStatus();
    }

    /**
     * Gets the selected companies.
     *
     * @param listCompanies the list companies
     * @return the selected companies
     */
    private List<Company_mst> getSelectedCompanies(final List<Company_mst> listCompanies) {
        final List<Company_mst> selectedCompanies = new ArrayList<>();
        for (Company_mst comp : listCompanies) {
            if (comp.isSelected()) {
                selectedCompanies.add(comp);
            }
        }
        return selectedCompanies;
    }

    /**
     * Sets the monthly date.
     *
     * @param year the year
     * @param month the month
     * @return the date
     */
    public Date setMonthlyDate(final String year, final String month) {
        return Date.from(LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), 1, 0, 0, 0)
            .atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Reload list employee.
     */
    public void reloadListEmployee() {
        this.listEmployees = null;
        this.selectedEmployee = null;
    }


    /**
     * Gets the time report.
     *
     * @return the time report
     */
    public int getTimeReport() {
        return this.reportType;
    }

    /**
     * Sets the time report.
     *
     * @param timeReport the new time report
     */
    public void setTimeReport(final int timeReport) {
        this.reportType = timeReport;
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
     * Gets the list branch.
     *
     * @return the list branch
     */
    public List<SelectItem> getListBranch() {
        this.listBranch = this.summaryService.getListSelectItemAllBranch();
        return this.listBranch;
    }

    /**
     * Sets the list branch.
     *
     * @param listBranch the new list branch
     */
    public void setListBranch(final List<SelectItem> listBranch) {
        this.listBranch = listBranch;
    }

    /**
     * Gets the list employees.
     *
     * @return the list employees
     */
    public List<Employee_mst> getListEmployees() {
        if (this.listEmployees == null) {
            this.listEmployees = new ArrayList<>();
            this.listEmployees.addAll(this.summaryService.listEmployeeByBranch(this.branchCode));
        }
        return this.listEmployees;
    }

    /**
     * Sets the list employees.
     *
     * @param listEmployees the new list employees
     */
    public void setListEmployees(final List<Employee_mst> listEmployees) {
        this.listEmployees = listEmployees;
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
     * Checks if is disable end date.
     *
     * @return true, if is disable end date
     */
    public boolean isDisableEndDate() {
        return this.disableEndDate;
    }

    /**
     * Sets the disable end date.
     *
     * @param disableEndDate the new disable end date
     */
    public void setDisableEndDate(final boolean disableEndDate) {
        this.disableEndDate = disableEndDate;
    }

    /**
     * Gets the monthly date.
     *
     * @return the monthly date
     */
    public Date getMonthlyDate() {
        return this.monthlyDate;
    }

    /**
     * Sets the monthly date.
     *
     * @param monthlyDate the new monthly date
     */
    public void setMonthlyDate(final Date monthlyDate) {
        this.monthlyDate = monthlyDate;
    }

    /**
     * Gets the monthly date visited.
     *
     * @return the monthly date visited
     */
    public Date getMonthlyDateVisited() {
        return this.monthlyDateVisited;
    }

    /**
     * Sets the monthly date visited.
     *
     * @param monthlyDateVisited the new monthly date visited
     */
    public void setMonthlyDateVisited(final Date monthlyDateVisited) {
        this.monthlyDateVisited = monthlyDateVisited;
    }

    /**
     * Gets the start date visited.
     *
     * @return the start date visited
     */
    public Date getStartDateVisited() {
        return this.startDateVisited;
    }

    /**
     * Sets the start date visited.
     *
     * @param startDateVisited the new start date visited
     */
    public void setStartDateVisited(final Date startDateVisited) {
        this.startDateVisited = startDateVisited;
    }

    /**
     * Gets the end date visited.
     *
     * @return the end date visited
     */
    public Date getEndDateVisited() {
        return this.endDateVisited;
    }

    /**
     * Sets the end date visited.
     *
     * @param endDateVisited the new end date visited
     */
    public void setEndDateVisited(final Date endDateVisited) {
        this.endDateVisited = endDateVisited;
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
     * Filter employee.
     *
     * @param query the query
     * @return the list
     */
    public List<Employee_mst> filterEmployee(final String query) {
        if (StringUtils.isEmpty(query)) {
            return this.getListEmployees();
        }

        return CollectionUtils.filter(this.getListEmployees(), new Predicate() {

            @Override
            public boolean evaluate(final Object arg0) {
                // return true if the item matched with query
                final Employee_mst item = (Employee_mst) arg0;
                return item.getEmp_name().contains(query) || String.valueOf(item.getEmp_code()).contains(query);
            }
        });
    }

    /**
     * Gets the tab index.
     *
     * @return the tab index
     */
    public int getTabIndex() {
        return this.tabIndex;
    }

    /**
     * Sets the tab index.
     *
     * @param tabIndex the new tab index
     */
    public void setTabIndex(final int tabIndex) {
        this.tabIndex = tabIndex;
    }

    /**
     * On tab change.
     *
     * @param event the event
     */
    public void onTabChange(final TabChangeEvent event) {
        final int index = ((TabView) event.getTab().getParent()).getChildren().indexOf(event.getTab());
        this.tabIndex = index;
        this.resetTabContent();
    }

    /**
     * Reset tab content.
     */
    private void resetTabContent() {
        this.setTimeReport(SummaryReportTypes.PeriodicReportTypes.MONTHLY_REPORT);
        this.selectedEmployee = null;
        this.unselectedCompany(this.companies);
        this.companies = null;
    }

    /**
     * Gets the min visit times.
     *
     * @return the min visit times
     */
    public int getMinVisitTimes() {
        return this.minVisitTimes;
    }

    /**
     * Sets the min visit times.
     *
     * @param minVisitTimes the new min visit times
     */
    public void setMinVisitTimes(final int minVisitTimes) {
        this.minVisitTimes = minVisitTimes;
    }

    /**
     * Checks if is select all companies.
     *
     * @return true, if is select all companies
     */
    public boolean isSelectAllCompanies() {
        if (CollectionUtils.isEmpty(this.companies)) {
            return false;
        }
        for (final Company_mst comp : this.companies) {
            if (!comp.isSelected()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets the select all companies.
     *
     * @param value the new select all companies
     */
    public void setSelectAllCompanies(final boolean value) {
        if (this.companies != null) {
            for (final Company_mst comp : this.companies) {
                comp.setSelected(value);
            }
        }
    }

    /**
     * Change end date.
     *
     * @return the date
     */
    public Date changeEndDate() {
        final Date date = this.startDate;
        if (this.endDate == null) {
            this.endDate = DateUtils.adjustDay(date, 7);
        }
        return this.endDate;
    }

    /**
     * Change end date visited.
     *
     * @return the date
     */
    public Date changeEndDateVisited() {
        final Date date = this.startDateVisited;
        if (this.endDateVisited == null) {
            this.endDateVisited = DateUtils.adjustDay(date, 7);
        }
        return this.endDateVisited;
    }

    /**
     * Gets the year periodic.
     *
     * @return the year periodic
     */
    public String getYearPeriodic() {
        return this.yearPeriodic;
    }

    /**
     * Sets the year periodic.
     *
     * @param yearPeriodic the new year periodic
     */
    public void setYearPeriodic(final String yearPeriodic) {
        this.yearPeriodic = yearPeriodic;
    }

    /**
     * Gets the month periodic.
     *
     * @return the month periodic
     */
    public String getMonthPeriodic() {
        return this.monthPeriodic;
    }

    /**
     * Sets the month periodic.
     *
     * @param monthPeriodic the new month periodic
     */
    public void setMonthPeriodic(final String monthPeriodic) {
        this.monthPeriodic = monthPeriodic;
    }

    /**
     * Gets the year visit time.
     *
     * @return the year visit time
     */
    public String getYearVisitTime() {
        return this.yearVisitTime;
    }

    /**
     * Sets the year visit time.
     *
     * @param yearVisitTime the new year visit time
     */
    public void setYearVisitTime(final String yearVisitTime) {
        this.yearVisitTime = yearVisitTime;
    }

    /**
     * Gets the month visit time.
     *
     * @return the month visit time
     */
    public String getMonthVisitTime() {
        return this.monthVisitTime;
    }

    /**
     * Sets the month visit time.
     *
     * @param monthVisitTime the new month visit time
     */
    public void setMonthVisitTime(final String monthVisitTime) {
        this.monthVisitTime = monthVisitTime;
    }

    /**
     * Search company.
     */
    public void searchCompany() {
        this.getCompanyByDateAndVisitTimes();
    }

    /**
     * Checks if is disable generate visited report.
     *
     * @return true, if is disable generate visited report
     */
    public boolean isDisableGenerateVisitedReport() {
        return CollectionUtils.isEmpty(this.companies)
            || CollectionUtils.isEmpty(this.getSelectedCompanies(this.companies));
    }

    /**
     * Checks if is disable generate periodic report.
     *
     * @return true, if is disable generate periodic report
     */
    public boolean isDisableGeneratePeriodicReport() {
        if (this.isPeriodReport()) {
            return !this.isValidDateStartDateAndEndDate(this.startDate, this.endDate)
                || (this.selectedEmployee == null);
        } else {
            return this.selectedEmployee == null;
        }
    }

    /**
     * Checks if is show monthly report summary.
     *
     * @return true, if is show monthly report summary
     */
    public boolean isShowMonthlyReportSummary() {
        return this.showMonthlyReportSummary;
    }

    /**
     * Sets the show monthly report summary.
     *
     * @param showMonthlyReportSummary the new show monthly report summary
     */
    public void setShowMonthlyReportSummary(final boolean showMonthlyReportSummary) {
        this.showMonthlyReportSummary = showMonthlyReportSummary;
    }

    /**
     * Sets the show.
     *
     * @param value the new show
     */
    public void setShow(final boolean value) {
        this.showMonthlyReportSummary = value;
    }

    /**
     * Gets the monthly report revision.
     *
     * @return the monthly report revision
     */
    public Monthly_report_revision getMonthlyReportRevision() {
        return this.monthlyReport;
    }

    /**
     * Sets the monthly report revision.
     *
     * @param monthlyReportRevision the new monthly report revision
     */
    public void setMonthlyReportRevision(final Monthly_report_revision monthlyReportRevision) {
        this.monthlyReport = monthlyReportRevision;
    }

    /**
     * Checks if is show visit time panel.
     *
     * @return true, if is show visit time panel
     */
    public boolean isShowVisitTimePanel() {
        return this.showVisitTimePanel;
    }

    /**
     * Sets the show visit time panel.
     *
     * @param showVisitTimePanel the new show visit time panel
     */
    public void setShowVisitTimePanel(final boolean showVisitTimePanel) {
        this.showVisitTimePanel = showVisitTimePanel;
    }

    /**
     * Checks if is show purpose panel.
     *
     * @return true, if is show purpose panel
     */
    public boolean isShowPurposePanel() {
        return this.showPurposePanel;
    }

    /**
     * Sets the show purpose panel.
     *
     * @param showPurposePanel the new show purpose panel
     */
    public void setShowPurposePanel(final boolean showPurposePanel) {
        this.showPurposePanel = showPurposePanel;
    }

    /**
     * Gets the monthly report permission.
     *
     * @return the monthly report permission
     */
    public MonthlyReportPermission getMonthlyReportPermission() {
        return this.monthlyReportPermission;
    }

    /**
     * Sets the monthly report permission.
     *
     * @param monthlyReportPermission the new monthly report permission
     */
    public void setMonthlyReportPermission(final MonthlyReportPermission monthlyReportPermission) {
        this.monthlyReportPermission = monthlyReportPermission;
    }

    /**
     * Gets the approval and submission info.
     *
     * @return the approval and submission info
     */
    public ApprovalAndSubmissionInfo getApprovalAndSubmissionInfo() {
        return this.approvalAndSubmissionInfo;
    }

    /**
     * Sets the approval and submission info.
     *
     * @param approvalAndSubmissionInfo the new approval and submission info
     */
    public void setApprovalAndSubmissionInfo(final ApprovalAndSubmissionInfo approvalAndSubmissionInfo) {
        this.approvalAndSubmissionInfo = approvalAndSubmissionInfo;
    }

    /**
     * Gets the comment.
     *
     * @return the comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Sets the comment.
     *
     * @param comment the new comment
     */
    public void setComment(final String comment) {
        this.comment = comment;
    }

    /**
     * Gets the old comment.
     *
     * @return the old comment
     */
    public String getOldComment() {
        if (this.oldComment == null) {
            this.oldComment = this.summaryService.findOldComment(this.selectedEmployee, this.startDate);
        }
        return this.oldComment;
    }

    public void setOldComment(final String oldComment) {
        this.oldComment = oldComment;
    }


    public UICommand getSubmit() {
        return this.submit;
    }

    public void setSubmit(final UICommand submit) {
        this.submit = submit;
    }

}
