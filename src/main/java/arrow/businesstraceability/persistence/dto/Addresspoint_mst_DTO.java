//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.mapped.Addresspoint_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Addresspoint_mst_DTO extends Addresspoint_mst {
    private Addresspoint_mst_MAPPED.Pk pk;

    public void setPk(final Addresspoint_mst_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Addresspoint_mst_MAPPED.Pk getPk() {
        return this.pk;
    }

    private java.lang.String adp_code;

    @Override
    public java.lang.String getAdp_code() {
        return this.adp_code;
    }

    public void setAdp_code(final java.lang.String adp_code) {
        this.adp_code = adp_code;
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