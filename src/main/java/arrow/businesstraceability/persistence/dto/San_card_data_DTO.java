//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.San_card_data_MAPPED;

import arrow.businesstraceability.persistence.entity.San_card_data;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class San_card_data_DTO extends San_card_data {
    private San_card_data_MAPPED.Pk pk;

    public void setPk(final San_card_data_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public San_card_data_MAPPED.Pk getPk() {
        return this.pk;
    }

    private java.lang.String id_san_card;

    @Override
    public java.lang.String getId_san_card() {
        return this.id_san_card;
    }

    public void setId_san_card(final java.lang.String id_san_card) {
        this.id_san_card = id_san_card;
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