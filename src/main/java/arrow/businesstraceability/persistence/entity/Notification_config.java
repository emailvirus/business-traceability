//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;
import javax.persistence.Transient;

import arrow.businesstraceability.persistence.dto.Notification_config_DTO;
import arrow.businesstraceability.persistence.mapped.Notification_config_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Notification_config extends Notification_config_MAPPED {

    public Notification_config() {
    }

    public Notification_config(final arrow.businesstraceability.persistence.entity.Employee_mst employeeMst, final int nc_target_employee) {
        super(employeeMst, nc_target_employee);
    }

    public static Notification_config find(final int nc_employee_code, final int nc_target_employee) {
        return EmLocator.getEm().find(Notification_config.class, new Notification_config.Pk(nc_employee_code, nc_target_employee));
    }

    public Notification_config_DTO getDto() {
        return DtoUtils.createDto(this, Notification_config_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Transient
    private boolean selectedAll;

    public boolean isSelectedAll() {
        return this.selectedAll;
    }

    public void setSelectedAll(final boolean pSelectedAll) {
        this.selectedAll = pSelectedAll;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}