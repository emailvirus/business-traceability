//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Acc_com_relation_DTO;
import arrow.businesstraceability.persistence.mapped.Acc_com_relation_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Acc_com_relation extends Acc_com_relation_MAPPED {

    public Acc_com_relation() {
    }

    public Acc_com_relation(final int id_com_relation) {
        super(id_com_relation);
    }

    public static Acc_com_relation find(final int id_com_relation) {
        return EmLocator.getEm().find(Acc_com_relation.class, new Acc_com_relation.Pk(id_com_relation));
    }

    public Acc_com_relation_DTO getDto() {
        return DtoUtils.createDto(this, Acc_com_relation_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}