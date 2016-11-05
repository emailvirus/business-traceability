//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Daily_activity_type_DTO;
import arrow.businesstraceability.persistence.mapped.Daily_activity_type_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Daily_activity_type extends Daily_activity_type_MAPPED {

    public Daily_activity_type() {
    }

    public Daily_activity_type(final short dat_code) {
        super(dat_code);
    }

    public static Daily_activity_type find(final short dat_code) {
        return EmLocator.getEm().find(Daily_activity_type.class, new Daily_activity_type.Pk(dat_code));
    }

    public Daily_activity_type_DTO getDto() {
        return DtoUtils.createDto(this, Daily_activity_type_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}