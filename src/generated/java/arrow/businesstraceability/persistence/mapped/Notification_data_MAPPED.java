//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Notification_data_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Notification_data> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Notification_data.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param nd_employee_code Data type: int
         * @param nd_target_employee Data type: int
         * @param nd_item_key Data type: int
         *
         */
        public Pk(final int nd_employee_code, final int nd_target_employee, final int nd_item_key) {
            this.nd_employee_code = nd_employee_code;
            this.nd_target_employee = nd_target_employee;
            this.nd_item_key = nd_item_key;
        }

        @Column(name = "ND_EMPLOYEE_CODE")
        protected int nd_employee_code;

        public int getNd_employee_code() {
            return this.nd_employee_code;
        }

        @Column(name = "ND_TARGET_EMPLOYEE")
        protected int nd_target_employee;

        public int getNd_target_employee() {
            return this.nd_target_employee;
        }

        @Column(name = "ND_ITEM_KEY")
        protected int nd_item_key;

        public int getNd_item_key() {
            return this.nd_item_key;
        }
    }

    //default constructor
    public Notification_data_MAPPED() {
    }

    protected Notification_data_MAPPED(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst, final int nd_target_employee, final arrow.businesstraceability.persistence.entity.Notification_item notification_item) {
        this.checkFKConsistency(employee_mst, nd_target_employee, notification_item);
        this.pk = new Pk(employee_mst.getEmp_code(), nd_target_employee, notification_item.getNi_item_key());
        this.employee_mst = employee_mst;
        this.nd_target_employee = nd_target_employee;
        this.notification_item = notification_item;
        this.nd_employee_code = employee_mst.getEmp_code();
        this.nd_item_key = notification_item.getNi_item_key();
    }

    private void checkFKConsistency(arrow.businesstraceability.persistence.entity.Employee_mst employee_mst, int nd_target_employee, arrow.businesstraceability.persistence.entity.Notification_item notification_item) {
        if (employee_mst == null) {
            throw new IllegalArgumentException("Parameter employee_mst must not be null");
        }
        if (notification_item == null) {
            throw new IllegalArgumentException("Parameter notification_item must not be null");
        }
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
    protected int nd_employee_code;

    public int getNd_employee_code() {
        return this.nd_employee_code;
    }

    @Column(insertable = false, updatable = false)
    protected int nd_target_employee;

    public int getNd_target_employee() {
        return this.nd_target_employee;
    }

    @Column(insertable = false, updatable = false)
    protected int nd_item_key;

    public int getNd_item_key() {
        return this.nd_item_key;
    }

    //Normal columns

    @Column
    private java.lang.String nd_type;

    public java.lang.String getNd_type() {
        return this.nd_type;
    }

    public final java.lang.String getNd_type_DIRECT() {
        return this.nd_type;
    }

    public void setNd_type(final java.lang.String nd_type) {
        this.nd_type = nd_type;
    }

    public final void setNd_type_DIRECT(final java.lang.String nd_type) {
        this.nd_type = nd_type;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ND_EMPLOYEE_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Employee_mst employee_mst;

    public arrow.businesstraceability.persistence.entity.Employee_mst getEmployee_mst() {
        return this.employee_mst;
    }

    /**
     * Set Employee_mst for Notification_data_MAPPED.
     *
     * @param employee_mst Employee_mst.
     *
     **/
    public void setEmployee_mst(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst) {
        if (employee_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Notification_data_MAPPED.setEmployee_mst(Employee_mst employee_mst) must not be null");
        }
        else {
            this.nd_employee_code = employee_mst.getEmp_code();
        }
        this.employee_mst = employee_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ND_ITEM_KEY", referencedColumnName = "NI_ITEM_KEY", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Notification_item notification_item;

    public arrow.businesstraceability.persistence.entity.Notification_item getNotification_item() {
        return this.notification_item;
    }

    /**
     * Set Notification_item for Notification_data_MAPPED.
     *
     * @param notification_item Notification_item.
     *
     **/
    public void setNotification_item(final arrow.businesstraceability.persistence.entity.Notification_item notification_item) {
        if (notification_item == null) {
            throw new IllegalArgumentException(
                    "Param of Notification_data_MAPPED.setNotification_item(Notification_item notification_item) must not be null");
        }
        else {
            this.nd_item_key = notification_item.getNi_item_key();
        }
        this.notification_item = notification_item;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}