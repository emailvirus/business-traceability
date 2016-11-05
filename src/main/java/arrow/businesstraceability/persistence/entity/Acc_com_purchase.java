//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.mapped.Acc_com_purchase_MAPPED;

import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

import arrow.businesstraceability.persistence.dto.Acc_com_purchase_DTO;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Acc_com_purchase extends Acc_com_purchase_MAPPED {

    public Acc_com_purchase() {
    }

    public Acc_com_purchase(final int id_com_relation) {
        super(id_com_relation);
    }

    public static Acc_com_purchase find(final int id_com_relation) {
        return EmLocator.getEm().find(Acc_com_purchase.class, new Acc_com_purchase.Pk(id_com_relation));
    }

    public Acc_com_purchase_DTO getDto() {
        return DtoUtils.createDto(this, Acc_com_purchase_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}