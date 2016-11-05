//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Batch_job_execution_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Batch_job_execution> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Batch_job_execution.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param job_execution_id Data type: int
         *
         */
        public Pk(final int job_execution_id) {
            this.job_execution_id = job_execution_id;
        }

        @Column(name = "JOB_EXECUTION_ID")
        protected int job_execution_id;

        public int getJob_execution_id() {
            return this.job_execution_id;
        }
    }

    //default constructor
    public Batch_job_execution_MAPPED() {
    }

    protected Batch_job_execution_MAPPED(final int job_execution_id) {
        this.checkFKConsistency(job_execution_id);
        this.pk = new Pk(job_execution_id);
        this.job_execution_id = job_execution_id;
    }

    private void checkFKConsistency(int job_execution_id) {
    }

    //Primary Key
    //Should be initialized only once by the constructor, thus there's no setters
    @EmbeddedId
    protected Pk pk;
    @Override
    public Pk getPk() {
        return this.pk;
    }

    //PK columns
    //Should have insertable=false, updatable=false, and no setters

    @Column(insertable = false, updatable = false)
    protected int job_execution_id;

    public int getJob_execution_id() {
        return this.job_execution_id;
    }

    //Normal columns

    @Column
    private java.util.Date create_time;

    public java.util.Date getCreate_time() {
        return this.create_time;
    }

    public final java.util.Date getCreate_time_DIRECT() {
        return this.create_time;
    }

    public void setCreate_time(final java.util.Date create_time) {
        this.create_time = create_time;
    }

    public final void setCreate_time_DIRECT(final java.util.Date create_time) {
        this.create_time = create_time;
    }

    @Column
    private java.util.Date end_time;

    public java.util.Date getEnd_time() {
        return this.end_time;
    }

    public final java.util.Date getEnd_time_DIRECT() {
        return this.end_time;
    }

    public void setEnd_time(final java.util.Date end_time) {
        this.end_time = end_time;
    }

    public final void setEnd_time_DIRECT(final java.util.Date end_time) {
        this.end_time = end_time;
    }

    @Column
    private java.lang.String exit_code;

    public java.lang.String getExit_code() {
        return this.exit_code;
    }

    public final java.lang.String getExit_code_DIRECT() {
        return this.exit_code;
    }

    public void setExit_code(final java.lang.String exit_code) {
        this.exit_code = exit_code;
    }

    public final void setExit_code_DIRECT(final java.lang.String exit_code) {
        this.exit_code = exit_code;
    }

    @Column
    private java.lang.String exit_message;

    public java.lang.String getExit_message() {
        return this.exit_message;
    }

    public final java.lang.String getExit_message_DIRECT() {
        return this.exit_message;
    }

    public void setExit_message(final java.lang.String exit_message) {
        this.exit_message = exit_message;
    }

    public final void setExit_message_DIRECT(final java.lang.String exit_message) {
        this.exit_message = exit_message;
    }

    @Column
    private java.lang.String job_configuration_location;

    public java.lang.String getJob_configuration_location() {
        return this.job_configuration_location;
    }

    public final java.lang.String getJob_configuration_location_DIRECT() {
        return this.job_configuration_location;
    }

    public void setJob_configuration_location(final java.lang.String job_configuration_location) {
        this.job_configuration_location = job_configuration_location;
    }

    public final void setJob_configuration_location_DIRECT(final java.lang.String job_configuration_location) {
        this.job_configuration_location = job_configuration_location;
    }

    @Column
    private java.lang.Integer job_instance_id;

    public java.lang.Integer getJob_instance_id() {
        return this.job_instance_id;
    }

    public final java.lang.Integer getJob_instance_id_DIRECT() {
        return this.job_instance_id;
    }

    public void setJob_instance_id(final java.lang.Integer job_instance_id) {
        this.job_instance_id = job_instance_id;
    }

    public final void setJob_instance_id_DIRECT(final java.lang.Integer job_instance_id) {
        this.job_instance_id = job_instance_id;
    }

    @Column
    private java.util.Date last_updated;

    public java.util.Date getLast_updated() {
        return this.last_updated;
    }

    public final java.util.Date getLast_updated_DIRECT() {
        return this.last_updated;
    }

    public void setLast_updated(final java.util.Date last_updated) {
        this.last_updated = last_updated;
    }

    public final void setLast_updated_DIRECT(final java.util.Date last_updated) {
        this.last_updated = last_updated;
    }

    @Column
    private java.util.Date start_time;

    public java.util.Date getStart_time() {
        return this.start_time;
    }

    public final java.util.Date getStart_time_DIRECT() {
        return this.start_time;
    }

    public void setStart_time(final java.util.Date start_time) {
        this.start_time = start_time;
    }

    public final void setStart_time_DIRECT(final java.util.Date start_time) {
        this.start_time = start_time;
    }

    @Column
    private java.lang.String status;

    public java.lang.String getStatus() {
        return this.status;
    }

    public final java.lang.String getStatus_DIRECT() {
        return this.status;
    }

    public void setStatus(final java.lang.String status) {
        this.status = status;
    }

    public final void setStatus_DIRECT(final java.lang.String status) {
        this.status = status;
    }

    @Column
    private int version;

    public int getVersion() {
        return this.version;
    }

    public final int getVersion_DIRECT() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public final void setVersion_DIRECT(final int version) {
        this.version = version;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}