package arrow.businesstraceability.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;

import freemarker.template.TemplateException;

import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.util.mail.EmailHelper;
import arrow.framework.logging.BaseLogger;
import arrow.framework.util.DateUtils;
import arrow.framework.util.collections.CollectionUtils;

/**
 * The Class NewReportNotificationJob.
 */
@Named
public class NewReportNotificationJob extends NoticeMailJob {
    private final String baseUri;

    /**
     * Instantiates a new new report notification job.
     *
     * @param uri the uri
     * @param localEmMain the local em main
     * @param localLog the local log
     */
    public NewReportNotificationJob(final String uri, final EntityManager localEmMain, final BaseLogger localLog) {
        super.emMain = localEmMain;
        super.log = localLog;
        this.baseUri = uri;
    }

    @Override
    protected void runJob() {
        // TODO
        this.sendDailyReport(this.baseUri, this.currentDate);
    }

    /**
     * Gets the list employee by code and date.
     *
     * @param employeeCode the employee code
     * @param dateCurrent the date current
     * @return the list employee by code and date
     */
    public List<Employee_mst> getListEmployeeByCodeAndDate(final int employeeCode, final Date dateCurrent) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT e FROM Employee_mst e, Notification_config nc, Daily_report d").append(" WHERE ");
        sb.append("e.emp_code=nc.nc_target_employee").append(" AND ");
        sb.append("e.emp_code=d.dai_employee_code").append(" AND ");
        sb.append("nc.nc_employee_code=" + employeeCode).append(" AND ");
        sb.append("d.dai_work_date='" + DateUtils.getPreviousDayFromCurrentDate(dateCurrent)).append("' AND ");
        sb.append("nc.nc_enable=TRUE");
        return super.emMain.createQuery(sb.toString(), Employee_mst.class).getResultList();
    }

    /**
     * Creates the list uri.
     *
     * @param employee the employee
     * @param url the url
     * @param currentDate the current date
     * @return the list
     */
    public List<String> createListURI(final List<Employee_mst> employee, final String url, final Date currentDate) {
        List<String> uri = new ArrayList<String>();
        for (Employee_mst user : employee) {
            uri.add(this.createURI(user, url, currentDate));
        }
        return uri;
    }

    /**
     * Creates the uri.
     *
     * @param employee the employee
     * @param baseUrl the base url
     * @param currentDate the current date
     * @return the string
     */
    public String createURI(final Employee_mst employee, final String baseUrl, final Date currentDate) {
        final StringBuilder url = new StringBuilder();
        url.append(baseUrl);
        url.append(AuthenticationConstants.INDEX_PAGE + "?");
        url.append("pathSearchReport=view_history_report").append("&");
        url.append("userId=" + employee.getEmp_code()).append("&");
        url.append(
                "dateSearch=" + DateUtils.formatDateTimeNotSlash(DateUtils.getPreviousDayFromCurrentDate(currentDate)));
        return url.toString();
    }

    /**
     * Send daily report.
     *
     * @param url the url
     * @param currentDate the current date
     * @return the string
     */
    public String sendDailyReport(final String url, final Date currentDate) {
        List<Integer> managerCode = this.listEmployeeCode;
        if (CollectionUtils.isNotEmpty(managerCode)) {
            for (Integer manager : managerCode) {
                Employee_mst infoManager = super.getInfoEmployee(manager);
                List<Employee_mst> listTargetEmployee = this.getListEmployeeByCodeAndDate(manager, currentDate);
                try {
                    if (CollectionUtils.isNotEmpty(listTargetEmployee)) {
                        EmailHelper.sendInfoReportByDay(listTargetEmployee, infoManager.getEmp_mail(),
                                this.createListURI(listTargetEmployee, url, currentDate),
                                DateUtils.getPreviousDayFromCurrentDate(currentDate));
                    }
                } catch (IOException | TemplateException | MessagingException me) {
                    super.log.debug("ERROR WHEN SEND MAIL NOTICE DAILY REPORT", me);
                }
            }
            return "HAVE SENT";
        }
        return "HAVE NOT SENT";
    }
}
