//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Position_mst;
import arrow.businesstraceability.persistence.mapped.Position_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Position_mst_DTO extends Position_mst {
    private Position_mst_MAPPED.Pk pk;

    public void setPk(final Position_mst_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Position_mst_MAPPED.Pk getPk() {
        return this.pk;
    }

    private short pos_code;

    @Override
    public short getPos_code() {
        return this.pos_code;
    }

    public void setPos_code(final short pos_code) {
        this.pos_code = pos_code;
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