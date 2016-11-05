//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Business_cons_info_DTO;
import arrow.businesstraceability.persistence.mapped.Business_cons_info_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Business_cons_info extends Business_cons_info_MAPPED {

    public Business_cons_info() {
    }

    public Business_cons_info(final java.lang.String bco_business_code, final java.util.Date bco_date) {
        super(bco_business_code, bco_date);
    }

    public static Business_cons_info find(final java.lang.String bco_business_code, final java.util.Date bco_date) {
        return EmLocator.getEm().find(Business_cons_info.class, new Business_cons_info.Pk(bco_business_code, bco_date));
    }

    public Business_cons_info_DTO getDto() {
        return DtoUtils.createDto(this, Business_cons_info_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}