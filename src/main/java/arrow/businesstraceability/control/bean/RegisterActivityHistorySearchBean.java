package arrow.businesstraceability.control.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import arrow.businesstraceability.constant.HistoryDailyReportConstants;
import arrow.businesstraceability.control.helper.ServiceSearchResult;
import arrow.businesstraceability.control.service.DailyReportService;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.framework.faces.component.pane.ModalPanel;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.util.DateUtils;
import arrow.framework.util.collections.CollectionUtils;

@Named
@ViewScoped
public class RegisterActivityHistorySearchBean extends ModalPanel {

    @Inject
    private DailyReportService reportService;

    private Daily_report selectedReport;

    private List<Daily_report> reportsOfSelectedUser;

    @Inject
    private DailyReportService dailyReportService;

    private List<Daily_report> allReport;

    private Daily_report currentReport;

    private List<Employee_mst> allEmployee;

    private List<Addresspoint_mst> allAddressPoint;

    private List<Company_mst> listCompany;

    private List<Addresspoint_mst> listPositionEmployee;

    private Employee_mst selectedEmployee;

    // Initial Bean variable for form history search
    private Date startDate;

    private Date endDate;

    private String selectPosition;

    private String inputTextCompanyActivity;

    private String selectAddressPoint;

    private String companyName;

    private String companyDepartment;

    private String personInChanrge;

    private boolean[] checkBoxReminder;

    private boolean[] checkBoxProjectFlag;

    private boolean[] checkBoxOpenFlag;

    // Variable check select check box in working time
    private boolean hasSelectStartTime = false;

    private boolean hasSelectEndTime = false;

    private boolean hasReportSelected;

    // Variable check expand
    private boolean hasExpandAllContentReport = false;

    // Count total result of search
    private long totalResult;

    private boolean enableEndDate = false;

    private boolean enableStartTime = false;

    private boolean isBusinessTask;

    private boolean isOtherTask;

    private List<Boolean> list;

    private boolean isIncludeRetiredEmployee;

    /**
     * Instantiates a new register activity history search bean.
     */
    public RegisterActivityHistorySearchBean() {
        this.allReport = new ArrayList<Daily_report>();
    }


    /**
     * Inits the.
     */
    public void init() {
        this.resetConditions();
    }

    /**
     * Search form.
     *
     * @see Action when click button search on form table list data
     */
    public void searchForm() {
        this.show();
    }

    /**
     * Reset conditions.
     */
    public void resetConditions() {
        this.enableStartTime = false;
        this.startDate = null;

        this.enableEndDate = false;
        this.endDate = null;
    }

    /**
     * Checks if is enable end date.
     *
     * @return true, if is enable end date
     */
    public boolean isEnableEndDate() {
        return this.enableEndDate;
    }

    /**
     * Sets the enable end date.
     *
     * @param enableEndDate the new enable end date
     */
    public void setEnableEndDate(final boolean enableEndDate) {
        this.enableEndDate = enableEndDate;
        if (this.enableEndDate) {
            this.endDate = this.startDate;
        } else {
            this.endDate = null;
        }
    }

    /**
     * Checks if is empty search condition.
     *
     * @return true, if is empty search condition
     */
    private boolean isEmptySearchCondition() {
        return (null == this.getStartDate()) && (null == this.getEndDate()) && (null == this.getSelectPosition())
            && (null == this.getSelectedEmployee()) && (null == this.getSelectAddressPoint())
            && StringUtils.isEmpty(this.getCompanyName()) && StringUtils.isEmpty(this.getCompanyDepartment())
            && StringUtils.isEmpty(this.getInputTextCompanyActivity())
            && StringUtils.isEmpty(this.getPersonInChanrge());
    }

    /**
     * Search history report.
     */
    public void searchHistoryReport() {
        if ((this.startDate != null) && (this.endDate != null) && DateUtils.after(this.startDate, this.endDate)) {
            ErrorMessage.sum_003_start_date_must_be_before_end_date().show();
            return;
        }

        ServiceSearchResult<Daily_report> resultHistoryDailyReport =
            this.dailyReportService.searchHistoryDailyReport(this);
        this.allReport = resultHistoryDailyReport.getListItems();
        this.totalResult = resultHistoryDailyReport.getTotalItems();
        RequestContext.getCurrentInstance().execute("PF('searchHistoryDialog_js').hide()");

        if (HistoryDailyReportConstants.MAX_RECORDS <= this.totalResult) {
            RequestContext.getCurrentInstance().execute("PF('warningDialog').show()");
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
            for (final Daily_report item : this.getAllReport()) {
                if (!item.equals(report) && item.isSelected()) {
                    item.setSelected(false);
                    break;
                }
            }
        }
    }

