//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.San_com_live_owner_MAPPED;

import arrow.businesstraceability.persistence.entity.San_com_live_owner;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class San_com_live_owner_DTO extends San_com_live_owner {
    private San_com_live_owner_MAPPED.Pk pk;

    public void setPk(final San_com_live_owner_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public San_com_live_owner_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int id_company;

    @Override
    public int getId_company() {
        return this.id_company;
    }

    public void setId_company(final int id_company) {
        this.id_company = id_company;
    }
  
    private java.lang.String ac_user;

    @Override
    public java.lang.String getAc_user() {
        return this.ac_user;
    }

    public void setAc_user(final java.lang.String ac_user) {
        this.ac_user = ac_user;
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