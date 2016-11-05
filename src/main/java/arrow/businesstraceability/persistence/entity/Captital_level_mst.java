//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Captital_level_mst_DTO;
import arrow.businesstraceability.persistence.mapped.Captital_level_mst_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Captital_level_mst extends Captital_level_mst_MAPPED {

    public Captital_level_mst() {
    }

    public Captital_level_mst(final int cal_level) {
        super(cal_level);
    }

    public static Captital_level_mst find(final int cal_level) {
        return EmLocator.getEm().find(Captital_level_mst.class, new Captital_level_mst.Pk(cal_level));
    }

    public Captital_level_mst_DTO getDto() {
        return DtoUtils.createDto(this, Captital_level_mst_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}