//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import arrow.businesstraceability.persistence.dto.Customer_info_view_DTO;
import arrow.businesstraceability.persistence.mapped.Customer_info_view_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Customer_info_view extends Customer_info_view_MAPPED {

    public Customer_info_view() {
    }

    public Customer_info_view(final int id) {
        super(id);
    }

    public static Customer_info_view find(final int id) {
        return EmLocator.getEm().find(Customer_info_view.class, new Customer_info_view.Pk(id));
    }

    public Customer_info_view_DTO getDto() {
        return DtoUtils.createDto(this, Customer_info_view_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Override
    @JsonIgnore
    public Pk getPk() {
        return this.pk;
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}