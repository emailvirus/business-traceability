//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import arrow.businesstraceability.persistence.dto.San_com_live_client_DTO;
import arrow.businesstraceability.persistence.mapped.San_com_live_client_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class San_com_live_client extends San_com_live_client_MAPPED {

    public San_com_live_client() {
    }

    public San_com_live_client(final arrow.businesstraceability.persistence.entity.San_com_info sanComInfo, final int id_person) {
        super(sanComInfo, id_person);
    }

    public static San_com_live_client find(final int id_company, final int id_person) {
        return EmLocator.getEm().find(San_com_live_client.class, new San_com_live_client.Pk(id_company, id_person));
    }

    public San_com_live_client_DTO getDto() {
        return DtoUtils.createDto(this, San_com_live_client_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public void setId_company(final int id_company) {
        this.id_company = id_company;
    }

    public void setId_person(final int id_person) {
        this.id_person = id_person;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}