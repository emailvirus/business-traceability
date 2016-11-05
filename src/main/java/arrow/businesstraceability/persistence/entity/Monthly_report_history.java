//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Monthly_report_history_DTO;
import arrow.businesstraceability.persistence.mapped.Monthly_report_history_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Monthly_report_history extends Monthly_report_history_MAPPED {

    public Monthly_report_history() {
    }

    public Monthly_report_history(final arrow.businesstraceability.persistence.entity.Monthly_report_revision monthlyReportRevision, final java.lang.String sousa) {
        super(monthlyReportRevision, sousa);
    }

    public static Monthly_report_history find(final int bajon_bangou, final int shain_kodo, final int repoto_no_getsudo, final int repoto_no_nendo, final java.lang.String sousa) {
        return EmLocator.getEm().find(Monthly_report_history.class, new Monthly_report_history.Pk(bajon_bangou, shain_kodo, repoto_no_getsudo, repoto_no_nendo, sousa));
    }

    public Monthly_report_history_DTO getDto() {
        return DtoUtils.createDto(this, Monthly_report_history_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}