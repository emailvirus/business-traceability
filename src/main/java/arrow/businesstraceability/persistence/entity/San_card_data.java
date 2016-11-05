//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.Transient;

import arrow.businesstraceability.persistence.dto.San_card_data_DTO;
import arrow.businesstraceability.persistence.mapped.San_card_data_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_card_data extends San_card_data_MAPPED {

    public San_card_data() {
    }

    public San_card_data(final java.lang.String id_san_card) {
        super(id_san_card);
    }

    public static San_card_data find(final java.lang.String id_san_card) {
        return EmLocator.getEm().find(San_card_data.class, new San_card_data.Pk(id_san_card));
    }

    public San_card_data_DTO getDto() {
        return DtoUtils.createDto(this, San_card_data_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_san_card(final String val) {
        this.pk = new Pk(val);
        this.id_san_card = val;
    }

    @Transient
    private Integer version;

    public void setObject_version(final Integer version) {
        this.version = version;
    }
    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}