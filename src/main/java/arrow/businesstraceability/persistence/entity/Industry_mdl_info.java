//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Industry_mdl_info_DTO;
import arrow.businesstraceability.persistence.mapped.Industry_mdl_info_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Industry_mdl_info extends Industry_mdl_info_MAPPED {

    public Industry_mdl_info() {
    }

    public Industry_mdl_info(final arrow.businesstraceability.persistence.entity.Company_mst companyMst, final arrow.businesstraceability.persistence.entity.Industry_big_info industryBigInfo, final java.lang.String inm_mdl_code) {
        super(companyMst, industryBigInfo, inm_mdl_code);
    }

    public static Industry_mdl_info find(final java.lang.String inm_company_code, final java.lang.String inm_big_code, final java.lang.String inm_mdl_code) {
        return EmLocator.getEm().find(Industry_mdl_info.class, new Industry_mdl_info.Pk(inm_company_code, inm_big_code, inm_mdl_code));
    }

    public Industry_mdl_info_DTO getDto() {
        return DtoUtils.createDto(this, Industry_mdl_info_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}