    /**
     * Gets the employee by addr point.
     *
     */
    public void getEmployeeByAddrPoint() {
        this.allEmployee = this.dailyReportService.getListActiveEmployeeByAddrPoint(this.selectPosition,
            this.isIncludeRetiredEmployee);
        this.selectedEmployee = null;
    }

    /**
     * Toggle expand all content report.
     */
    public void toggleExpandAllContentReport() {
        this.hasExpandAllContentReport = !this.hasExpandAllContentReport;
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
     * Gets the current report.
     *
     * @return the current report
     */
    public Daily_report getCurrentReport() {
        return this.currentReport;
    }

    /**
     * Sets the current report.
     *
     * @param currentReport the new current report
     */
    public void setCurrentReport(final Daily_report currentReport) {
        this.currentReport = currentReport;
    }

    /**
     * Filter employee.
     *
     * @param query the query
     * @return the list
     */
    public List<Employee_mst> filterEmployee(final String query) {
        if (StringUtils.isEmpty(query)) {
            return this.getAllEmployee();
        }

        return CollectionUtils.filter(this.getAllEmployee(), new Predicate() {
            @Override
            public boolean evaluate(final Object arg0) {
                // return true if the item matched with query
                final Employee_mst item = (Employee_mst) arg0;
                return item.getEmp_name().contains(query) || String.valueOf(item.getEmp_code()).contains(query);
            }
        });
    }

    /**
     * Event selected a row on table.
     *
     * @param event the event
     */
    public void onRowSelect(final SelectEvent event) {
        this.selectedReport = (Daily_report) event.getObject();
    }

    /**
     * Gets the all employee.
     *
     * @return the all employee
     */
    public List<Employee_mst> getAllEmployee() {
        if (this.allEmployee == null) {
            if (!this.isIncludeRetiredEmployee) {
                this.allEmployee = this.dailyReportService.getAllActiveEmployee();
            } else {
                this.allEmployee = this.dailyReportService.getAllNotDeletedEmployee();
            }
        }
        return this.allEmployee;
    }

    /**
     * Sets the all employee.
     *
     * @param allEmployee the new all employee
     */
    public void setAllEmployee(final List<Employee_mst> allEmployee) {
        this.allEmployee = allEmployee;
    }

    /**
     * Gets the all address point.
     *
     * @return the all address point
     */
    public List<Addresspoint_mst> getAllAddressPoint() {
        if (this.allAddressPoint == null) {
            this.allAddressPoint = this.dailyReportService.getAllAddressPoint();
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
     * Gets the list company.
     *
     * @return the list company
     */
    public List<Company_mst> getListCompany() {
        if (this.listCompany == null) {
            this.listCompany = this.dailyReportService.getListCompany();
        }
        return this.listCompany;
    }

    /**
     * Sets the list company.
     *
     * @param listCompany the new list company
     */
    public void setListCompany(final List<Company_mst> listCompany) {
        this.listCompany = listCompany;
    }

    /**
     * Gets the list position employee.
     *
     * @return the list position employee
     */
    public List<Addresspoint_mst> getListPositionEmployee() {
        if (this.listPositionEmployee == null) {
            this.listPositionEmployee = this.dailyReportService.getListPositionEmployee();
        }
        return this.listPositionEmployee;
    }

    /**
     * Sets the list position employee.
     *
     * @param listPositionEmployee the new list position employee
     */
    public void setListPositionEmployee(final List<Addresspoint_mst> listPositionEmployee) {
        this.listPositionEmployee = listPositionEmployee;
    }

    /**
     * Checks if is enable start time.
     *
     * @return true, if is enable start time
     */
    public boolean isEnableStartTime() {
        return this.enableStartTime;
    }

    /**
     * Sets the enable start time.
     *
     * @param value the new enable start time
     */
    public void setEnableStartTime(final boolean value) {
        this.enableStartTime = value;
        if (!this.enableStartTime) {
            this.clearTimeConditions();
        }
    }

    private void clearTimeConditions() {
        this.startDate = null;
        this.enableEndDate = false;
        this.endDate = null;
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
     * Checks if is business task.
     *
     * @return true, if is business task
     */
    public boolean isBusinessTask() {
        return this.isBusinessTask;
    }

    /**
     * Sets the business task.
     *
     * @param businessTask the new business task
     */
    public void setBusinessTask(final boolean businessTask) {
        this.isBusinessTask = businessTask;
    }

    /**
     * Checks if is other task.
     *
     * @return true, if is other task
     */
    public boolean isOtherTask() {
        return this.isOtherTask;
    }

    /**
     * Sets the other task.
     *
     * @param otherTask the new other task
     */
    public void setOtherTask(final boolean otherTask) {
        this.isOtherTask = otherTask;
    }

    /**
     * Gets the select position.
     *
     * @return the select position
     */
    public String getSelectPosition() {
        return this.selectPosition;
    }

    /**
     * Sets the select position.
     *
     * @param selectPosition the new select position
     */
    public void setSelectPosition(final String selectPosition) {
        this.selectPosition = selectPosition;
    }

    /**
     * Gets the input text company activity.
     *
     * @return the input text company activity
     */
    public String getInputTextCompanyActivity() {
        return this.inputTextCompanyActivity;
    }

    /**
     * Sets the input text company activity.
     *
     * @param inputTextCompanyActivity the new input text company activity
     */
    public void setInputTextCompanyActivity(final String inputTextCompanyActivity) {
        this.inputTextCompanyActivity = inputTextCompanyActivity;
    }

    /**
     * Gets the select address point.
     *
     * @return the select address point
     */
    public String getSelectAddressPoint() {
        return this.selectAddressPoint;
    }

    /**
     * Sets the select address point.
     *
     * @param selectAddressPoint the new select address point
     */
    public void setSelectAddressPoint(final String selectAddressPoint) {
        this.selectAddressPoint = selectAddressPoint;
    }

    /**
     * Gets the company name.
     *
     * @return the company name
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * Sets the company name.
     *
     * @param companyName the new company name
     */
    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the company department.
     *
     * @return the company department
     */
    public String getCompanyDepartment() {
        return this.companyDepartment;
    }

    /**
     * Sets the company department.
     *
     * @param companyDepartment the new company department
     */
    public void setCompanyDepartment(final String companyDepartment) {
        this.companyDepartment = companyDepartment;
    }

    /**
     * Gets the check box reminder.
     *
     * @return the check box reminder
     */
    public boolean[] getCheckBoxReminder() {
        return this.checkBoxReminder;
    }

    /**
     * Sets the check box reminder.
     *
     * @param checkBoxReminder the new check box reminder
     */
    public void setCheckBoxReminder(final boolean[] checkBoxReminder) {
        this.checkBoxReminder = checkBoxReminder;
    }

    /**
     * Gets the check box project flag.
     *
     * @return the check box project flag
     */
    public boolean[] getCheckBoxProjectFlag() {
        return this.checkBoxProjectFlag;
    }

    /**
     * Sets the check box project flag.
     *
     * @param checkBoxProjectFlag the new check box project flag
     */
    public void setCheckBoxProjectFlag(final boolean[] checkBoxProjectFlag) {
        this.checkBoxProjectFlag = checkBoxProjectFlag;
    }

    /**
     * Gets the check box open flag.
     *
     * @return the check box open flag
     */
    public boolean[] getCheckBoxOpenFlag() {
        return this.checkBoxOpenFlag;
    }

    /**
     * Sets the check box open flag.
     *
     * @param checkBoxOpenFlag the new check box open flag
     */
    public void setCheckBoxOpenFlag(final boolean[] checkBoxOpenFlag) {
        this.checkBoxOpenFlag = checkBoxOpenFlag;
    }

    /**
     * Checks if is checks for select start time.
     *
     * @return true, if is checks for select start time
     */
    public boolean isHasSelectStartTime() {
        return this.hasSelectStartTime;
    }

    /**
     * Sets the checks for select start time.
     *
     * @param hasSelectStartTime the new checks for select start time
     */
    public void setHasSelectStartTime(final boolean hasSelectStartTime) {
        this.hasSelectStartTime = hasSelectStartTime;
    }

    /**
     * Checks if is checks for select end time.
     *
     * @return true, if is checks for select end time
     */
    public boolean isHasSelectEndTime() {
        return this.hasSelectEndTime;
    }

    /**
     * Sets the checks for select end time.
     *
     * @param hasSelectEndTime the new checks for select end time
     */
    public void setHasSelectEndTime(final boolean hasSelectEndTime) {
        this.hasSelectEndTime = hasSelectEndTime;
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
     * Checks if is checks for expand all content report.
     *
     * @return true, if is checks for expand all content report
     */
    public boolean isHasExpandAllContentReport() {
        return this.hasExpandAllContentReport;
    }

    /**
     * Sets the checks for expand all content report.
     *
     * @param hasExpandAllContentReport the new checks for expand all content report
     */
    public void setHasExpandAllContentReport(final boolean hasExpandAllContentReport) {
        this.hasExpandAllContentReport = hasExpandAllContentReport;
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
     * Checks if is enable search button.
     *
     * @return true, if is enable search button
     */
    public boolean isEnableSearchButton() {
        return !this.isEmptySearchCondition() || this.isSelectCheckboxInFormSearch();
    }

    private boolean isSelectCheckboxInFormSearch() {
        return this.isSelectCheckboxOpenFlag() || this.isSelectCheckboxReminder() || this.isSelectCheckboxProjectFlag()
            || this.isBusinessTask || this.isOtherTask;
    }

    private boolean isSelectCheckboxReminder() {
        return (this.checkBoxReminder != null) && (this.checkBoxReminder.length != 0);
    }

    private boolean isSelectCheckboxProjectFlag() {
        return (this.checkBoxProjectFlag != null) && (this.checkBoxProjectFlag.length != 0);
    }

    private boolean isSelectCheckboxOpenFlag() {
        return (this.checkBoxOpenFlag != null) && (this.checkBoxOpenFlag.length != 0);
    }

    /**
     * Gets the total result.
     *
     * @return the total result
     */
    public long getTotalResult() {
        return this.totalResult;
    }

    /**
     * Sets the total result.
     *
     * @param totalResult the new total result
     */
    public void setTotalResult(final Integer totalResult) {
        this.totalResult = totalResult;
    }

    /**
     * Gets the selected report.
     *
     * @return the selected report
     */
    public Daily_report getSelectedReport() {
        return this.selectedReport;
    }

    /**
     * Sets the selected report.
     *
     * @param selectedReport the new selected report
     */
    public void setSelectedReport(final Daily_report selectedReport) {
        this.selectedReport = selectedReport;
    }

    /**
     * Gets the reports of selected user.
     *
     * @return the reports of selected user
     */
    public List<Daily_report> getReportsOfSelectedUser() {
        if (this.selectedReport != null) {
            this.reportsOfSelectedUser = this.reportService.searchEmployeeReport(this.selectedReport.getDai_work_date(),
                this.selectedReport.getEmployee_mst());
        }
        return this.reportsOfSelectedUser;
    }

    /**
     * Sets the reports of selected user.
     *
     * @param reportsOfSelectedUser the new reports of selected user
     */
    public void setReportsOfSelectedUser(final List<Daily_report> reportsOfSelectedUser) {
        this.reportsOfSelectedUser = reportsOfSelectedUser;
    }

    /**
     * Gets the person in chanrge.
     *
     * @return the person in chanrge
     */
    public String getPersonInChanrge() {
        return this.personInChanrge;
    }

    /**
     * Sets the person in chanrge.
     *
     * @param personInChanrge the new person in chanrge
     */
    public void setPersonInChanrge(final String personInChanrge) {
        this.personInChanrge = personInChanrge;
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
        this.allEmployee = null;
        if (this.selectPosition != null) {
            this.allEmployee = this.dailyReportService.getListActiveEmployeeByAddrPoint(this.selectPosition,
                this.isIncludeRetiredEmployee);
        }
        if (!this.isIncludeRetiredEmployee && (this.selectedEmployee != null) && this.selectedEmployee.isRetired()) {
            this.selectedEmployee = null;
        }
    }
}
