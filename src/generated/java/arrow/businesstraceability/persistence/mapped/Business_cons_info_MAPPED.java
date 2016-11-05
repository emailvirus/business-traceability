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
public class Business_cons_info_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Business_cons_info> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Business_cons_info.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param bco_business_code Data type: java.lang.String
         * @param bco_date Data type: java.util.Date
         *
         */
        public Pk(final java.lang.String bco_business_code, final java.util.Date bco_date) {
            this.bco_business_code = bco_business_code;
            this.bco_date = bco_date;
        }

        @Column(name = "BCO_BUSINESS_CODE")
        protected java.lang.String bco_business_code;

        public java.lang.String getBco_business_code() {
            return this.bco_business_code;
        }

        @Column(name = "BCO_DATE")
        protected java.util.Date bco_date;

        public java.util.Date getBco_date() {
            return this.bco_date;
        }
    }

    //default constructor
    public Business_cons_info_MAPPED() {
    }

    protected Business_cons_info_MAPPED(final java.lang.String bco_business_code, final java.util.Date bco_date) {
        this.checkFKConsistency(bco_business_code, bco_date);
        this.pk = new Pk(bco_business_code, bco_date);
        this.bco_business_code = bco_business_code;
        this.bco_date = bco_date;
    }

    private void checkFKConsistency(java.lang.String bco_business_code, java.util.Date bco_date) {
        if (bco_business_code == null) {
            throw new IllegalArgumentException("Parameter bco_business_code must not be null");
        }
        if (bco_date == null) {
            throw new IllegalArgumentException("Parameter bco_date must not be null");
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
    protected java.lang.String bco_business_code;

    public java.lang.String getBco_business_code() {
        return this.bco_business_code;
    }

    @Column(insertable = false, updatable = false)
    protected java.util.Date bco_date;

    public java.util.Date getBco_date() {
        return this.bco_date;
    }

    //Normal columns

    @Column
    private java.lang.String bco_consideration;

    public java.lang.String getBco_consideration() {
        return this.bco_consideration;
    }

    public final java.lang.String getBco_consideration_DIRECT() {
        return this.bco_consideration;
    }

    public void setBco_consideration(final java.lang.String bco_consideration) {
        this.bco_consideration = bco_consideration;
    }

    public final void setBco_consideration_DIRECT(final java.lang.String bco_consideration) {
        this.bco_consideration = bco_consideration;
    }

    @Column
    private java.lang.String bco_emp_adpcode;

    public java.lang.String getBco_emp_adpcode() {
        return this.bco_emp_adpcode;
    }

    public final java.lang.String getBco_emp_adpcode_DIRECT() {
        return this.bco_emp_adpcode;
    }

    public void setBco_emp_adpcode(final java.lang.String bco_emp_adpcode) {
        this.bco_emp_adpcode = bco_emp_adpcode;
    }

    public final void setBco_emp_adpcode_DIRECT(final java.lang.String bco_emp_adpcode) {
        this.bco_emp_adpcode = bco_emp_adpcode;
    }

    @Column
    private int bco_emp_code;

    public int getBco_emp_code() {
        return this.bco_emp_code;
    }

    public final int getBco_emp_code_DIRECT() {
        return this.bco_emp_code;
    }

    public void setBco_emp_code(final int bco_emp_code) {
        this.bco_emp_code = bco_emp_code;
    }

    public final void setBco_emp_code_DIRECT(final int bco_emp_code) {
        this.bco_emp_code = bco_emp_code;
    }

    @Column
    private java.util.Date bco_update_date;

    public java.util.Date getBco_update_date() {
        return this.bco_update_date;
    }

    public final java.util.Date getBco_update_date_DIRECT() {
        return this.bco_update_date;
    }

    public void setBco_update_date(final java.util.Date bco_update_date) {
        this.bco_update_date = bco_update_date;
    }

    public final void setBco_update_date_DIRECT(final java.util.Date bco_update_date) {
        this.bco_update_date = bco_update_date;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "BCO_EMP_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Employee_mst employee_mst;

    public arrow.businesstraceability.persistence.entity.Employee_mst getEmployee_mst() {
        return this.employee_mst;
    }

    /**
     * Set Employee_mst for Business_cons_info_MAPPED.
     *
     * @param employee_mst Employee_mst.
     *
     **/
    public void setEmployee_mst(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst) {
        if (employee_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Business_cons_info_MAPPED.setEmployee_mst(Employee_mst employee_mst) must not be null");
        }
        else {
            this.bco_emp_code = employee_mst.getEmp_code();
        }
        this.employee_mst = employee_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}