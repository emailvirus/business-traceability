//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_com_cnumber_DTO;
import arrow.businesstraceability.persistence.mapped.San_com_cnumber_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_com_cnumber extends San_com_cnumber_MAPPED {

    public San_com_cnumber() {
    }

    public San_com_cnumber(final int id_cnumber) {
        super(id_cnumber);
    }

    public static San_com_cnumber find(final int id_cnumber) {
        return EmLocator.getEm().find(San_com_cnumber.class, new San_com_cnumber.Pk(id_cnumber));
    }

    public San_com_cnumber_DTO getDto() {
        return DtoUtils.createDto(this, San_com_cnumber_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_cnumber(final int id_cnumber) {
        this.id_cnumber = id_cnumber;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}