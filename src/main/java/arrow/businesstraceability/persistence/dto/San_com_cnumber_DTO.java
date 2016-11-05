//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.San_com_cnumber_MAPPED;

import arrow.businesstraceability.persistence.entity.San_com_cnumber;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class San_com_cnumber_DTO extends San_com_cnumber {
    private San_com_cnumber_MAPPED.Pk pk;

    public void setPk(final San_com_cnumber_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public San_com_cnumber_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int id_cnumber;

    @Override
    public int getId_cnumber() {
        return this.id_cnumber;
    }

    public void setId_cnumber(final int id_cnumber) {
        this.id_cnumber = id_cnumber;
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