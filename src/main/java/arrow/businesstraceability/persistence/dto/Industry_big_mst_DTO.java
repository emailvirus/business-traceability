//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.persistence.mapped.Industry_big_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Industry_big_mst_DTO extends Industry_big_mst {
    private Industry_big_mst_MAPPED.Pk pk;

    public void setPk(final Industry_big_mst_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Industry_big_mst_MAPPED.Pk getPk() {
        return this.pk;
    }

    private short big_code;

    @Override
    public short getBig_code() {
        return this.big_code;
    }

    public void setBig_code(final short big_code) {
        this.big_code = big_code;
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