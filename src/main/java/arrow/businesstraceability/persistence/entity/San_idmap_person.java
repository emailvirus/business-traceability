//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_idmap_person_DTO;
import arrow.businesstraceability.persistence.mapped.San_idmap_person_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_idmap_person extends San_idmap_person_MAPPED {

    public San_idmap_person() {
    }

    public San_idmap_person(final int id_person) {
        super(id_person);
    }

    public static San_idmap_person find(final int id_person) {
        return EmLocator.getEm().find(San_idmap_person.class, new San_idmap_person.Pk(id_person));
    }

    public San_idmap_person_DTO getDto() {
        return DtoUtils.createDto(this, San_idmap_person_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_person(final int id_person) {
        this.id_person = id_person;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}