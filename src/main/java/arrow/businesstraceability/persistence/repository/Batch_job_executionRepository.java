//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.Date;
import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Batch_job_execution;
import arrow.businesstraceability.persistence.mapped.Batch_job_execution_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Batch_job_executionRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Batch_job_execution, Batch_job_execution_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT b.job_execution_id FROM Batch_job_execution b WHERE b.start_time >= ?1 ORDER BY b.start_time ASC")
    public abstract List<Integer> getJobExecutionIdByStartTime(Date startTime);

    @Query("SELECT b.start_time FROM Batch_job_execution b WHERE b.job_execution_id = ?1 ")
    public abstract Date getBatchJobExecutionIdById(int id);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}