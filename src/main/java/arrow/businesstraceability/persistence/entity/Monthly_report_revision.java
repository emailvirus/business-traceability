//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.constant.SummaryReportConstants;
import arrow.businesstraceability.persistence.dto.Monthly_report_revision_DTO;
import arrow.businesstraceability.persistence.mapped.Monthly_report_revision_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Monthly_report_revision extends Monthly_report_revision_MAPPED {

    public Monthly_report_revision() {
    }

    public Monthly_report_revision(final int bajon_bangou, final arrow.businesstraceability.persistence.entity.Employee_mst employeeMst, final int repoto_no_getsudo, final int repoto_no_nendo) {
        super(bajon_bangou, employeeMst, repoto_no_getsudo, repoto_no_nendo);
    }

    public static Monthly_report_revision find(final int bajon_bangou, final int shain_kodo, final int repoto_no_getsudo, final int repoto_no_nendo) {
        return EmLocator.getEm().find(Monthly_report_revision.class, new Monthly_report_revision.Pk(bajon_bangou, shain_kodo, repoto_no_getsudo, repoto_no_nendo));
    }

    public Monthly_report_revision_DTO getDto() {
        return DtoUtils.createDto(this, Monthly_report_revision_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public boolean isOpenStatus() {
        return SummaryReportConstants.MonthlyReport.STATUS_OPEN.equalsIgnoreCase(this.getShounin_joutai());
    }

    public boolean isWaitingStatus() {
        return SummaryReportConstants.MonthlyReport.STATUS_WAITING.equalsIgnoreCase(this.getShounin_joutai());
    }

    public boolean isApprovedStatus() {
        return SummaryReportConstants.MonthlyReport.STATUS_APPROVED.equalsIgnoreCase(this.getShounin_joutai());
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}