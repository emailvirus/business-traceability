//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.San_com_udomain_MAPPED;

import arrow.businesstraceability.persistence.entity.San_com_udomain;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class San_com_udomain_DTO extends San_com_udomain {
    private San_com_udomain_MAPPED.Pk pk;

    public void setPk(final San_com_udomain_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public San_com_udomain_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int id_urldomain;

    @Override
    public int getId_urldomain() {
        return this.id_urldomain;
    }

    public void setId_urldomain(final int id_urldomain) {
        this.id_urldomain = id_urldomain;
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