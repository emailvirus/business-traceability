//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_com_branch_DTO;
import arrow.businesstraceability.persistence.mapped.San_com_branch_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_com_branch extends San_com_branch_MAPPED {

    public San_com_branch() {
    }

    public San_com_branch(final int id_branch) {
        super(id_branch);
    }

    public static San_com_branch find(final int id_branch) {
        return EmLocator.getEm().find(San_com_branch.class, new San_com_branch.Pk(id_branch));
    }

    public San_com_branch_DTO getDto() {
        return DtoUtils.createDto(this, San_com_branch_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_branch(final int id_branch) {
        this.id_branch = id_branch;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}