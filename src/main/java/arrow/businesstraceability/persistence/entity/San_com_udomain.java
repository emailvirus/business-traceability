//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_com_udomain_DTO;
import arrow.businesstraceability.persistence.mapped.San_com_udomain_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_com_udomain extends San_com_udomain_MAPPED {

    public San_com_udomain() {
    }

    public San_com_udomain(final int id_urldomain) {
        super(id_urldomain);
    }

    public static San_com_udomain find(final int id_urldomain) {
        return EmLocator.getEm().find(San_com_udomain.class, new San_com_udomain.Pk(id_urldomain));
    }

    public San_com_udomain_DTO getDto() {
        return DtoUtils.createDto(this, San_com_udomain_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_urldomain(final int id_urldomain) {
        this.id_urldomain = id_urldomain;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}