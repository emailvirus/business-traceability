//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Industry_sml_mst_DTO;
import arrow.businesstraceability.persistence.mapped.Industry_sml_mst_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Industry_sml_mst extends Industry_sml_mst_MAPPED {

    public Industry_sml_mst() {
    }

    public Industry_sml_mst(final short sml_code, final short sml_md_code, final short sml_sm_code) {
        super(sml_code, sml_md_code, sml_sm_code);
    }

    public static Industry_sml_mst find(final short sml_code, final short sml_md_code, final short sml_sm_code) {
        return EmLocator.getEm().find(Industry_sml_mst.class, new Industry_sml_mst.Pk(sml_code, sml_md_code, sml_sm_code));
    }

    public Industry_sml_mst_DTO getDto() {
        return DtoUtils.createDto(this, Industry_sml_mst_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}