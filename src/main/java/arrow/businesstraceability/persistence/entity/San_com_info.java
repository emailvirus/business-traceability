//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_com_info_DTO;
import arrow.businesstraceability.persistence.mapped.San_com_info_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_com_info extends San_com_info_MAPPED {

    public San_com_info() {
    }

    public San_com_info(final java.lang.String id_san_company) {
        super(id_san_company);
    }

    public static San_com_info find(final java.lang.String id_san_company) {
        return EmLocator.getEm().find(San_com_info.class, new San_com_info.Pk(id_san_company));
    }

    public San_com_info_DTO getDto() {
        return DtoUtils.createDto(this, San_com_info_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_san_company(final java.lang.String id_san_company) {
        this.id_san_company = id_san_company;
    }


    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}