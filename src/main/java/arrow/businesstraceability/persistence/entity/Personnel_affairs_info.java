//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Personnel_affairs_info_DTO;
import arrow.businesstraceability.persistence.mapped.Personnel_affairs_info_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Personnel_affairs_info extends Personnel_affairs_info_MAPPED {

    public Personnel_affairs_info() {
    }

    public Personnel_affairs_info(final java.lang.String pea_business_code, final java.util.Date pea_date) {
        super(pea_business_code, pea_date);
    }

    public static Personnel_affairs_info find(final java.lang.String pea_business_code, final java.util.Date pea_date) {
        return EmLocator.getEm().find(Personnel_affairs_info.class, new Personnel_affairs_info.Pk(pea_business_code, pea_date));
    }

    public Personnel_affairs_info_DTO getDto() {
        return DtoUtils.createDto(this, Personnel_affairs_info_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}