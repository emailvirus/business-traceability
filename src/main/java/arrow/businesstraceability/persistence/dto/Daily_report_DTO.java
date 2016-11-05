//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.control.misc.WorkingTime;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.mapped.Daily_report_MAPPED;
import arrow.framework.util.DateUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Daily_report_DTO extends Daily_report {
    private Daily_report_MAPPED.Pk pk;

    public void setPk(final Daily_report_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Daily_report_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int dai_employee_code;

    @Override
    public int getDai_employee_code() {
        return this.dai_employee_code;
    }

    public void setDai_employee_code(final int dai_employee_code) {
        this.dai_employee_code = dai_employee_code;
    }
  
    private java.lang.String dai_point_code;

    @Override
    public java.lang.String getDai_point_code() {
        return this.dai_point_code;
    }

    public void setDai_point_code(final java.lang.String dai_point_code) {
        this.dai_point_code = dai_point_code;
    }
  
    private java.util.Date dai_work_date;

    @Override
    public java.util.Date getDai_work_date() {
        return this.dai_work_date;
    }

    public void setDai_work_date(final java.util.Date dai_work_date) {
        this.dai_work_date = dai_work_date;
    }
  
    private java.util.Date dai_work_stime;

    @Override
    public java.util.Date getDai_work_stime() {
        return this.dai_work_stime;
    }

    public void setDai_work_stime(final java.util.Date dai_work_stime) {
        this.dai_work_stime = dai_work_stime;
    }
  
    private java.util.Date dai_work_etime;

    @Override
    public java.util.Date getDai_work_etime() {
        return this.dai_work_etime;
    }

    public void setDai_work_etime(final java.util.Date dai_work_etime) {
        this.dai_work_etime = dai_work_etime;
    }
  

    private boolean isPersisted;

    @Override
    public boolean isPersisted() {
        return this.isPersisted;
    }

    public void setPersisted(final boolean value) {
        this.isPersisted = value;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public boolean isOtherWork() {
        return !this.getDai_work_type_DIRECT();
    }

    public boolean isTurnOnReminder() {
        return Boolean.TRUE.equals(this.getDai_rimaindar_flg());
    }

    public void adjustData() {
        this.setDai_required_time_DIRECT(DateUtils.extractDuration(this.getDai_work_stime(), this.getDai_work_etime()));
        if (this.isOtherWork()) {
            this.setDai_compemp_name(null);
            this.setDai_employee_post(null);
            this.setCompany_mst(null);
            this.setDai_bus_code(null);
            this.setDaily_activity_type(null);
            this.setDai_rimaindar_flg(null);
            this.setDai_summary_stime(null);
            this.setDai_summary_etime(null);
            this.setDai_anken_flg(null);
            this.setDai_matter_flg(null);
        }
    }

    private WorkingTime startTime;

    private WorkingTime endTime;

    public boolean isValid() {
        return (this.dai_work_date != null) && this.startTime.isValid() && this.endTime.isValid();
    }

    public WorkingTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(final WorkingTime pStartTime) {
        this.startTime = pStartTime;
    }

    public WorkingTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(final WorkingTime pEndTime) {
        this.endTime = pEndTime;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}