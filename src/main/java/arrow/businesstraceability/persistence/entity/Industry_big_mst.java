//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Industry_big_mst_DTO;
import arrow.businesstraceability.persistence.mapped.Industry_big_mst_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Industry_big_mst extends Industry_big_mst_MAPPED {

    public Industry_big_mst() {
    }

    public Industry_big_mst(final short big_code) {
        super(big_code);
    }

    public static Industry_big_mst find(final short big_code) {
        return EmLocator.getEm().find(Industry_big_mst.class, new Industry_big_mst.Pk(big_code));
    }

    public Industry_big_mst_DTO getDto() {
        return DtoUtils.createDto(this, Industry_big_mst_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public static Industry_big_mst find(final java.lang.Short big_code) {
        return EmLocator.getEm().find(Industry_big_mst.class, new Industry_big_mst.Pk(big_code));
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}