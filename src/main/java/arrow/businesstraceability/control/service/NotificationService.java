package arrow.businesstraceability.control.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.persistence.PersistenceException;

import org.omnifaces.util.Faces;

import freemarker.template.TemplateException;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.constant.RegisterReportConstants;
import arrow.businesstraceability.control.bean.NotificationBean;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.mobile.bean.NotificationMobileBean;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Notification_config;
import arrow.businesstraceability.persistence.entity.Notification_data;
import arrow.businesstraceability.persistence.entity.Notification_item;
import arrow.businesstraceability.persistence.entity.Position_mst;
import arrow.businesstraceability.persistence.repository.Notification_configRepository;
import arrow.businesstraceability.persistence.repository.Notification_dataRepository;
import arrow.businesstraceability.persistence.repository.Notification_itemRepository;
import arrow.businesstraceability.util.mail.EmailHelper;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.util.DateUtils;
import arrow.framework.util.collections.CollectionUtils;

/**
 * The Class NotificationService.
 */
@Named
@ConversationScoped
public class NotificationService extends AbstractService {

    /** The user credential. */
    @Inject
    private UserCredential userCredential;

    @Inject
    private Notification_configRepository notificationConfigRepo;

    @Inject
    private Notification_dataRepository notificationDataRepo;

    @Inject
    private Notification_itemRepository notificationItemRepo;

    /**
     * Get list Employee by Address_point and Position_mst with condiction : the user login is not show data.
     * 15/09/2015: Taind - not load employee does deleted
     *
     * @param selectAddressPoint the select address point
     * @param selectedPosition the selected position
     * @param currentEmployeeCode the current_employee code
     * @return the employee by addr and pos
     */
    public List<Employee_mst> getEmployeeByAddrAndPos(final String selectAddressPoint, final int selectedPosition,
        final int currentEmployeeCode) {
        final StringBuilder sb = new StringBuilder();
        // Check if select All Position
        sb.append("SELECT e FROM Employee_mst e WHERE ");
        sb.append("e.emp_adpcode = '" + selectAddressPoint).append("' ");
        if (selectedPosition != 0) {
            sb.append("AND e.emp_poscode = " + selectedPosition).append(" ");
        }
        sb.append("AND (e.emp_condi_code = '0' OR e.emp_condi_code = null) ");
        sb.append("AND e.emp_delete_flg = false ");
        sb.append("AND e.emp_code <> " + currentEmployeeCode);
        return super.emMain.createQuery(sb.toString(), Employee_mst.class).getResultList();
    }

