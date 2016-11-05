package arrow.businesstraceability.control.bean.common;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.control.service.DailyReportService;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.framework.bean.AbstractBean;
import arrow.framework.util.DateUtils;

/**
 * The Class CommonReportBean.
 *
 * @author tainguyen
 */
@Named
@ViewScoped
public class CommonReportBean extends AbstractBean {
    @Inject
    private DailyReportService reportService;

    /**
     * Gets the reports of selected user.
     *
     * @param report the report
     * @return the reports of selected user
     */
    public List<Daily_report> getReportsOfSelectedUser(final Daily_report report) {
        if (report == null) {
            return null;
        }
        return this.reportService.searchEmployeeReport(report.getDai_work_date(), report.getEmployee_mst());
    }

    /**
     * Show date with format.
     *
     * @param date the date
     * @return the string
     */
    public String showDateWithFormat(final Date date) {
        if (date == null) {
            return null;
        }
        return DateUtils.formatDate(date);
    }

}
