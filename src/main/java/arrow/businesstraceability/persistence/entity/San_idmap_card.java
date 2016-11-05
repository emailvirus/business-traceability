//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_idmap_card_DTO;
import arrow.businesstraceability.persistence.mapped.San_idmap_card_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_idmap_card extends San_idmap_card_MAPPED {

    public San_idmap_card() {
    }

    public San_idmap_card(final int id_card) {
        super(id_card);
    }

    public static San_idmap_card find(final int id_card) {
        return EmLocator.getEm().find(San_idmap_card.class, new San_idmap_card.Pk(id_card));
    }

    public San_idmap_card_DTO getDto() {
        return DtoUtils.createDto(this, San_idmap_card_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_card(final int id_card) {
        this.id_card = id_card;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}