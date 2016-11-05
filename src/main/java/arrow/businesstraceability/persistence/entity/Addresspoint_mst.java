//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Addresspoint_mst_DTO;
import arrow.businesstraceability.persistence.mapped.Addresspoint_mst_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Addresspoint_mst extends Addresspoint_mst_MAPPED {

    public Addresspoint_mst() {
    }

    public Addresspoint_mst(final java.lang.String adp_code) {
        super(adp_code);
    }

    public static Addresspoint_mst find(final java.lang.String adp_code) {
        return EmLocator.getEm().find(Addresspoint_mst.class, new Addresspoint_mst.Pk(adp_code));
    }

    public Addresspoint_mst_DTO getDto() {
        return DtoUtils.createDto(this, Addresspoint_mst_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public static Addresspoint_mst EMPTY_ENTITY() {
        Addresspoint_mst empty = new Addresspoint_mst("");
        empty.setAdp_name_DIRECT("--------");
        return empty;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}