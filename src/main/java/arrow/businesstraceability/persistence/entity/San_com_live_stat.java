//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_com_live_stat_DTO;
import arrow.businesstraceability.persistence.mapped.San_com_live_stat_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_com_live_stat extends San_com_live_stat_MAPPED {

    public San_com_live_stat() {
    }

    public San_com_live_stat(final arrow.businesstraceability.persistence.entity.San_com_info sanComInfo) {
        super(sanComInfo);
    }

    public static San_com_live_stat find(final int id_company) {
        return EmLocator.getEm().find(San_com_live_stat.class, new San_com_live_stat.Pk(id_company));
    }

    public San_com_live_stat_DTO getDto() {
        return DtoUtils.createDto(this, San_com_live_stat_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_company(final int id_company) {
        this.id_company = id_company;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}