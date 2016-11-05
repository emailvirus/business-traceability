//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.mapped.Tdb_detail_industry_MAPPED;

import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

import arrow.businesstraceability.persistence.dto.Tdb_detail_industry_DTO;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Tdb_detail_industry extends Tdb_detail_industry_MAPPED {

    public Tdb_detail_industry() {
    }

    public Tdb_detail_industry(final java.lang.String code) {
        super(code);
    }

    public static Tdb_detail_industry find(final java.lang.String code) {
        return EmLocator.getEm().find(Tdb_detail_industry.class, new Tdb_detail_industry.Pk(code));
    }

    public Tdb_detail_industry_DTO getDto() {
        return DtoUtils.createDto(this, Tdb_detail_industry_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}