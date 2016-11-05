//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Notification_config;
import arrow.businesstraceability.persistence.mapped.Notification_config_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Notification_config_DTO extends Notification_config {
    private Notification_config_MAPPED.Pk pk;

    public void setPk(final Notification_config_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Notification_config_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int nc_employee_code;

    @Override
    public int getNc_employee_code() {
        return this.nc_employee_code;
    }

    public void setNc_employee_code(final int nc_employee_code) {
        this.nc_employee_code = nc_employee_code;
    }
  
    private int nc_target_employee;

    @Override
    public int getNc_target_employee() {
        return this.nc_target_employee;
    }

    public void setNc_target_employee(final int nc_target_employee) {
        this.nc_target_employee = nc_target_employee;
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