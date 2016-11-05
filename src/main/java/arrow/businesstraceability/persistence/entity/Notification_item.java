//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Notification_item_DTO;
import arrow.businesstraceability.persistence.mapped.Notification_item_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Notification_item extends Notification_item_MAPPED {

    public Notification_item() {
    }

    public Notification_item(final int ni_item_key) {
        super(ni_item_key);
    }

    public static Notification_item find(final int ni_item_key) {
        return EmLocator.getEm().find(Notification_item.class, new Notification_item.Pk(ni_item_key));
    }

    public Notification_item_DTO getDto() {
        return DtoUtils.createDto(this, Notification_item_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}