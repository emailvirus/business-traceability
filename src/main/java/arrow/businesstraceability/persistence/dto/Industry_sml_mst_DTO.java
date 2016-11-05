//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Industry_sml_mst;
import arrow.businesstraceability.persistence.mapped.Industry_sml_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Industry_sml_mst_DTO extends Industry_sml_mst {
    private Industry_sml_mst_MAPPED.Pk pk;

    public void setPk(final Industry_sml_mst_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Industry_sml_mst_MAPPED.Pk getPk() {
        return this.pk;
    }

    private short sml_code;

    @Override
    public short getSml_code() {
        return this.sml_code;
    }

    public void setSml_code(final short sml_code) {
        this.sml_code = sml_code;
    }
  
    private short sml_md_code;

    @Override
    public short getSml_md_code() {
        return this.sml_md_code;
    }

    public void setSml_md_code(final short sml_md_code) {
        this.sml_md_code = sml_md_code;
    }
  
    private short sml_sm_code;

    @Override
    public short getSml_sm_code() {
        return this.sml_sm_code;
    }

    public void setSml_sm_code(final short sml_sm_code) {
        this.sml_sm_code = sml_sm_code;
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