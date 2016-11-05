//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_com_site_DTO;
import arrow.businesstraceability.persistence.mapped.San_com_site_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_com_site extends San_com_site_MAPPED {

    public San_com_site() {
    }

    public San_com_site(final int id_site) {
        super(id_site);
    }

    public static San_com_site find(final int id_site) {
        return EmLocator.getEm().find(San_com_site.class, new San_com_site.Pk(id_site));
    }

    public San_com_site_DTO getDto() {
        return DtoUtils.createDto(this, San_com_site_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_site(final int id_site) {
        this.id_site = id_site;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}