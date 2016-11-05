//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Daily_report_DTO;
import arrow.businesstraceability.persistence.mapped.Daily_report_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Daily_report extends Daily_report_MAPPED {

    public Daily_report() {
    }

    public Daily_report(final arrow.businesstraceability.persistence.entity.Employee_mst employeeMst, final arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspointMst, final java.util.Date dai_work_date, final java.util.Date dai_work_stime, final java.util.Date dai_work_etime) {
        super(employeeMst, addresspointMst, dai_work_date, dai_work_stime, dai_work_etime);
    }

    public static Daily_report find(final int dai_employee_code, final java.lang.String dai_point_code, final java.util.Date dai_work_date, final java.util.Date dai_work_stime, final java.util.Date dai_work_etime) {
        return EmLocator.getEm().find(Daily_report.class, new Daily_report.Pk(dai_employee_code, dai_point_code, dai_work_date, dai_work_stime, dai_work_etime));
    }

    public Daily_report_DTO getDto() {
        return DtoUtils.createDto(this, Daily_report_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Override
    public void setDai_rimaindar_flg(final Boolean rimaindar_flag) {
        if ((rimaindar_flag == null) || !rimaindar_flag) {
            super.setDai_summary_stime_DIRECT(null);
            super.setDai_summary_etime_DIRECT(null);
        }
        super.setDai_rimaindar_flg(rimaindar_flag);

    }

    @Override
    public java.lang.Boolean getDai_rimaindar_flg() {
        return super.getDai_rimaindar_flg_DIRECT() == null ? Boolean.FALSE : super.getDai_rimaindar_flg_DIRECT();
    }

    @Override
    public void setDai_work_type(final Boolean dai_anken_flg) {
        super.setDai_work_type(dai_anken_flg);
        if (!dai_anken_flg.booleanValue()) {
            super.setDai_work_tancode((Short.valueOf((short) 0)));
        }
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}