//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.mapped.Acc_com_bugyo14_MAPPED;

import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

import arrow.businesstraceability.persistence.dto.Acc_com_bugyo14_DTO;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Acc_com_bugyo14 extends Acc_com_bugyo14_MAPPED {

    public Acc_com_bugyo14() {
    }

    public Acc_com_bugyo14(final java.lang.String code) {
        super(code);
    }

    public static Acc_com_bugyo14 find(final java.lang.String code) {
        return EmLocator.getEm().find(Acc_com_bugyo14.class, new Acc_com_bugyo14.Pk(code));
    }

    public Acc_com_bugyo14_DTO getDto() {
        return DtoUtils.createDto(this, Acc_com_bugyo14_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}