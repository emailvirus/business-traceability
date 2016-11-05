//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Industry_big_info;
import arrow.businesstraceability.persistence.mapped.Industry_big_info_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Industry_big_info_DTO extends Industry_big_info {
    private Industry_big_info_MAPPED.Pk pk;

    public void setPk(final Industry_big_info_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Industry_big_info_MAPPED.Pk getPk() {
        return this.pk;
    }

    private java.lang.String inb_big_code;

    @Override
    public java.lang.String getInb_big_code() {
        return this.inb_big_code;
    }

    public void setInb_big_code(final java.lang.String inb_big_code) {
        this.inb_big_code = inb_big_code;
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