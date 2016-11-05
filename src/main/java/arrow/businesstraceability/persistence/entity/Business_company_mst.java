//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Business_company_mst_DTO;
import arrow.businesstraceability.persistence.mapped.Business_company_mst_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Business_company_mst extends Business_company_mst_MAPPED {

    public Business_company_mst() {
    }

    public Business_company_mst(final java.lang.String buc_business_code, final arrow.businesstraceability.persistence.entity.Company_mst companyMst) {
        super(buc_business_code, companyMst);
    }

    public static Business_company_mst find(final java.lang.String buc_business_code, final java.lang.String buc_company_code) {
        return EmLocator.getEm().find(Business_company_mst.class, new Business_company_mst.Pk(buc_business_code, buc_company_code));
    }

    public Business_company_mst_DTO getDto() {
        return DtoUtils.createDto(this, Business_company_mst_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}