    /**
     * Gets the list addr contraint employee.
     *
     * @return the list addr contraint employee
     */
    public List<Addresspoint_mst> getListAddrContraintEmployee() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT ad FROM Employee_mst em, Addresspoint_mst ad WHERE ");
        sb.append("em.emp_adpcode = ad.adp_code");
        return super.emMain.createQuery(sb.toString(), Addresspoint_mst.class).getResultList();
    }

    /**
     * Gets the position by addr.
     *
     * @param selectedAddress the selected address
     * @param currentEmployeeCode the current_employee code
     * @return the position by addr
     */
    public List<Position_mst> getPositionByAddr(final String selectedAddress, final int currentEmployeeCode) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT po FROM Employee_mst em, Position_mst po WHERE ");
        sb.append("em.emp_poscode = po.pos_code").append(" AND ");
        sb.append("em.emp_adpcode = '" + selectedAddress).append("' ");
        sb.append(" AND em.emp_code <> " + currentEmployeeCode);
        return super.emMain.createQuery(sb.toString(), Position_mst.class).getResultList();
    }

    /**
     * Save notification into NOTIFICATION_CONFIG table.
     *
     * @param notificationBean the notification bean
     * @param currentEmployeeCode the current_employee code
     * @return the service result
     */
    public ServiceResult<Notification_config> saveNotification(final NotificationBean notificationBean,
        final Employee_mst currentEmployeeCode) {
        final List<Message> errors = new ArrayList<>();

        final Employee_mst employeeManager = this.userCredential.getUserEmployee();
        final List<Notification_config> listNotifications =
            this.getListNotificationConfig(currentEmployeeCode.getEmp_code());
        List<Employee_mst> listEmployeeSelected = notificationBean.getAllEmployee();
        // Check if the employee have saved that match the employee have selected from the form =>
        // save
        // fail => return errors
        for (final Notification_config notificationcf : listNotifications) {
            if (notificationBean.getSelectedEmployee() == 1) {
                listEmployeeSelected.remove(this.getEmployeeByTargetEmp(notificationcf));
            } else if (notificationcf.getNc_target_employee() == notificationBean.getSelectedEmployee()) {
                errors.add(ErrorMessage.notification_002_employee_exited());
                return new ServiceResult<Notification_config>(false, errors);
            }
        }
        // Employee_mst employee = super.emMain.find(Employee_mst.class,
        // notificationBean.getSelectedEmployee());
        if ((notificationBean.getSelectedEmployee() == 1) && (listEmployeeSelected.size() > 0)) {
            Notification_config notificationForTest = new Notification_config();
            for (final Employee_mst emp : listEmployeeSelected) {
                Notification_config newNotification = new Notification_config(employeeManager, emp.getEmp_code());
                newNotification.setNc_type(RegisterReportConstants.NOTIFICATION_CONFIG_TYPE_DAILY);
                newNotification.setNc_enable(true);
                try {
                    super.emMain.persist(newNotification);
                    notificationForTest = newNotification;
                } catch (final PersistenceException e) {
                    super.log.debug("EXCEPTION WHEN SAVE NOTIFICATION CONFIG", e);
                    errors.add(ErrorMessage.daily_001_save_unsuccessfully());
                    return new ServiceResult<Notification_config>(false, newNotification, errors);
                }
            }
            super.emMain.flush();
            return new ServiceResult<Notification_config>(true, notificationForTest);
        } else if (notificationBean.getSelectedEmployee() != 1) {
            // Case : if master is not select all employee
            final Notification_config newNotification =
                new Notification_config(employeeManager, notificationBean.getSelectedEmployee());
            newNotification.setNc_type(RegisterReportConstants.NOTIFICATION_CONFIG_TYPE_DAILY);
            newNotification.setNc_enable(true);
            try {
                super.emMain.persist(newNotification);
                super.emMain.flush();
                return new ServiceResult<Notification_config>(true, newNotification);
            } catch (final PersistenceException e) {
                super.log.debug("EXCEPTION WHEN DELETE NOTIFICATION SAVE NOTIFICATION", e);
                errors.add(ErrorMessage.daily_001_save_unsuccessfully());
                return new ServiceResult<Notification_config>(false, newNotification, errors);
            }
        } else {
            // If master select all but all employee have added to follow
            errors.add(ErrorMessage.notification_002_employee_exited());
            return new ServiceResult<Notification_config>(false, errors);
        }
    }

    /**
     * Save notification into NOTIFICATION_CONFIG table.
     *
     * @param notificationBean the notification bean
     * @return the service result
     */
    public ServiceResult<Notification_config> saveNotification(final NotificationMobileBean notificationBean) {
        final List<Message> errors = new ArrayList<>();

        final Employee_mst master = this.userCredential.getUserEmployee();
        final List<Notification_config> listNotifications = this.getListNotificationConfig(master.getEmp_code());
        List<Employee_mst> listEmployeeSelected = notificationBean.getAllEmployee();
        // Check if the employee have saved that match the employee have selected from the form =>
        // save
        // fail => return errors
        for (final Notification_config notificationcf : listNotifications) {
            if (notificationBean.getSelectedEmployee() == 1) {
                listEmployeeSelected.remove(this.getEmployeeByTargetEmp(notificationcf));
            } else if (notificationcf.getNc_target_employee() == notificationBean.getSelectedEmployee()) {
                errors.add(ErrorMessage.notification_002_employee_exited());
                return new ServiceResult<Notification_config>(false, errors);
            }
        }
        // Employee_mst employee = super.emMain.find(Employee_mst.class,
        // notificationBean.getSelectedEmployee());
        if ((notificationBean.getSelectedEmployee() == 1) && (listEmployeeSelected.size() > 0)) {
            Notification_config notificationForTest = new Notification_config();
            for (final Employee_mst emp : listEmployeeSelected) {
                Notification_config newNotification = new Notification_config(master, emp.getEmp_code());
                newNotification.setNc_type(RegisterReportConstants.NOTIFICATION_CONFIG_TYPE_DAILY);
                newNotification.setNc_enable(true);
                try {
                    super.emMain.persist(newNotification);
                    notificationForTest = newNotification;
                } catch (final PersistenceException e) {
                    super.log.debug("EXCEPTION WHEN PERSIST (ADD NEW NOTIFICATION_CONFIG) - SELECT ALL", e);
                    errors.add(ErrorMessage.daily_001_save_unsuccessfully());
                    return new ServiceResult<Notification_config>(false, newNotification, errors);
                }
            }
            super.emMain.flush();
            return new ServiceResult<Notification_config>(true, notificationForTest);
        } else if (notificationBean.getSelectedEmployee() != 1) {
            // Case : if master is not select all employee
            final Notification_config newNotification =
                new Notification_config(master, notificationBean.getSelectedEmployee());
            newNotification.setNc_type(RegisterReportConstants.NOTIFICATION_CONFIG_TYPE_DAILY);
            newNotification.setNc_enable(true);
            try {
                super.emMain.persist(newNotification);
                super.emMain.flush();
                return new ServiceResult<Notification_config>(true, newNotification);
            } catch (final PersistenceException e) {
                super.log.debug("EXCEPTION WHEN PERSIST (ADD NEW NOTIFICATION_CONFIG) - SELECT NOT ALL", e);
                errors.add(ErrorMessage.daily_001_save_unsuccessfully());
                return new ServiceResult<Notification_config>(false, newNotification, errors);
            }
        } else {
            // If master select all but all employee have added to follow
            errors.add(ErrorMessage.notification_002_employee_exited());
            return new ServiceResult<Notification_config>(false, errors);
        }
    }

    /**
     * Gets the employee by target emp.
     *
     * @param config the config
     * @return the employee by target emp
     */
    public Employee_mst getEmployeeByTargetEmp(final Notification_config config) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT em FROM Employee_mst em WHERE ");
        sb.append("em.emp_code = " + config.getNc_target_employee());
        sb.append(" AND (em.emp_condi_code = '0' OR em.emp_condi_code = null) ");
        return super.emMain.createQuery(sb.toString(), Employee_mst.class).getSingleResult();
    }

    /**
     * Get list notification from NOTIFICATION_CONFIG table for CONFIG with condition : the user login is not show data.
     *
     * @param current_employeeCode the current_employee code
     * @return the list notification config
     */
    public List<Notification_config> getListNotificationConfig(final int current_employeeCode) {
        final ArrowQuery<Notification_config> query = new ArrowQuery<>(super.emMain);
        query.select("e").from("Notification_config e");
        query.where("e.nc_employee_code=?", current_employeeCode);
        query.orderBy("e.last_updated_at DESC");
        return query.getResultList();
    }

    /**
     * Get list notification from NOTIFICATION_CONFIG table for DASHBOARD with condition : the user login is not show
     * data.
     *
     * @param currentEmployeeCode the current_employee code
     * @return the list notification config for dash board
     */
    public List<Notification_config> getListNotificationConfigForDashBoard(final int currentEmployeeCode) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT e FROM Notification_config e, Notification_data d").append(" WHERE ");
        sb.append("e.nc_employee_code=d.nd_employee_code").append(" AND ");
        sb.append("e.nc_target_employee=d.nd_target_employee").append(" AND ");
        sb.append("e.nc_employee_code=" + currentEmployeeCode);
        return super.emMain.createQuery(sb.toString(), Notification_config.class).getResultList();
    }

    /**
     * Get Employee Name.
     *
     * @param empCode the emp_code
     * @return the employee name
     */
    public Employee_mst getEmployeeByCode(final int empCode) {
        final ArrowQuery<Employee_mst> query = new ArrowQuery<>(super.emMain);
        query.select("e").from("Employee_mst e");
        query.where("e.emp_code=?", empCode);
        return query.getSingleResult();

    }

    /**
     * Delete notification then delete notification_data and notification_item.
     *
     * @param selectedNotification the selected notification
     * @return the service result
     */
    public ServiceResult<Notification_config> deleteNotifications(final Notification_config selectedNotification) {
        try {
            Notification_config oldConfig =
                this.notificationConfigRepo.findNotificationConfigByEmpCodeAndTargetCode(
                    selectedNotification.getNc_employee_code(), selectedNotification.getNc_target_employee())
                    .getAnyResult();
            if (oldConfig == null) {
                return new ServiceResult<>(false, ErrorMessage.daily_002_delete_unsucessfully());
            }

            this.notificationConfigRepo.remove(oldConfig);

            List<Notification_data> listData =
                this.notificationDataRepo.findNotificationDataByEmpCodeAndTargetCode(
                    selectedNotification.getNc_employee_code(), selectedNotification.getNc_target_employee())
                    .getResultList();
            if (CollectionUtils.isNotEmpty(listData)) {
                this.deleteListNotificationData(listData);
            }

        } catch (final PersistenceException e) {
            super.log.debug("EXCEPTION WHEN DELETE NOTIFICATION CONFIG", e);
            final List<Message> errors = new ArrayList<Message>();
            errors.add(ErrorMessage.daily_002_delete_unsucessfully());
            return new ServiceResult<>(false, errors);
        }
        return new ServiceResult<Notification_config>(true, selectedNotification);
    }

    /**
     * Delete list notification data.
     *
     * @param listData the list data
     */
    private void deleteListNotificationData(final List<Notification_data> listData) {
        for (Notification_data data : listData) {
            this.notificationDataRepo.remove(data);
            List<Notification_data> listDataItemKey =
                this.notificationDataRepo.findNotificationDataByItemKey(data.getNd_item_key()).getResultList();
            if (listDataItemKey.size() > 1) {
                continue;
            }

            Notification_item item =
                this.notificationItemRepo.findNotificationItemByItemKey(data.getNd_item_key()).getAnyResult();
            if (item != null) {
                this.notificationItemRepo.remove(item);
            }
        }
    }

    /**
     * GET data from Notification_config with condiction nc_target_employee.
     *
     * @param employeeCode the employee code
     * @return the emp exit on noti config
     */
    public List<Notification_config> getEmpExitOnNotiConfig(final int employeeCode) {
        final ArrowQuery<Notification_config> query = new ArrowQuery<>(super.emMain);
        query.select("e").from("Notification_config e");
        query.where("e.nc_target_employee=?", employeeCode);
        return query.getResultList();
    }

    /**
     * Gets the num row noti unread.
     *
     * @param notiConfig the noti config
     * @return the num row noti unread
     */
    public Long getNumRowNotiUnread(final Notification_config notiConfig) {
        Long numRows = (long) 0;
        if (notiConfig == null) {
            return numRows;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(
            "SELECT COUNT(e.nd_item_key) FROM Notification_data e WHERE e.nd_target_employee = "
                + notiConfig.getNc_target_employee()).append(" ");
        sb.append("AND e.nd_employee_code = " + notiConfig.getNc_employee_code());
        numRows = super.emMain.createQuery(sb.toString(), Long.class).getSingleResult();
        return numRows;
    }

    /**
     * Get list of daily report for DashBoard when master view notification.
     *
     * @param dashBoardNotification the dash board notification
     * @return List daily report
     */
    public List<Daily_report> getListReportByNotification(final Notification_config dashBoardNotification) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT e FROM Daily_report e, Notification_data nd , Notification_item ni ").append(" WHERE ");
        sb.append("nd.nd_item_key=ni.ni_item_key").append(" AND ");
        sb.append("ni.ni_employee_code=e.dai_employee_code").append(" AND ");
        sb.append("ni.ni_point_code=e.dai_point_code").append(" AND ");
        sb.append("ni.ni_work_date=e.dai_work_date").append(" AND ");
        sb.append("ni.ni_work_stime=e.dai_work_stime").append(" AND ");
        sb.append("ni.ni_work_etime=e.dai_work_etime").append(" AND ");
        sb.append("nd.nd_target_employee=" + dashBoardNotification.getNc_target_employee()).append(" AND ");
        sb.append("nd.nd_employee_code=" + dashBoardNotification.getNc_employee_code());
        List<Daily_report> listReport = super.emMain.createQuery(sb.toString(), Daily_report.class).getResultList();
        return listReport;
    }

    /**
     * Delete notification from Notification_data when master viewed report or mar as read.
     *
     * @param report the report
     * @param currentEmployeeCode the current_employee code
     * @return the notification_item
     */
    public Notification_item deleteNotificationData(final Daily_report report, final int currentEmployeeCode) {
        // Get the item_key of notification_item
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT ni FROM Notification_item ni").append(" WHERE ");
        sb.append("ni.ni_point_code='" + report.getDai_point_code()).append("' AND ");
        sb.append("ni.ni_work_date='" + DateUtils.formatDate(report.getDai_work_date())).append("' AND ");
        sb.append("ni.ni_work_stime='" + DateUtils.formatTime(report.getDai_work_stime())).append("' AND ");
        sb.append("ni.ni_work_etime='" + DateUtils.formatTime(report.getDai_work_etime())).append("' AND ");
        sb.append("ni.ni_employee_code=" + report.getDai_employee_code());

        List<Notification_item> notificationItem =
            super.emMain.createQuery(sb.toString(), Notification_item.class).getResultList();

        // Delete notification from Notification_data if have ni_item_key and nd_target_employee and
        // current master (nd_employee_code)
        if (notificationItem.size() > 0) {
            final ArrowQuery<Notification_data> query = new ArrowQuery<>(super.emMain);
            query.select("nd").from("Notification_data nd");
            query.where("nd.nd_item_key=?", notificationItem.get(0).getNi_item_key());
            query.where("nd.nd_target_employee=?", report.getDai_employee_code());
            query.where("nd.nd_employee_code=?", currentEmployeeCode);
            final Notification_data notificationData = query.getSingleResult();
            try {
                this.emMain.remove(notificationData);
            } catch (final PersistenceException e) {
                this.log.debug("EXCEPTION WHEN DELETE NOTIFICATION DATA", e);
                return null;
            }
        }
        return notificationItem.get(0);

    }

    /**
     * Delete data in notification_data and notification_item when Master delete notification_config (no follow
     * employee).
     *
     * @param dataConfig the data config
     */
    public void deleteNotificationData(final Notification_config dataConfig) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT nd FROM Notification_data nd").append(" WHERE ");
        sb.append("nd.nd_employee_code=" + dataConfig.getNc_employee_code()).append(" AND ");
        sb.append("nd.nd_target_employee=" + dataConfig.getNc_target_employee());
        final List<Notification_data> notificationData =
            super.emMain.createQuery(sb.toString(), Notification_data.class).getResultList();
        if (notificationData.size() > 0) {
            for (final Notification_data notificationdt : notificationData) {
                // Delete row in notification_data
                try {
                    super.emMain.remove(notificationdt);

                    // Check if nd_item_key is more than 1 in notification_item (more than 1 master
                    // add 1
                    // employee to follow)
                    final StringBuilder sbCheckItemKey = new StringBuilder();
                    sbCheckItemKey.append("SELECT nd FROM Notification_data nd").append(" WHERE ");
                    sbCheckItemKey.append("nd.nd_item_key=" + notificationdt.getNd_item_key());
                    final List<Notification_data> numOfNotificationData =
                        super.emMain.createQuery(sbCheckItemKey.toString(), Notification_data.class).getResultList();
                    if (numOfNotificationData.size() > 1) {
                        // If have more than 1 item key that have more than 1 master is following
                        // this employee
                        // so
                        // DO NOT DELETE row in notification_item
                        continue;
                    }

                    final Notification_item notificationItem = Notification_item.find(notificationdt.getNd_item_key());
                    super.emMain.remove(notificationItem);
                } catch (final PersistenceException e) {
                    super.log.debug("EXCEPTION WHEN DELETE NOTIFICATION DATA", e);
                }
            }
        }
    }

    /**
     * Delete notification from Notification_data when employee delete report.
     *
     * @param report the report
     */
    public void deleteNotificationByEmp(final Daily_report report) {
        // Get the item_key of notification_item
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT ni FROM Notification_item ni").append(" WHERE ");
        sb.append("ni.ni_point_code='" + report.getDai_point_code()).append("' AND ");
        sb.append("ni.ni_work_date='" + DateUtils.formatDate(report.getDai_work_date())).append("' AND ");
        sb.append("ni.ni_work_stime='" + DateUtils.formatTime(report.getDai_work_stime())).append("' AND ");
        sb.append("ni.ni_work_etime='" + DateUtils.formatTime(report.getDai_work_etime())).append("' AND ");
        sb.append("ni.ni_employee_code=" + report.getDai_employee_code());
        List<Notification_item> notificationItem =
            super.emMain.createQuery(sb.toString(), Notification_item.class).getResultList();
        // this condition to check if master has viewed or mark as read then data is clean so do
        // nothing
        if (notificationItem.size() == 0) {
            return;
        }

        // Get data the Notification_data
        final StringBuilder sb1 = new StringBuilder();
        sb1.append("SELECT nd FROM Notification_data nd, Notification_item ni").append(" WHERE ");
        sb1.append("nd.nd_item_key=ni.ni_item_key").append(" AND ");
        sb1.append("nd.nd_item_key=" + notificationItem.get(0).getNi_item_key());
        final List<Notification_data> listNotificationData =
            super.emMain.createQuery(sb1.toString(), Notification_data.class).getResultList();

        // Delete notification from Notification_data if have ni_item_key and nd_target_employee and
        // current master (nd_employee_code)
        for (final Notification_data notification : listNotificationData) {
            final ArrowQuery<Notification_data> query = new ArrowQuery<>(super.emMain);
            query.select("nd").from("Notification_data nd");
            query.where("nd.nd_item_key=?", notificationItem.get(0).getNi_item_key());
            query.where("nd.nd_target_employee=?", report.getDai_employee_code());
            query.where("nd.nd_employee_code=?", notification.getNd_employee_code());
            final Notification_data notificationData = query.getSingleResult();
            try {
                this.emMain.remove(notificationData);
            } catch (final PersistenceException e) {
                this.log.debug("EXCEPTION WHEN DELETE NOTIFICATION DATA BY EMPLOYEE", e);
                return;
            }
        }
        this.deleteNotificationItem(notificationItem.get(0));

    }

    /**
     * Mark as read notification data.
     *
     * @param selectedReports the selected reports
     * @param currentEmployeeCode the current_employee code
     * @return the service result
     */
    public ServiceResult<Daily_report> markAsReadNotificationData(final List<Daily_report> selectedReports,
        final int currentEmployeeCode) {
        boolean temp = false;
        final List<Message> errors = new ArrayList<Message>();
        for (final Daily_report report : selectedReports) {
            temp = this.deleteNotificationItem(this.deleteNotificationData(report, currentEmployeeCode));
            if (!temp) {
                errors.add(ErrorMessage.notification_001_mark_as_read_unsuccessfully());
                return new ServiceResult<Daily_report>(false, errors);
            }
        }
        return new ServiceResult<Daily_report>(true);
    }

    /**
     * Delete notification item.
     *
     * @param notificationItem the notification item
     * @return true, if successful
     */
    public boolean deleteNotificationItem(final Notification_item notificationItem) {
        // Check to delete item from notification_item
        if (notificationItem == null) {
            return false;
        }
        final StringBuilder queryGetNumRows = new StringBuilder();
        queryGetNumRows.append("SELECT COUNT(e.nd_item_key) FROM Notification_data e, ")
            .append("Notification_item ni WHERE e.nd_item_key=ni.ni_item_key").append(" AND ");
        queryGetNumRows.append("ni.ni_item_key = " + notificationItem.getNi_item_key());
        final Long numRows = super.emMain.createQuery(queryGetNumRows.toString(), Long.class).getSingleResult();
        if (numRows == 0) {
            // Delete row in notification_item
            try {
                this.emMain.remove(notificationItem);
            } catch (final PersistenceException e) {
                this.log.debug("EXCEPTION WHEN DELETE NOTIFICATION ITEM", e);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for notification item.
     *
     * @param savedReport the saved report
     * @return true, if successful
     */
    public boolean hasNotificationItem(final Daily_report savedReport) {
        final StringBuilder sbCheck = new StringBuilder();
        sbCheck.append("SELECT ni FROM Notification_item ni").append(" WHERE ");
        sbCheck.append("ni.ni_point_code='" + savedReport.getDai_point_code()).append("' AND ");
        sbCheck.append("ni.ni_work_date='" + DateUtils.formatDate(savedReport.getDai_work_date())).append("' AND ");
        sbCheck.append("ni.ni_work_stime='" + DateUtils.formatTime(savedReport.getDai_work_stime())).append("' AND ");
        sbCheck.append("ni.ni_work_etime='" + DateUtils.formatTime(savedReport.getDai_work_etime())).append("' AND ");
        sbCheck.append("ni.ni_employee_code=" + savedReport.getDai_employee_code());
        final List<Notification_item> resultsCheck =
            super.emMain.createQuery(sbCheck.toString(), Notification_item.class).getResultList();
        return CollectionUtils.isNotEmpty(resultsCheck);
    }

    /**
     * Adds the new notification item.
     *
     * @param savedReport the saved report
     * @param notiConfig the noti config
     */
    public void addNewNotificationItem(final Daily_report savedReport, final List<Notification_config> notiConfig) {
        // Get the max id of Notification_item
        this.emMain.clear();
        Integer notificationItems =
            super.emMain.createQuery("SELECT MAX(e.ni_item_key) FROM Notification_item e", Integer.class)
                .getSingleResult();
        if (notificationItems != null) {
            notificationItems += 1;
        } else {
            notificationItems = 1;
        }
        // For Notification_item
        final Notification_item newNotificationItem = new Notification_item(notificationItems);
        newNotificationItem.setNi_type(RegisterReportConstants.NOTIFICATION_CONFIG_TYPE_DAILY);
        newNotificationItem.setNi_created_at(DateUtils.getCurrentDatetime());
        newNotificationItem.setNi_employee_code(savedReport.getDai_employee_code());
        newNotificationItem.setNi_work_date(savedReport.getDai_work_date());
        newNotificationItem.setNi_work_stime(savedReport.getDai_work_stime());
        newNotificationItem.setNi_work_etime(savedReport.getDai_work_etime());
        newNotificationItem.setNi_point_code(savedReport.getDai_point_code());
        super.emMain.persist(newNotificationItem);

        // For Notification_data
        for (final Notification_config notification : notiConfig) {
            final Notification_data newNotifitionData =
                new Notification_data(notification.getEmployee_mst(), notification.getNc_target_employee(),
                    newNotificationItem);
            newNotifitionData.setNd_type(RegisterReportConstants.NOTIFICATION_CONFIG_TYPE_DAILY);
            super.emMain.persist(newNotifitionData);
        }
        try {
            super.emMain.flush();
        } catch (final PersistenceException e) {
            super.log.debug("Error while create notification:", e);
        }
        super.emMain.clear();
    }

    /**
     * Process save reprot event.
     *
     * @param event the event
     */
    private void processSaveReprotEvent(final RegisterActivityEvent event) {
        final Daily_report savedReport = event.getSavedReport();
        final List<Notification_config> notiConfig = this.getEmpExitOnNotiConfig(savedReport.getDai_employee_code());
        if (CollectionUtils.isEmpty(notiConfig) || this.hasNotificationItem(savedReport)) {
            return;
        }

        this.addNewNotificationItem(savedReport, notiConfig);
    }

    /**
     * Process add new report event.
     *
     * @param event the event
     */
    private void processAddNewReportEvent(final RegisterActivityEvent event) {
        final Daily_report savedReport = event.getSavedReport();
        final List<Notification_config> notiConfig = this.getEmpExitOnNotiConfig(savedReport.getDai_employee_code());
        if (CollectionUtils.isEmpty(notiConfig)) {
            return;
        }
        this.addNewNotificationItem(savedReport, notiConfig);
    }

    /**
     * Observe event save notification data.
     *
     * @param event the event
     */
    public void observeEventSaveNotificationData(@Observes final RegisterActivityEvent event) {
        if (event.getEventName().equalsIgnoreCase("afterClickSaveDailyReportWhenEdit")) {
            this.processSaveReprotEvent(event);
        } else if (event.getEventName().equalsIgnoreCase("afterClickSaveDailyReportWhenAddNew")) {
            this.processAddNewReportEvent(event);
            // Send mail when create new report
            // this.sendNoticeWhenCreatedReport(event.getSavedReport());
        } else if (event.getEventName().equalsIgnoreCase("afterClickDeleteDailyReport")) {
            this.deleteNotificationByEmp(event.getSavedReport());
        } else if (event.getEventName().equalsIgnoreCase("afterCreateDailyReport")) {
            this.sendNoticeWhenCreatedReport(event.getSavedReport());
        }
    }

    /**
     * Send notice when created report.
     *
     * @param reportCreated the report created
     */
    public void sendNoticeWhenCreatedReport(final Daily_report reportCreated) {
        try {
            List<Notification_config> listManager = this.getListManagerByEmpCode(reportCreated.getDai_employee_code());
            if (CollectionUtils.isNotEmpty(listManager)) {
                for (Notification_config manager : listManager) {
                    final StringBuilder url = new StringBuilder();
                    url.append(Faces.getRequestBaseURL());
                    url.append(AuthenticationConstants.INDEX_PAGE + "?");
                    url.append("pathSearchReport=view_history_report").append("&");
                    url.append("userId=" + reportCreated.getDai_employee_code()).append("&");
                    url.append("dateSearch=" + DateUtils.formatDateTimeNotSlash(reportCreated.getDai_work_date()));
                    EmailHelper.sendInforWhenCreateReport(reportCreated, manager.getEmployee_mst().getEmp_mail(),
                        url.toString());
                }
            }
        } catch (IOException | TemplateException | MessagingException me) {
            super.log.debug("EXCEPTION SEND MAIL WHEN CREATE NEW DAILY REPORT", me);
        }
    }

    /**
     * Gets the list manager by emp code.
     *
     * @param employeeCode the employee code
     * @return the list manager by emp code
     */
    public List<Notification_config> getListManagerByEmpCode(final int employeeCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT nc FROM Notification_config nc").append(" WHERE ");
        sb.append("nc.nc_target_employee = " + employeeCode).append(" AND ");
        sb.append("nc.nc_enable=TRUE");
        return super.emMain.createQuery(sb.toString(), Notification_config.class).getResultList();
    }

    /**
     * The Class RegisterActivityEvent.
     */
    public static class RegisterActivityEvent {

        /** The event name. */
        private final String eventName;

        /** The saved report. */
        private final Daily_report savedReport;

        /**
         * Instantiates a new register activity event.
         *
         * @param eventName the event name
         * @param savedReport the saved report
         */
        public RegisterActivityEvent(final String eventName, final Daily_report savedReport) {
            this.eventName = eventName;
            this.savedReport = savedReport;
        }

        /**
         * Gets the event name.
         *
         * @return the event name
         */
        public String getEventName() {
            return this.eventName;
        }

        /**
         * Gets the saved report.
         *
         * @return the saved report
         */
        public Daily_report getSavedReport() {
            return this.savedReport;
        }
    }
}
