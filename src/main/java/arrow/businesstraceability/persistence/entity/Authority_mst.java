//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Authority_mst_DTO;
import arrow.businesstraceability.persistence.mapped.Authority_mst_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;
import arrow.framework.util.i18n.Messages;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Authority_mst extends Authority_mst_MAPPED {

    public Authority_mst() {
    }

    public Authority_mst(final short aut_code) {
        super(aut_code);
    }

    public static Authority_mst find(final short aut_code) {
        return EmLocator.getEm().find(Authority_mst.class, new Authority_mst.Pk(aut_code));
    }

    public Authority_mst_DTO getDto() {
        return DtoUtils.createDto(this, Authority_mst_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    // Translate labels
    public String translateDesc() {
        return Messages.get("authority_mst_" + this.getAut_code() + ".label");
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}