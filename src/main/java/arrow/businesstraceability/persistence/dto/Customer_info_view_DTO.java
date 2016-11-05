//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.Customer_info_view_MAPPED;

import arrow.businesstraceability.persistence.entity.Customer_info_view;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Customer_info_view_DTO extends Customer_info_view {
    private Customer_info_view_MAPPED.Pk pk;

    public void setPk(final Customer_info_view_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Customer_info_view_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int id;

    @Override
    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
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