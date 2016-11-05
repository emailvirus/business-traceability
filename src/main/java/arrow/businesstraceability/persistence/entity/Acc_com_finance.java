//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.mapped.Acc_com_finance_MAPPED;

import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

import arrow.businesstraceability.persistence.dto.Acc_com_finance_DTO;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Acc_com_finance extends Acc_com_finance_MAPPED {

    public Acc_com_finance() {
    }

    public Acc_com_finance(final int id_finance) {
        super(id_finance);
    }

    public static Acc_com_finance find(final int id_finance) {
        return EmLocator.getEm().find(Acc_com_finance.class, new Acc_com_finance.Pk(id_finance));
    }

    public Acc_com_finance_DTO getDto() {
        return DtoUtils.createDto(this, Acc_com_finance_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}