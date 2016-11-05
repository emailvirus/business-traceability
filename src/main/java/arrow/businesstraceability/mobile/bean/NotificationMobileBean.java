package arrow.businesstraceability.mobile.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.constant.MobilePageId;
import arrow.businesstraceability.control.bean.MobileScreenFlowBean;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.helper.MobileContext;
import arrow.businesstraceability.control.helper.ServiceResult;
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
import arrow.framework.util.collections.CollectionUtils;


/**
 * The Class NotificationMobileBean.
 *
 * @author Tai
 */

@Named
@ViewScoped
public class NotificationMobileBean extends AbstractBean {

    /** The current user. */
    @Inject
    UserCredential currentUser;

    @Inject
    MobileScreenFlowBean mobileScreenFlowBean;

    @Inject
    private NotificationService notifyService;

    @Inject
    private FacesContext facesContext;

    // For Notification configuration
    private List<Addresspoint_mst> allBranch;

    private List<Employee_mst> allEmployee;

    private List<Position_mst> allPosition;

    private List<Notification_config> listNotification;

    private String selectedAddress = "";

    private int selectedEmployee;

    private int selectedPosition;

    // Beans for DashBoard
    private List<Notification_config> dashBoardNotification;

    private List<Daily_report> listReportInDashBoard;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        if (this.allBranch == null) {
            this.allBranch = this.notifyService.getListAddrContraintEmployee();
        }

        this.listNotification = this.notifyService.getListNotificationConfig(this.currentUser.getUserCode());
        this.dashBoardNotification =
                this.notifyService.getListNotificationConfigForDashBoard(this.currentUser.getUserCode());

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
        this.allEmployee =
                this.notifyService.getEmployeeByAddrAndPos(this.selectedAddress, this.selectedPosition,
                        this.currentUser.getUserCode());
    }

    /**
     * Render list position.
     */
    public void renderListPosition() {
        this.allPosition = this.notifyService.getPositionByAddr(this.selectedAddress, this.currentUser.getUserCode());
        this.allEmployee =
                this.notifyService.getEmployeeByAddrAndPos(this.selectedAddress, this.selectedPosition,
                        this.currentUser.getUserCode());
    }

    /**
     * Adds the new notification.
     */
    public void addNewNotification() {
        final ServiceResult<Notification_config> results = this.notifyService.saveNotification(this);
        if (results.isSuccessful()) {
            this.listNotification = this.notifyService.getListNotificationConfig(this.currentUser.getUserCode());
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
        if ((this.listNotification == null) || CollectionUtils.isEmpty(this.listNotification)) {
            return;
        }
        // store notification is selected.
        final List<Notification_config> selectedNotifications = new ArrayList<Notification_config>();
        for (final Notification_config report : this.listNotification) {
            if (report.isSelected()) {
                selectedNotifications.add(report);
                break;
            }
        }
        if (selectedNotifications.size() == 0) {
            ErrorMessage.daily_005_you_must_select_atleast_1_item().show();
            return;
        }

        final ServiceResult<Notification_config> deletedReport =
                this.notifyService.deleteNotifications(selectedNotifications.get(0));

        if (deletedReport.isSuccessful()) {
            // Reset table daily report and reset form add new report
            this.listNotification = this.notifyService.getListNotificationConfig(this.currentUser.getUserCode());

            InfoMessage.daily_002_delete_successfully().show();
        }
        else {
            deletedReport.showMessages(FacesContext.getCurrentInstance());
        }

    }

    /**
     * Delete notification data.
     *
     * @param report the report
     */
    public void deleteNotificationData(final Daily_report report) {
        Notification_item item = this.notifyService.deleteNotificationData(report, this.currentUser.getUserCode());
        this.notifyService.deleteNotificationItem(item);
    }

    /**
     * Gets the all notification.
     *
     * @return the all notification
     */
    public List<Notification_config> getAllNotification() {
        if (this.listNotification == null) {
            return this.notifyService.getListNotificationConfig(this.currentUser.getUserCode());
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
     * Gets the employee by code.
     *
     * @param empCode the emp_code
     * @return the employee by code
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
     * Checks if is checks for selected menu items.
     *
     * @return true, if is checks for selected menu items
     */
    public boolean isHasSelectedMenuItems() {
        return (!this.selectedAddress.equalsIgnoreCase("")) && (this.selectedEmployee != 0);
    }

    /**
     * Mobile view report.
     *
     * @param currentReport the current report
     * @return the string
     */
    public String mobileViewReport(final Daily_report currentReport) {
        MobileContext currentContext = this.mobileScreenFlowBean.getCurrentContext();
        currentContext.getAttributes().put(MobileContext.CURRENT_REPORT, currentReport);
        return this.mobileScreenFlowBean.gotoPage(MobilePageId.EDIT_DAILY_REPORT);
    }

    /**
     * Mobile view dashboard.
     *
     * @return the string
     */
    public String mobileViewDashboard() {
        return this.mobileScreenFlowBean.gotoPage(MobilePageId.HOME);
    }

}
