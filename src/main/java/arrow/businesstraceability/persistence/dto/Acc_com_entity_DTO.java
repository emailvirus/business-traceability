//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.Acc_com_entity_MAPPED;

import arrow.businesstraceability.persistence.entity.Acc_com_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Acc_com_entity_DTO extends Acc_com_entity {
    private Acc_com_entity_MAPPED.Pk pk;

    public void setPk(final Acc_com_entity_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Acc_com_entity_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int id_com_entity;

    @Override
    public int getId_com_entity() {
        return this.id_com_entity;
    }

    public void setId_com_entity(final int id_com_entity) {
        this.id_com_entity = id_com_entity;
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