//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_card_tag_DTO;
import arrow.businesstraceability.persistence.mapped.San_card_tag_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_card_tag extends San_card_tag_MAPPED {

    public San_card_tag() {
    }

    public San_card_tag(final int id_tagtbl) {
        super(id_tagtbl);
    }

    public static San_card_tag find(final int id_tagtbl) {
        return EmLocator.getEm().find(San_card_tag.class, new San_card_tag.Pk(id_tagtbl));
    }

    public San_card_tag_DTO getDto() {
        return DtoUtils.createDto(this, San_card_tag_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_tagtbl(final int id_tagtbl) {
        this.id_tagtbl = id_tagtbl;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}