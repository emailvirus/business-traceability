//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Position_mst_DTO;
import arrow.businesstraceability.persistence.mapped.Position_mst_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Position_mst extends Position_mst_MAPPED {

    public Position_mst() {
    }

    public Position_mst(final short pos_code) {
        super(pos_code);
    }

    public static Position_mst find(final short pos_code) {
        return EmLocator.getEm().find(Position_mst.class, new Position_mst.Pk(pos_code));
    }

    public Position_mst_DTO getDto() {
        return DtoUtils.createDto(this, Position_mst_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}