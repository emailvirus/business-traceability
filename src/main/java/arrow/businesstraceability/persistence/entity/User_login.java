//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.User_login_DTO;
import arrow.businesstraceability.persistence.mapped.User_login_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class User_login extends User_login_MAPPED {

    public User_login() {
    }

    public User_login(final arrow.businesstraceability.persistence.entity.Employee_mst employeeMst) {
        super(employeeMst);
    }

    public static User_login find(final int ul_user_code) {
        return EmLocator.getEm().find(User_login.class, new User_login.Pk(ul_user_code));
    }

    public User_login_DTO getDto() {
        return DtoUtils.createDto(this, User_login_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}