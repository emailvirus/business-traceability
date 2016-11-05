//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Industry_mdl_mst;
import arrow.businesstraceability.persistence.mapped.Industry_mdl_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Industry_mdl_mst_DTO extends Industry_mdl_mst {
    private Industry_mdl_mst_MAPPED.Pk pk;

    public void setPk(final Industry_mdl_mst_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Industry_mdl_mst_MAPPED.Pk getPk() {
        return this.pk;
    }

    private short mdl_code;

    @Override
    public short getMdl_code() {
        return this.mdl_code;
    }

    public void setMdl_code(final short mdl_code) {
        this.mdl_code = mdl_code;
    }
  
    private short mdl_md_code;

    @Override
    public short getMdl_md_code() {
        return this.mdl_md_code;
    }

    public void setMdl_md_code(final short mdl_md_code) {
        this.mdl_md_code = mdl_md_code;
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