//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.Notification_data_DTO;
import arrow.businesstraceability.persistence.mapped.Notification_data_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Notification_data extends Notification_data_MAPPED {

    public Notification_data() {
    }

    public Notification_data(final arrow.businesstraceability.persistence.entity.Employee_mst employeeMst, final int nd_target_employee, final arrow.businesstraceability.persistence.entity.Notification_item notificationItem) {
        super(employeeMst, nd_target_employee, notificationItem);
    }

    public static Notification_data find(final int nd_employee_code, final int nd_target_employee, final int nd_item_key) {
        return EmLocator.getEm().find(Notification_data.class, new Notification_data.Pk(nd_employee_code, nd_target_employee, nd_item_key));
    }

    public Notification_data_DTO getDto() {
        return DtoUtils.createDto(this, Notification_data_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}