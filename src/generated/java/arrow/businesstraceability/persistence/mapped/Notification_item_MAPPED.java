//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Notification_item_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Notification_item> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Notification_item.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param ni_item_key Data type: int
         *
         */
        public Pk(final int ni_item_key) {
            this.ni_item_key = ni_item_key;
        }

        @Column(name = "NI_ITEM_KEY")
        protected int ni_item_key;

        public int getNi_item_key() {
            return this.ni_item_key;
        }
    }

    //default constructor
    public Notification_item_MAPPED() {
    }

    protected Notification_item_MAPPED(final int ni_item_key) {
        this.checkFKConsistency(ni_item_key);
        this.pk = new Pk(ni_item_key);
        this.ni_item_key = ni_item_key;
    }

    private void checkFKConsistency(int ni_item_key) {
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
    protected int ni_item_key;

    public int getNi_item_key() {
        return this.ni_item_key;
    }

    //Normal columns

    @Column
    private java.util.Date ni_created_at;

    public java.util.Date getNi_created_at() {
        return this.ni_created_at;
    }

    public final java.util.Date getNi_created_at_DIRECT() {
        return this.ni_created_at;
    }

    public void setNi_created_at(final java.util.Date ni_created_at) {
        this.ni_created_at = ni_created_at;
    }

    public final void setNi_created_at_DIRECT(final java.util.Date ni_created_at) {
        this.ni_created_at = ni_created_at;
    }

    @Column
    private int ni_employee_code;

    public int getNi_employee_code() {
        return this.ni_employee_code;
    }

    public final int getNi_employee_code_DIRECT() {
        return this.ni_employee_code;
    }

    public void setNi_employee_code(final int ni_employee_code) {
        this.ni_employee_code = ni_employee_code;
    }

    public final void setNi_employee_code_DIRECT(final int ni_employee_code) {
        this.ni_employee_code = ni_employee_code;
    }

    @Column
    private java.lang.String ni_point_code;

    public java.lang.String getNi_point_code() {
        return this.ni_point_code;
    }

    public final java.lang.String getNi_point_code_DIRECT() {
        return this.ni_point_code;
    }

    public void setNi_point_code(final java.lang.String ni_point_code) {
        this.ni_point_code = ni_point_code;
    }

    public final void setNi_point_code_DIRECT(final java.lang.String ni_point_code) {
        this.ni_point_code = ni_point_code;
    }

    @Column
    private java.lang.String ni_type;

    public java.lang.String getNi_type() {
        return this.ni_type;
    }

    public final java.lang.String getNi_type_DIRECT() {
        return this.ni_type;
    }

    public void setNi_type(final java.lang.String ni_type) {
        this.ni_type = ni_type;
    }

    public final void setNi_type_DIRECT(final java.lang.String ni_type) {
        this.ni_type = ni_type;
    }

    @Column
    private java.util.Date ni_work_date;

    public java.util.Date getNi_work_date() {
        return this.ni_work_date;
    }

    public final java.util.Date getNi_work_date_DIRECT() {
        return this.ni_work_date;
    }

    public void setNi_work_date(final java.util.Date ni_work_date) {
        this.ni_work_date = ni_work_date;
    }

    public final void setNi_work_date_DIRECT(final java.util.Date ni_work_date) {
        this.ni_work_date = ni_work_date;
    }

    @Column
    private java.util.Date ni_work_etime;

    public java.util.Date getNi_work_etime() {
        return this.ni_work_etime;
    }

    public final java.util.Date getNi_work_etime_DIRECT() {
        return this.ni_work_etime;
    }

    public void setNi_work_etime(final java.util.Date ni_work_etime) {
        this.ni_work_etime = ni_work_etime;
    }

    public final void setNi_work_etime_DIRECT(final java.util.Date ni_work_etime) {
        this.ni_work_etime = ni_work_etime;
    }

    @Column
    private java.util.Date ni_work_stime;

    public java.util.Date getNi_work_stime() {
        return this.ni_work_stime;
    }

    public final java.util.Date getNi_work_stime_DIRECT() {
        return this.ni_work_stime;
    }

    public void setNi_work_stime(final java.util.Date ni_work_stime) {
        this.ni_work_stime = ni_work_stime;
    }

    public final void setNi_work_stime_DIRECT(final java.util.Date ni_work_stime) {
        this.ni_work_stime = ni_work_stime;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}