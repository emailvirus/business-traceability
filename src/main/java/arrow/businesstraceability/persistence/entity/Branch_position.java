//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.faces.component.UIComponent;
import javax.persistence.Entity;

import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.persistence.dto.Branch_position_DTO;
import arrow.businesstraceability.persistence.mapped.Branch_position_MAPPED;
import arrow.framework.faces.validator.ValidatorUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Branch_position extends Branch_position_MAPPED {

    public Branch_position() {
    }

    public Branch_position(final int pos_id) {
        super(pos_id);
    }

    public static Branch_position find(final int pos_id) {
        return EmLocator.getEm().find(Branch_position.class, new Branch_position.Pk(pos_id));
    }

    public Branch_position_DTO getDto() {
        return DtoUtils.createDto(this, Branch_position_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public boolean isBranchLeader() {
        return AuthenticationConstants.BranchPosition.LEADER == this.getPos_code();
    }

    public boolean isBranchViceLeader() {
        return AuthenticationConstants.BranchPosition.VICE_LEADER == this.getPos_code();
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}