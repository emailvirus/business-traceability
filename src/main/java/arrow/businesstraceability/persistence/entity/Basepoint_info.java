//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Basepoint_info_DTO;
import arrow.businesstraceability.persistence.mapped.Basepoint_info_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Basepoint_info extends Basepoint_info_MAPPED {

    public Basepoint_info() {
    }

    public Basepoint_info(final arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspointMst, final arrow.businesstraceability.persistence.entity.Company_mst companyMst) {
        super(addresspointMst, companyMst);
    }

    public static Basepoint_info find(final java.lang.String bas_point_code, final java.lang.String bas_company_code) {
        return EmLocator.getEm().find(Basepoint_info.class, new Basepoint_info.Pk(bas_point_code, bas_company_code));
    }

    public Basepoint_info_DTO getDto() {
        return DtoUtils.createDto(this, Basepoint_info_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}