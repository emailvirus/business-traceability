//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Batch_job_execution_params;
import arrow.businesstraceability.persistence.mapped.Batch_job_execution_params_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Batch_job_execution_paramsRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Batch_job_execution_params, Batch_job_execution_params_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT b.string_val FROM Batch_job_execution_params b WHERE b.key_name = 'logFileName' AND b.job_execution_id = ?1 ")
    public abstract String getLogFileNameById(Integer id);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}