//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.mapped.Tdb_big_industry_MAPPED;

import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

import arrow.businesstraceability.persistence.dto.Tdb_big_industry_DTO;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Tdb_big_industry extends Tdb_big_industry_MAPPED {

    public Tdb_big_industry() {
    }

    public Tdb_big_industry(final java.lang.String code) {
        super(code);
    }

    public static Tdb_big_industry find(final java.lang.String code) {
        return EmLocator.getEm().find(Tdb_big_industry.class, new Tdb_big_industry.Pk(code));
    }

    public Tdb_big_industry_DTO getDto() {
        return DtoUtils.createDto(this, Tdb_big_industry_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}