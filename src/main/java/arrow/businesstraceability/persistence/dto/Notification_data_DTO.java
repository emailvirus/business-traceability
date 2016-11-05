//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Notification_data;
import arrow.businesstraceability.persistence.mapped.Notification_data_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Notification_data_DTO extends Notification_data {
    private Notification_data_MAPPED.Pk pk;

    public void setPk(final Notification_data_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Notification_data_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int nd_employee_code;

    @Override
    public int getNd_employee_code() {
        return this.nd_employee_code;
    }

    public void setNd_employee_code(final int nd_employee_code) {
        this.nd_employee_code = nd_employee_code;
    }
  
    private int nd_target_employee;

    @Override
    public int getNd_target_employee() {
        return this.nd_target_employee;
    }

    public void setNd_target_employee(final int nd_target_employee) {
        this.nd_target_employee = nd_target_employee;
    }
  
    private int nd_item_key;

    @Override
    public int getNd_item_key() {
        return this.nd_item_key;
    }

    public void setNd_item_key(final int nd_item_key) {
        this.nd_item_key = nd_item_key;
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