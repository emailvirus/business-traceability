//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Daily_activity_type;
import arrow.businesstraceability.persistence.mapped.Daily_activity_type_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Daily_activity_type_DTO extends Daily_activity_type {
    private Daily_activity_type_MAPPED.Pk pk;

    public void setPk(final Daily_activity_type_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Daily_activity_type_MAPPED.Pk getPk() {
        return this.pk;
    }

    private short dat_code;

    @Override
    public short getDat_code() {
        return this.dat_code;
    }

    public void setDat_code(final short dat_code) {
        this.dat_code = dat_code;
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