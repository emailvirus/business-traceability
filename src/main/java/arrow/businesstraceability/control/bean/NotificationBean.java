package arrow.businesstraceability.control.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.control.helper.ScreenContext;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.EmployeeManagementService;
import arrow.businesstraceability.control.service.NotificationService;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Notification_config;
import arrow.businesstraceability.persistence.entity.Notification_item;
import arrow.businesstraceability.persistence.entity.Position_mst;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.util.StringUtils;
import arrow.framework.util.collections.CollectionUtils;

/**
 * The Class NotificationBean.
 */
@Named
@ViewScoped
public class NotificationBean extends AbstractBean {

    /** The notify service. */
    @Inject
    private NotificationService notifyService;

    /** The user credential. */
    @Inject
    private UserCredential userCredential;

    /** The faces context. */
    @Inject
    private FacesContext facesContext;

    /** The all branch. */
    // For Notification configuration
    private List<Addresspoint_mst> allBranch;

    /** The all employee. */
    private List<Employee_mst> allEmployee;

    /** The all position. */
    private List<Position_mst> allPosition;

    /** The list notification. */
    private List<Notification_config> listNotification;

    /** The selected notification config. */
    private Notification_config selectedNotificationConfig;

    /** The selected address. */
    private String selectedAddress = "";

    /** The selected employee. */
    private int selectedEmployee;

    /** The selected position. */
    private int selectedPosition;

    /** The has notification selected. */
    private boolean hasNotificationSelected;

    /** The dash board notification. */
    // Beans for DashBoard
    private List<Notification_config> dashBoardNotification;

    /** The list report in dash board. */
    private List<Daily_report> listReportInDashBoard;

    /** The list report in dash board will delete. */
    private List<Daily_report> listReportInDashBoardWillDelete;

    /** The time no report. */
    // For setting time to call schedule when you not register report or create new report
    private Date timeNoReport;

    /** The time new report. */
    private Date timeNewReport;

    /** The report selected by user. */
    private Daily_report reportSelectedByUser;

