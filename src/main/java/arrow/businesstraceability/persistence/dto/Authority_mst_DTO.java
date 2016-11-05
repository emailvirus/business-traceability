//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Authority_mst;
import arrow.businesstraceability.persistence.mapped.Authority_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Authority_mst_DTO extends Authority_mst {
    private Authority_mst_MAPPED.Pk pk;

    public void setPk(final Authority_mst_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Authority_mst_MAPPED.Pk getPk() {
        return this.pk;
    }

    private short aut_code;

    @Override
    public short getAut_code() {
        return this.aut_code;
    }

    public void setAut_code(final short aut_code) {
        this.aut_code = aut_code;
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