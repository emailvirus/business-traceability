//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Credit_info_DTO;
import arrow.businesstraceability.persistence.mapped.Credit_info_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Credit_info extends Credit_info_MAPPED {

    public Credit_info() {
    }

    public Credit_info(final arrow.businesstraceability.persistence.entity.Company_mst companyMst, final java.util.Date crd_date) {
        super(companyMst, crd_date);
    }

    public static Credit_info find(final java.lang.String crd_company_code, final java.util.Date crd_date) {
        return EmLocator.getEm().find(Credit_info.class, new Credit_info.Pk(crd_company_code, crd_date));
    }

    public Credit_info_DTO getDto() {
        return DtoUtils.createDto(this, Credit_info_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}