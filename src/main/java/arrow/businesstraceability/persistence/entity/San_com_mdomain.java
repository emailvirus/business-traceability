//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_com_mdomain_DTO;
import arrow.businesstraceability.persistence.mapped.San_com_mdomain_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_com_mdomain extends San_com_mdomain_MAPPED {

    public San_com_mdomain() {
    }

    public San_com_mdomain(final int id_maildomain) {
        super(id_maildomain);
    }

    public static San_com_mdomain find(final int id_maildomain) {
        return EmLocator.getEm().find(San_com_mdomain.class, new San_com_mdomain.Pk(id_maildomain));
    }

    public San_com_mdomain_DTO getDto() {
        return DtoUtils.createDto(this, San_com_mdomain_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_maildomain(final int id_maildomain) {
        this.id_maildomain = id_maildomain;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}