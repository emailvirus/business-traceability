//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Batch_job_execution_params_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Batch_job_execution_params> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Batch_job_execution_params.class;
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
    public Batch_job_execution_params_MAPPED() {
    }

    protected Batch_job_execution_params_MAPPED(final int job_execution_id) {
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
    private java.util.Date date_val;

    public java.util.Date getDate_val() {
        return this.date_val;
    }

    public final java.util.Date getDate_val_DIRECT() {
        return this.date_val;
    }

    public void setDate_val(final java.util.Date date_val) {
        this.date_val = date_val;
    }

    public final void setDate_val_DIRECT(final java.util.Date date_val) {
        this.date_val = date_val;
    }

    @Column
    private java.lang.Double double_val;

    public java.lang.Double getDouble_val() {
        return this.double_val;
    }

    public final java.lang.Double getDouble_val_DIRECT() {
        return this.double_val;
    }

    public void setDouble_val(final java.lang.Double double_val) {
        this.double_val = double_val;
    }

    public final void setDouble_val_DIRECT(final java.lang.Double double_val) {
        this.double_val = double_val;
    }

    @Column
    private java.lang.String key_name;

    public java.lang.String getKey_name() {
        return this.key_name;
    }

    public final java.lang.String getKey_name_DIRECT() {
        return this.key_name;
    }

    public void setKey_name(final java.lang.String key_name) {
        this.key_name = key_name;
    }

    public final void setKey_name_DIRECT(final java.lang.String key_name) {
        this.key_name = key_name;
    }

    @Column
    private java.lang.Integer long_val;

    public java.lang.Integer getLong_val() {
        return this.long_val;
    }

    public final java.lang.Integer getLong_val_DIRECT() {
        return this.long_val;
    }

    public void setLong_val(final java.lang.Integer long_val) {
        this.long_val = long_val;
    }

    public final void setLong_val_DIRECT(final java.lang.Integer long_val) {
        this.long_val = long_val;
    }

    @Column
    private java.lang.String string_val;

    public java.lang.String getString_val() {
        return this.string_val;
    }

    public final java.lang.String getString_val_DIRECT() {
        return this.string_val;
    }

    public void setString_val(final java.lang.String string_val) {
        this.string_val = string_val;
    }

    public final void setString_val_DIRECT(final java.lang.String string_val) {
        this.string_val = string_val;
    }

    @Column
    private java.lang.String type_cd;

    public java.lang.String getType_cd() {
        return this.type_cd;
    }

    public final java.lang.String getType_cd_DIRECT() {
        return this.type_cd;
    }

    public void setType_cd(final java.lang.String type_cd) {
        this.type_cd = type_cd;
    }

    public final void setType_cd_DIRECT(final java.lang.String type_cd) {
        this.type_cd = type_cd;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}