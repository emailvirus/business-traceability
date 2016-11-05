//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_com_whole_card_DTO;
import arrow.businesstraceability.persistence.mapped.San_com_whole_card_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_com_whole_card extends San_com_whole_card_MAPPED {

    public San_com_whole_card() {
    }

    public San_com_whole_card(final arrow.businesstraceability.persistence.entity.San_card_data sanCardData) {
        super(sanCardData);
    }

    public static San_com_whole_card find(final int id_card) {
        return EmLocator.getEm().find(San_com_whole_card.class, new San_com_whole_card.Pk(id_card));
    }

    public San_com_whole_card_DTO getDto() {
        return DtoUtils.createDto(this, San_com_whole_card_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_card(final int id_card) {
        this.id_card = id_card;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}