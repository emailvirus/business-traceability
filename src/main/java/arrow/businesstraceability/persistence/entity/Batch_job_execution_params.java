//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.mapped.Batch_job_execution_params_MAPPED;

import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

import arrow.businesstraceability.persistence.dto.Batch_job_execution_params_DTO;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Batch_job_execution_params extends Batch_job_execution_params_MAPPED {

    public Batch_job_execution_params() {
    }

    public Batch_job_execution_params(final int job_execution_id) {
        super(job_execution_id);
    }

    public static Batch_job_execution_params find(final int job_execution_id) {
        return EmLocator.getEm().find(Batch_job_execution_params.class, new Batch_job_execution_params.Pk(job_execution_id));
    }

    public Batch_job_execution_params_DTO getDto() {
        return DtoUtils.createDto(this, Batch_job_execution_params_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}