//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_idmap_company_DTO;
import arrow.businesstraceability.persistence.mapped.San_idmap_company_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_idmap_company extends San_idmap_company_MAPPED {

    public San_idmap_company() {
    }

    public San_idmap_company(final int id_company) {
        super(id_company);
    }

    public static San_idmap_company find(final int id_company) {
        return EmLocator.getEm().find(San_idmap_company.class, new San_idmap_company.Pk(id_company));
    }

    public San_idmap_company_DTO getDto() {
        return DtoUtils.createDto(this, San_idmap_company_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_company(final int id_company) {
        this.id_company = id_company;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}