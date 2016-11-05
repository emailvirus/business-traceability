//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Industry_mdl_mst_DTO;
import arrow.businesstraceability.persistence.mapped.Industry_mdl_mst_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Industry_mdl_mst extends Industry_mdl_mst_MAPPED {

    public Industry_mdl_mst() {
    }

    public Industry_mdl_mst(final short mdl_code, final short mdl_md_code) {
        super(mdl_code, mdl_md_code);
    }

    public static Industry_mdl_mst find(final short mdl_code, final short mdl_md_code) {
        return EmLocator.getEm().find(Industry_mdl_mst.class, new Industry_mdl_mst.Pk(mdl_code, mdl_md_code));
    }

    public Industry_mdl_mst_DTO getDto() {
        return DtoUtils.createDto(this, Industry_mdl_mst_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}