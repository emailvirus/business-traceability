//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.Acc_com_bugyo15_MAPPED;

import arrow.businesstraceability.persistence.entity.Acc_com_bugyo15;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Acc_com_bugyo15_DTO extends Acc_com_bugyo15 {
    private Acc_com_bugyo15_MAPPED.Pk pk;

    public void setPk(final Acc_com_bugyo15_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Acc_com_bugyo15_MAPPED.Pk getPk() {
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