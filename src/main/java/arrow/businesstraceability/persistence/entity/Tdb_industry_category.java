//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.mapped.Tdb_industry_category_MAPPED;

import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

import arrow.businesstraceability.persistence.dto.Tdb_industry_category_DTO;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Tdb_industry_category extends Tdb_industry_category_MAPPED {

    public Tdb_industry_category() {
    }

    public Tdb_industry_category(final java.lang.String code) {
        super(code);
    }

    public static Tdb_industry_category find(final java.lang.String code) {
        return EmLocator.getEm().find(Tdb_industry_category.class, new Tdb_industry_category.Pk(code));
    }

    public Tdb_industry_category_DTO getDto() {
        return DtoUtils.createDto(this, Tdb_industry_category_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}