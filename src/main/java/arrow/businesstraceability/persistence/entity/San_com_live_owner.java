//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_com_live_owner_DTO;
import arrow.businesstraceability.persistence.mapped.San_com_live_owner_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_com_live_owner extends San_com_live_owner_MAPPED {

    public San_com_live_owner() {
    }

    public San_com_live_owner(final arrow.businesstraceability.persistence.entity.San_com_info sanComInfo, final java.lang.String ac_user) {
        super(sanComInfo, ac_user);
    }

    public static San_com_live_owner find(final int id_company, final java.lang.String ac_user) {
        return EmLocator.getEm().find(San_com_live_owner.class, new San_com_live_owner.Pk(id_company, ac_user));
    }

    public San_com_live_owner_DTO getDto() {
        return DtoUtils.createDto(this, San_com_live_owner_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_company(final int id_company) {
        this.id_company = id_company;
    }

    public void setAc_user(final java.lang.String ac_user) {
        this.ac_user = ac_user;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}