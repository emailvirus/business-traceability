package arrow.businesstraceability.scheduler;

import java.util.Date;
import java.util.List;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Notification_config;
import arrow.framework.util.DateUtils;


/**
 * The Class NoticeMailJob.
 */
public abstract class NoticeMailJob extends AbstractService {

    /** The list notification config. */
    protected List<Notification_config> listNotificationConfig;

    protected List<Integer> listEmployeeCode;

    protected Date currentDate;

    /**
     * Gets the list manager employee code.
     *
     * @return
     */
    public void getListManagerEmployeeCode() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT nc.nc_employee_code FROM Notification_config nc");
        this.listEmployeeCode = super.emMain.createQuery(sb.toString(), Integer.class).getResultList();
    }

    /**
     * Gets the info employee.
     *
     * @param empCode the emp code
     * @return the info employee
     */
    public Employee_mst getInfoEmployee(final int empCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT e FROM Employee_mst e").append(" WHERE ");
        sb.append("e.emp_code=" + empCode);
        return super.emMain.createQuery(sb.toString(), Employee_mst.class).getSingleResult();
    }


    /**
     * Execute.
     */
    public void execute() {
        this.prepareData();

        this.runJob();

        this.cleanup();
    }

    /**
     * Cleanup.
     */
    private void cleanup() {}

    /**
     * Run job.
     */
    protected abstract void runJob();

    /**
     * Prepare data.
     */
    private void prepareData() {
        this.currentDate = DateUtils.getCurrentDatetime();
        this.getListManagerEmployeeCode();
    }
}
