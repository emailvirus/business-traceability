//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.mapped.Acc_com_industry_MAPPED;

import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

import arrow.businesstraceability.persistence.dto.Acc_com_industry_DTO;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Acc_com_industry extends Acc_com_industry_MAPPED {

    public Acc_com_industry() {
    }

    public Acc_com_industry(final int id_industry) {
        super(id_industry);
    }

    public static Acc_com_industry find(final int id_industry) {
        return EmLocator.getEm().find(Acc_com_industry.class, new Acc_com_industry.Pk(id_industry));
    }

    public Acc_com_industry_DTO getDto() {
        return DtoUtils.createDto(this, Acc_com_industry_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}