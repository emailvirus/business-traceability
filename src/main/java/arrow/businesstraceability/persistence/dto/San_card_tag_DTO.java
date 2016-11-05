//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.San_card_tag_MAPPED;

import arrow.businesstraceability.persistence.entity.San_card_tag;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class San_card_tag_DTO extends San_card_tag {
    private San_card_tag_MAPPED.Pk pk;

    public void setPk(final San_card_tag_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public San_card_tag_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int id_tagtbl;

    @Override
    public int getId_tagtbl() {
        return this.id_tagtbl;
    }

    public void setId_tagtbl(final int id_tagtbl) {
        this.id_tagtbl = id_tagtbl;
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