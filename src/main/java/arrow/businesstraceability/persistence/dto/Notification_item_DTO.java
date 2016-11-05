//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Notification_item;
import arrow.businesstraceability.persistence.mapped.Notification_item_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Notification_item_DTO extends Notification_item {
    private Notification_item_MAPPED.Pk pk;

    public void setPk(final Notification_item_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Notification_item_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int ni_item_key;

    @Override
    public int getNi_item_key() {
        return this.ni_item_key;
    }

    public void setNi_item_key(final int ni_item_key) {
        this.ni_item_key = ni_item_key;
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