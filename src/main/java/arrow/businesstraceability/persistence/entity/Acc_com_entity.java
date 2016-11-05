//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.persistence.dto.Acc_com_entity_DTO;
import arrow.businesstraceability.persistence.mapped.Acc_com_entity_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Acc_com_entity extends Acc_com_entity_MAPPED {

    public Acc_com_entity() {
    }

    public Acc_com_entity(final int id_com_entity) {
        super(id_com_entity);
    }

    public static Acc_com_entity find(final int id_com_entity) {
        return EmLocator.getEm().find(Acc_com_entity.class, new Acc_com_entity.Pk(id_com_entity));
    }

    public Acc_com_entity_DTO getDto() {
        return DtoUtils.createDto(this, Acc_com_entity_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public boolean isTemporalFreeze() {
        return StringUtils.equals(Constants.TEMPORAL_FREEZE, this.getCode_acc_client());
    }

    @Transient
    private String short_indx_customer;

    public String getShort_indx_customer() {
        this.short_indx_customer =
            StringUtils.isNotEmpty(this.getIndx_customer()) ? this.getIndx_customer().substring(0, 4) : null;
        return this.short_indx_customer;
    }

    public void setShort_indx_customer(final String short_indx_customer) {
        this.short_indx_customer = short_indx_customer;
        this.setIndx_customer(this.short_indx_customer.concat(Constants.CUSTOMER_CODE_SUFFIX));
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}