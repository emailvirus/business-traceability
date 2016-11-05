//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.Tdb_big_industry_MAPPED;

import arrow.businesstraceability.persistence.entity.Tdb_big_industry;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Tdb_big_industry_DTO extends Tdb_big_industry {
    private Tdb_big_industry_MAPPED.Pk pk;

    public void setPk(final Tdb_big_industry_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Tdb_big_industry_MAPPED.Pk getPk() {
        return this.pk;
    }

    private java.lang.String code;

    @Override
    public java.lang.String getCode() {
        return this.code;
    }

    public void setCode(final java.lang.String code) {
        this.code = code;
    }
  

    private boolean isPersisted;

    @Override
    public boolean isPersisted() {
        return this.isPersisted;
    }

    public void setPersisted(final boolean value) {
        this.isPersisted = value;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}