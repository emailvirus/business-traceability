//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.San_idmap_card_MAPPED;

import arrow.businesstraceability.persistence.entity.San_idmap_card;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class San_idmap_card_DTO extends San_idmap_card {
    private San_idmap_card_MAPPED.Pk pk;

    public void setPk(final San_idmap_card_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public San_idmap_card_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int id_card;

    @Override
    public int getId_card() {
        return this.id_card;
    }

    public void setId_card(final int id_card) {
        this.id_card = id_card;
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