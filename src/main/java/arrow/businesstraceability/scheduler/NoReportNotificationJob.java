package arrow.businesstraceability.scheduler;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;

import org.apache.commons.collections.map.HashedMap;

import freemarker.template.TemplateException;

import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Notification_config;
import arrow.businesstraceability.util.mail.EmailHelper;
import arrow.framework.logging.BaseLogger;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.util.DateUtils;
import arrow.framework.util.collections.CollectionUtils;

/**
 * The Class NoReportNotificationJob.
 */
@Named
public class NoReportNotificationJob extends NoticeMailJob {

    /**
     * Instantiates a new no report notification job.
     *
     * @param localEmMain the local em main
     * @param localLog the local log
     */
    public NoReportNotificationJob(final EntityManager localEmMain, final BaseLogger localLog) {
        super.emMain = localEmMain;
        super.log = localLog;
    }

    /* (non-Javadoc)
     * @see arrow.businesstraceability.scheduler.NoticeMailJob#runJob()
     */
    @Override
    protected void runJob() {
        this.sendMailToEmployeeNotRegisterDailyReport(this.currentDate);
    }

    /**
     * Gets the daily report of employee in date.
     *
     * @param currentDate the current date
     * @param employeeCode the employee code
     * @return the daily report of employee in date
     */
    public List<Daily_report> getDailyReportOfEmployeeInDate(final Date currentDate, final int employeeCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT e FROM Daily_report e").append(" WHERE ");
        sb.append("e.dai_work_date='" + DateUtils.getPreviousDayFromCurrentDate(currentDate)).append("' AND ");
        sb.append("e.dai_employee_code=" + employeeCode).append(" ORDER BY e.dai_work_stime ASC");

        return super.emMain.createQuery(sb.toString(), Daily_report.class).getResultList();
    }

    /**
     * Gets the notification config by manager.
     *
     * @param managerCode the manager code
     * @return the notification config by manager
     */
    public List<Notification_config> getNotificationConfigByManager(final int managerCode) {
        final ArrowQuery<Notification_config> query = new ArrowQuery<>(super.emMain);
        query.select("e").from("Notification_config e");
        query.where("e.nc_employee_code=?", managerCode);
        return query.getResultList();
    }


    /**
     * Send mail to employee not register daily report.
     *
     * @param currentDate the current date
     * @return the string
     */
    public String sendMailToEmployeeNotRegisterDailyReport(final Date currentDate) {
        List<Integer> manager = this.listEmployeeCode;
        @SuppressWarnings("unchecked")
        Map<String, String> listTargetEmployee = new HashedMap();

        if (CollectionUtils.isNotEmpty(manager)) {
            // for each manager code
            for (Integer managerCode : manager) {
                Employee_mst infoManager = super.getInfoEmployee(managerCode);
                List<Notification_config> lstNotification = this.getNotificationConfigByManager(managerCode);
                for (Notification_config notificationConfig : lstNotification) {
                    int employeeCode = notificationConfig.getNc_target_employee();
                    List<Daily_report> lstDailyReport = this.getDailyReportOfEmployeeInDate(currentDate, employeeCode);
                    // Check employee not register daily report on last day
                    if (CollectionUtils.isEmpty(lstDailyReport)) {
                        Employee_mst targetedEmployee = super.getInfoEmployee(employeeCode);
                        listTargetEmployee.put(targetedEmployee.getEmp_name(),
                                targetedEmployee.getAddresspoint_mst().getAdp_name());
                    }
                }
                try {
                    if (!listTargetEmployee.isEmpty()) {
                        EmailHelper.sendEmailWhenNotRegisterDailyReport(listTargetEmployee, infoManager.getEmp_mail(),
                                DateUtils.getPreviousDayFromCurrentDate(currentDate));
                    }
                } catch (IOException | TemplateException | MessagingException me) {
                    super.log.debug("ERROR WHEN SEND MAIL NOTICE DAILY REPORT", me);
                    return "HAVE NOT SENT";
                }
            }
            return "HAVE PASSED SENT";
        }
        return "HAVE NOT MANAGER";
    }
}
