//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Notification_config_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Notification_config> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Notification_config.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param nc_employee_code Data type: int
         * @param nc_target_employee Data type: int
         *
         */
        public Pk(final int nc_employee_code, final int nc_target_employee) {
            this.nc_employee_code = nc_employee_code;
            this.nc_target_employee = nc_target_employee;
        }

        @Column(name = "NC_EMPLOYEE_CODE")
        protected int nc_employee_code;

        public int getNc_employee_code() {
            return this.nc_employee_code;
        }

        @Column(name = "NC_TARGET_EMPLOYEE")
        protected int nc_target_employee;

        public int getNc_target_employee() {
            return this.nc_target_employee;
        }
    }

    //default constructor
    public Notification_config_MAPPED() {
    }

    protected Notification_config_MAPPED(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst, final int nc_target_employee) {
        this.checkFKConsistency(employee_mst, nc_target_employee);
        this.pk = new Pk(employee_mst.getEmp_code(), nc_target_employee);
        this.employee_mst = employee_mst;
        this.nc_target_employee = nc_target_employee;
        this.nc_employee_code = employee_mst.getEmp_code();
    }

    private void checkFKConsistency(arrow.businesstraceability.persistence.entity.Employee_mst employee_mst, int nc_target_employee) {
        if (employee_mst == null) {
            throw new IllegalArgumentException("Parameter employee_mst must not be null");
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
    protected int nc_employee_code;

    public int getNc_employee_code() {
        return this.nc_employee_code;
    }

    @Column(insertable = false, updatable = false)
    protected int nc_target_employee;

    public int getNc_target_employee() {
        return this.nc_target_employee;
    }

    //Normal columns

    @Column
    private boolean nc_enable;

    public boolean getNc_enable() {
        return this.nc_enable;
    }

    public final boolean getNc_enable_DIRECT() {
        return this.nc_enable;
    }

    public void setNc_enable(final boolean nc_enable) {
        this.nc_enable = nc_enable;
    }

    public final void setNc_enable_DIRECT(final boolean nc_enable) {
        this.nc_enable = nc_enable;
    }

    @Column
    private java.lang.String nc_type;

    public java.lang.String getNc_type() {
        return this.nc_type;
    }

    public final java.lang.String getNc_type_DIRECT() {
        return this.nc_type;
    }

    public void setNc_type(final java.lang.String nc_type) {
        this.nc_type = nc_type;
    }

    public final void setNc_type_DIRECT(final java.lang.String nc_type) {
        this.nc_type = nc_type;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "NC_EMPLOYEE_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Employee_mst employee_mst;

    public arrow.businesstraceability.persistence.entity.Employee_mst getEmployee_mst() {
        return this.employee_mst;
    }

    /**
     * Set Employee_mst for Notification_config_MAPPED.
     *
     * @param employee_mst Employee_mst.
     *
     **/
    public void setEmployee_mst(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst) {
        if (employee_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Notification_config_MAPPED.setEmployee_mst(Employee_mst employee_mst) must not be null");
        }
        else {
            this.nc_employee_code = employee_mst.getEmp_code();
        }
        this.employee_mst = employee_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}