    /** The employee management service. */
    @Inject
    private EmployeeManagementService employeeManagementService;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        if (this.allBranch == null) {
            this.allBranch = this.notifyService.getListAddrContraintEmployee();
        }
        this.listNotification = this.notifyService.getListNotificationConfig(this.userCredential.getUserCode());
        this.dashBoardNotification = this.notifyService
                .getListNotificationConfigForDashBoard(this.userCredential.getUserCode());
        this.listReportInDashBoardWillDelete = new ArrayList<>();
    }

    /**
     * Gets the all employee.
     *
     * @return the all employee
     */
    public List<Employee_mst> getAllEmployee() {
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
     * Render list employee.
     */
    public void renderListEmployee() {
        this.allEmployee = this.notifyService.getEmployeeByAddrAndPos(this.selectedAddress, this.selectedPosition,
                this.userCredential.getUserCode());
    }

    /**
     * Render list position.
     */
    public void renderListPosition() {
        this.allPosition = this.notifyService
                .getPositionByAddr(this.selectedAddress, this.userCredential.getUserCode());
        this.allEmployee = this.notifyService.getEmployeeByAddrAndPos(this.selectedAddress, this.selectedPosition,
                this.userCredential.getUserCode());
    }

    /**
     * Adds the new notification.
     */
    public void addNewNotification() {
        final ServiceResult<Notification_config> results = this.notifyService
                .saveNotification(this, this.userCredential.getUserEmployee());
        if (results.isSuccessful()) {
            this.listNotification = this.notifyService.getListNotificationConfig(this.userCredential.getUserCode());
            InfoMessage.daily_001_save_successfully().show();
        }
        else {
            results.showMessages(this.facesContext);
        }
    }

    /**
     * Delete notification.
     */
    public void deleteNotification() {
        // find selected notification
        if (CollectionUtils.isEmpty(this.listNotification)) {
            return;
        }
        // store notification is selected.
        if (this.selectedNotificationConfig == null) {
            ErrorMessage.daily_005_you_must_select_atleast_1_item().show();
            return;

        }
        else {
            if (!this.selectedNotificationConfig.isSelected()) {
                ErrorMessage.daily_005_you_must_select_atleast_1_item().show();
                return;
            }
        }

        final ServiceResult<Notification_config> deletedReport = this.notifyService
                .deleteNotifications(this.selectedNotificationConfig);

        if (deletedReport.isSuccessful()) {
            // Reset table daily report and reset form add new report
            this.listNotification = this.notifyService.getListNotificationConfig(this.userCredential.getUserCode());
            this.selectedNotificationConfig.setSelected(false);
            InfoMessage.daily_002_delete_successfully().show();
        }
        else {
            deletedReport.showMessages(FacesContext.getCurrentInstance());
        }

    }

    /**
     * Toggle selection.
     *
     * @param notification the notification
     */
    public void toggleSelection(final Notification_config notification) {
        if (notification.isSelected()) {
            this.hasNotificationSelected = true;
            if (this.selectedNotificationConfig != null) {
                this.selectedNotificationConfig.setSelected(false);
            }
            this.selectedNotificationConfig = notification;
        }
        else {
            this.selectedNotificationConfig.setSelected(false);
            this.hasNotificationSelected = false;
        }
    }

    /**
     * Select report. Add report selected into list report will delete on future and add all report same day as selected
     * report
     *
     * @param report the report
     * @param config the config
     */
    public void toggleSelectionReport(final Daily_report report, final Notification_config config) {
        this.getAllReportInDashBoard(config);
        if (report.isSelected()) {
            // Add selected report to list will delete on future
            this.listReportInDashBoardWillDelete.add(report);
            // Selecte all report same day as selected report
            for (Daily_report rpt : this.listReportInDashBoard) {
                if (!report.equals(rpt) && (report.getDai_work_date().compareTo(rpt.getDai_work_date()) == 0)) {
                    rpt.setSelected(true);
                    this.listReportInDashBoardWillDelete.add(rpt);
                }
            }
        }
        else {
            if (this.listReportInDashBoardWillDelete.contains(report)) {
                this.listReportInDashBoardWillDelete.remove(report);
            }
        }

        this.checkSelectAllReportOfConfig();
    }

    /**
     * When user check or uncheck a checkbox, if currently, select all report is available and user uncheck a record in
     * this list report, set select all is false.
     */
    private void checkSelectAllReportOfConfig() {
        for (Notification_config config : this.dashBoardNotification) {
            for (Daily_report report : this.getAllReportInDashBoard(config)) {
                if (!this.listReportInDashBoardWillDelete.contains(report)) {
                    config.setSelectedAll(false);
                    break;
                }
            }
        }
    }

    /**
     * If select all report of config, remove some report of config have in list report will delete on future.
     *
     * @param notification the notification
     */
    public void toggleSelectionAllReport(final Notification_config notification) {
        this.getAllReportInDashBoard(notification);
        if (notification.isSelectedAll()) {
            for (final Daily_report report : this.listReportInDashBoard) {
                if (!this.listReportInDashBoardWillDelete.contains(report)) {
                    this.listReportInDashBoardWillDelete.add(report);
                }
            }
            this.setSelectedForList(true, this.listReportInDashBoard);
        }
        else {
            for (final Daily_report report : this.listReportInDashBoard) {
                if (this.listReportInDashBoardWillDelete.contains(report)) {
                    this.listReportInDashBoardWillDelete.remove(report);
                }
            }
            this.setSelectedForList(false, this.listReportInDashBoard);
        }
    }

    /**
     * Set selected for target list.
     *
     * @param selected the selected
     * @param reports the reports
     */
    private void setSelectedForList(final boolean selected, final List<Daily_report> reports) {
        for (Daily_report report : reports) {
            report.setSelected(selected);
        }
    }

    /**
     * Mark as read all notification.
     */
    public void markAllAsRead() {
        // Reset list contain report mark as read
        this.listReportInDashBoardWillDelete = new ArrayList<>();
        for (Notification_config config : this.dashBoardNotification) {
            for (Daily_report report : this.getAllReportInDashBoard(config)) {
                this.listReportInDashBoardWillDelete.add(report);
            }
        }
        this.markAsReadNotificationData();
        this.init();
    }

    /**
     * Mark as read notification.
     */
    public void markAsReadNotificationData() {
        // Haven't a notification, stop method
        if (CollectionUtils.isEmpty(this.dashBoardNotification)) {
            return;
        }

        if (CollectionUtils.isEmpty(this.listReportInDashBoardWillDelete)) {
            ErrorMessage.daily_005_you_must_select_atleast_1_item().show();
            return;
        }

        final ServiceResult<Daily_report> deletedNotificationData = this.notifyService
                .markAsReadNotificationData(this.listReportInDashBoardWillDelete, this.userCredential.getUserCode());

        if (deletedNotificationData.isSuccessful()) {
            this.cleanStage();
            this.init();
            InfoMessage.notification_001_mark_as_read_successfully().show();
        }
        else {
            deletedNotificationData.showMessages(FacesContext.getCurrentInstance());
        }
    }

    /**
     * Clean stage.
     */
    private void cleanStage() {
        this.listReportInDashBoard = null;
        this.listReportInDashBoardWillDelete = new ArrayList<>();
    }

    /**
     * Delete notification data.
     *
     * @param report the report
     */
    public void deleteNotificationData(final Daily_report report) {
        Notification_item item = this.notifyService.deleteNotificationData(report, this.userCredential.getUserCode());
        this.notifyService.deleteNotificationItem(item);
        this.init();
    }

    /**
     * Gets the all notification.
     *
     * @return the all notification
     */
    public List<Notification_config> getAllNotification() {
        if (this.listNotification == null) {
            return this.notifyService.getListNotificationConfig(this.userCredential.getUserCode());
        }
        return this.listNotification;

    }

    /**
     * Gets the list report in dash board.
     *
     * @return the list report in dash board
     */
    public List<Daily_report> getListReportInDashBoard() {
        return this.listReportInDashBoard;
    }

    /**
     * Sets the list report in dash board.
     *
     * @param listReportInDashBoard the new list report in dash board
     */
    public void setListReportInDashBoard(final List<Daily_report> listReportInDashBoard) {
        this.listReportInDashBoard = listReportInDashBoard;
    }

    /**
     * Get all report when show notification in dashboard.
     *
     * @param currentNotificationConfig the current notification config
     * @return the all report in dash board
     */
    public List<Daily_report> getAllReportInDashBoard(final Notification_config currentNotificationConfig) {
        if (currentNotificationConfig != null) {
            if (this.dashBoardNotification != null) {
                this.listReportInDashBoard = this.notifyService.getListReportByNotification(currentNotificationConfig);
                return this.listReportInDashBoard;
            }
        }
        return this.listReportInDashBoard;
    }

    /**
     * Gets the employee name.
     *
     * @param empCode the emp_code
     * @return the employee name
     */
    public Employee_mst getEmployeeByCode(final int empCode) {
        if (empCode == 0) {
            return null;
        }
        return this.notifyService.getEmployeeByCode(empCode);
    }

    /**
     * Gets the num row noti unread.
     *
     * @param notificationConfig the notification config
     * @return the num row noti unread
     */
    public Long getNumRowNotiUnread(final Notification_config notificationConfig) {
        return this.notifyService.getNumRowNotiUnread(notificationConfig);
    }

    /**
     * Gets the selected address.
     *
     * @return the selected address
     */
    public String getSelectedAddress() {
        return this.selectedAddress;
    }

    /**
     * Sets the selected address.
     *
     * @param selectedAddress the new selected address
     */
    public void setSelectedAddress(final String selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    /**
     * Gets the all branch.
     *
     * @return the all branch
     */
    public List<Addresspoint_mst> getAllBranch() {
        return this.allBranch;
    }

    /**
     * Sets the all branch.
     *
     * @param allBranch the new all branch
     */
    public void setAllBranch(final List<Addresspoint_mst> allBranch) {
        this.allBranch = allBranch;
    }

    /**
     * Gets the selected employee.
     *
     * @return the selected employee
     */
    public int getSelectedEmployee() {
        return this.selectedEmployee;
    }

    /**
     * Sets the selected employee.
     *
     * @param selectedEmployee the new selected employee
     */
    public void setSelectedEmployee(final int selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    /**
     * Gets the selected position.
     *
     * @return the selected position
     */
    public int getSelectedPosition() {
        return this.selectedPosition;
    }

    /**
     * Sets the selected position.
     *
     * @param selectedPosition the new selected position
     */
    public void setSelectedPosition(final int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    /**
     * Gets the all position.
     *
     * @return the all position
     */
    public List<Position_mst> getAllPosition() {
        return this.allPosition;
    }

    /**
     * Sets the all position.
     *
     * @param allPosition the new all position
     */
    public void setAllPosition(final List<Position_mst> allPosition) {
        this.allPosition = allPosition;
    }

    /**
     * Gets the list notification.
     *
     * @return the list notification
     */
    public List<Notification_config> getListNotification() {
        return this.listNotification;
    }

    /**
     * Sets the list notification.
     *
     * @param listNotification the new list notification
     */
    public void setListNotification(final List<Notification_config> listNotification) {
        this.listNotification = listNotification;
    }

    /**
     * Gets the dash board notification.
     *
     * @return the dash board notification
     */
    public List<Notification_config> getDashBoardNotification() {
        return this.dashBoardNotification;
    }

    /**
     * Sets the dash board notification.
     *
     * @param dashBoardNotification the new dash board notification
     */
    public void setDashBoardNotification(final List<Notification_config> dashBoardNotification) {
        this.dashBoardNotification = dashBoardNotification;
    }

    /**
     * Checks if is checks for notification selected.
     *
     * @return true, if is checks for notification selected
     */
    public boolean isHasNotificationSelected() {
        return this.hasNotificationSelected;
    }

    /**
     * Sets the checks for notification selected.
     *
     * @param hasNotificationSelected the new checks for notification selected
     */
    public void setHasNotificationSelected(final boolean hasNotificationSelected) {
        this.hasNotificationSelected = hasNotificationSelected;
    }

    /**
     * Checks if is checks for selected menu items.
     *
     * @return true, if is checks for selected menu items
     */
    public boolean isHaedMenuItems() {
        return (StringUtils.isNotEmpty(this.selectedAddress) && (this.selectedEmployee != 0));
    }

    /**
     * Gets the time no report.
     *
     * @return the time no report
     */
    public Date getTimeNoReport() {
        return this.timeNoReport;
    }

    /**
     * Sets the time no report.
     *
     * @param timeNoReport the new time no report
     */
    public void setTimeNoReport(final Date timeNoReport) {
        this.timeNoReport = timeNoReport;
    }

    /**
     * Gets the time new report.
     *
     * @return the time new report
     */
    public Date getTimeNewReport() {
        return this.timeNewReport;
    }

    /**
     * Sets the time new report.
     *
     * @param timeNewReport the new time new report
     */
    public void setTimeNewReport(final Date timeNewReport) {
        this.timeNewReport = timeNewReport;
    }

    /**
     * Gets the selected notification config.
     *
     * @return the selected notification config
     */
    public Notification_config getSelectedNotificationConfig() {
        return this.selectedNotificationConfig;
    }

    /**
     * Sets the selected notification config.
     *
     * @param selectedNotificationConfig the new selected notification config
     */
    public void setSelectedNotificationConfig(final Notification_config selectedNotificationConfig) {
        this.selectedNotificationConfig = selectedNotificationConfig;
    }

    /**
     * Open report.
     *
     * @param report the report
     */
    public void openReport(final Daily_report report) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put(ScreenContext.OWNER_NAME, report.getEmployee_mst().getEmp_name());
        this.screenBean.switchScreen(ScreenContext.DAILY_REPORT_REGISTER, false, attributes);
    }

    /**
     * Gets the list report in dash board will delete.
     *
     * @return the list report in dash board will delete
     */
    public List<Daily_report> getListReportInDashBoardWillDelete() {
        return this.listReportInDashBoardWillDelete;
    }

    /**
     * Sets the list report in dash board will delete.
     *
     * @param listReportInDashBoardWillDelete the new list report in dash board will delete
     */
    public void setListReportInDashBoardWillDelete(final List<Daily_report> listReportInDashBoardWillDelete) {
        this.listReportInDashBoardWillDelete = listReportInDashBoardWillDelete;
    }

    /**
     * Checks if is disable button mark as read.
     *
     * @return true, if is disable button mark as read
     */
    public boolean isDisableButtonMarkAsRead() {
        return CollectionUtils.isEmpty(this.listReportInDashBoardWillDelete);
    }

    /**
     * Checks if is disable button mark all as read.
     *
     * @return true, if is disable button mark all as read
     */
    public boolean isDisableButtonMarkAllAsRead() {
        return CollectionUtils.isEmpty(this.dashBoardNotification);
    }

    /**
     * Sets the selected report by user.
     *
     * @param report the new selected report by user
     */
    public void setSelectedReportByUser(final Daily_report report) {
        this.reportSelectedByUser = report;
    }

    /**
     * Gets the report selected by user.
     *
     * @return the report selected by user
     */
    public Daily_report getReportSelectedByUser() {
        return this.reportSelectedByUser;
    }

    /**
     * Sets the report selected by user.
     *
     * @param reportSelectedByUser the new report selected by user
     */
    public void setReportSelectedByUser(final Daily_report reportSelectedByUser) {
        this.reportSelectedByUser = reportSelectedByUser;
    }

    /**
     * Checks if is retired employee.
     *
     * @param empCode the emp code
     * @return true, if is retired employee
     */
    public boolean isRetiredEmployee(final int empCode) {
        Employee_mst employee = this.employeeManagementService.findEmployeeByEmpCode(empCode);
        return (employee.getEmp_condi_code() != null) && (
                AuthenticationConstants.EmployeeStatus.RETIRED.equalsIgnoreCase(employee.getEmp_condi_code().toString())
                        || employee.getEmp_delete_flg());
    }
}
