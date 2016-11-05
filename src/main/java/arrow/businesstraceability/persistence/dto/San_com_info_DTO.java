//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.San_com_info_MAPPED;

import arrow.businesstraceability.persistence.entity.San_com_info;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class San_com_info_DTO extends San_com_info {
    private San_com_info_MAPPED.Pk pk;

    public void setPk(final San_com_info_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public San_com_info_MAPPED.Pk getPk() {
        return this.pk;
    }

    private java.lang.String id_san_company;

    @Override
    public java.lang.String getId_san_company() {
        return this.id_san_company;
    }

    public void setId_san_company(final java.lang.String id_san_company) {
        this.id_san_company = id_san_company;
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