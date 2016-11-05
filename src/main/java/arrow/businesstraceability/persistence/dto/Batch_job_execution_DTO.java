//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.mapped.Batch_job_execution_MAPPED;

import arrow.businesstraceability.persistence.entity.Batch_job_execution;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Batch_job_execution_DTO extends Batch_job_execution {
    private Batch_job_execution_MAPPED.Pk pk;

    public void setPk(final Batch_job_execution_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Batch_job_execution_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int job_execution_id;

    @Override
    public int getJob_execution_id() {
        return this.job_execution_id;
    }

    public void setJob_execution_id(final int job_execution_id) {
        this.job_execution_id = job_execution_id;
    }
  

    private boolean isPersisted;

    @Override
    public boolean isPersisted() {
        return this.isPersisted;
    }

    public void setPersisted(final boolean value) {
        this.isPersisted = value;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}