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
public class Personnel_affairs_info_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Personnel_affairs_info> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Personnel_affairs_info.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param pea_business_code Data type: java.lang.String
         * @param pea_date Data type: java.util.Date
         *
         */
        public Pk(final java.lang.String pea_business_code, final java.util.Date pea_date) {
            this.pea_business_code = pea_business_code;
            this.pea_date = pea_date;
        }

        @Column(name = "PEA_BUSINESS_CODE")
        protected java.lang.String pea_business_code;

        public java.lang.String getPea_business_code() {
            return this.pea_business_code;
        }

        @Column(name = "PEA_DATE")
        protected java.util.Date pea_date;

        public java.util.Date getPea_date() {
            return this.pea_date;
        }
    }

    //default constructor
    public Personnel_affairs_info_MAPPED() {
    }

    protected Personnel_affairs_info_MAPPED(final java.lang.String pea_business_code, final java.util.Date pea_date) {
        this.checkFKConsistency(pea_business_code, pea_date);
        this.pk = new Pk(pea_business_code, pea_date);
        this.pea_business_code = pea_business_code;
        this.pea_date = pea_date;
    }

    private void checkFKConsistency(java.lang.String pea_business_code, java.util.Date pea_date) {
        if (pea_business_code == null) {
            throw new IllegalArgumentException("Parameter pea_business_code must not be null");
        }
        if (pea_date == null) {
            throw new IllegalArgumentException("Parameter pea_date must not be null");
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
    protected java.lang.String pea_business_code;

    public java.lang.String getPea_business_code() {
        return this.pea_business_code;
    }

    @Column(insertable = false, updatable = false)
    protected java.util.Date pea_date;

    public java.util.Date getPea_date() {
        return this.pea_date;
    }

    //Normal columns

    @Column
    private java.lang.String pea_compemp_name;

    public java.lang.String getPea_compemp_name() {
        return this.pea_compemp_name;
    }

    public final java.lang.String getPea_compemp_name_DIRECT() {
        return this.pea_compemp_name;
    }

    public void setPea_compemp_name(final java.lang.String pea_compemp_name) {
        this.pea_compemp_name = pea_compemp_name;
    }

    public final void setPea_compemp_name_DIRECT(final java.lang.String pea_compemp_name) {
        this.pea_compemp_name = pea_compemp_name;
    }

    @Column
    private java.lang.String pea_comppoint_code;

    public java.lang.String getPea_comppoint_code() {
        return this.pea_comppoint_code;
    }

    public final java.lang.String getPea_comppoint_code_DIRECT() {
        return this.pea_comppoint_code;
    }

    public void setPea_comppoint_code(final java.lang.String pea_comppoint_code) {
        this.pea_comppoint_code = pea_comppoint_code;
    }

    public final void setPea_comppoint_code_DIRECT(final java.lang.String pea_comppoint_code) {
        this.pea_comppoint_code = pea_comppoint_code;
    }

    @Column
    private java.lang.String pea_details;

    public java.lang.String getPea_details() {
        return this.pea_details;
    }

    public final java.lang.String getPea_details_DIRECT() {
        return this.pea_details;
    }

    public void setPea_details(final java.lang.String pea_details) {
        this.pea_details = pea_details;
    }

    public final void setPea_details_DIRECT(final java.lang.String pea_details) {
        this.pea_details = pea_details;
    }

    @Column
    private java.lang.String pea_employee_post;

    public java.lang.String getPea_employee_post() {
        return this.pea_employee_post;
    }

    public final java.lang.String getPea_employee_post_DIRECT() {
        return this.pea_employee_post;
    }

    public void setPea_employee_post(final java.lang.String pea_employee_post) {
        this.pea_employee_post = pea_employee_post;
    }

    public final void setPea_employee_post_DIRECT(final java.lang.String pea_employee_post) {
        this.pea_employee_post = pea_employee_post;
    }

    @Column
    private java.lang.String pea_emp_adpcode;

    public java.lang.String getPea_emp_adpcode() {
        return this.pea_emp_adpcode;
    }

    public final java.lang.String getPea_emp_adpcode_DIRECT() {
        return this.pea_emp_adpcode;
    }

    public void setPea_emp_adpcode(final java.lang.String pea_emp_adpcode) {
        this.pea_emp_adpcode = pea_emp_adpcode;
    }

    public final void setPea_emp_adpcode_DIRECT(final java.lang.String pea_emp_adpcode) {
        this.pea_emp_adpcode = pea_emp_adpcode;
    }

    @Column
    private java.lang.Integer pea_emp_code;

    public java.lang.Integer getPea_emp_code() {
        return this.pea_emp_code;
    }

    public final java.lang.Integer getPea_emp_code_DIRECT() {
        return this.pea_emp_code;
    }

    public void setPea_emp_code(final java.lang.Integer pea_emp_code) {
        this.pea_emp_code = pea_emp_code;
    }

    public final void setPea_emp_code_DIRECT(final java.lang.Integer pea_emp_code) {
        this.pea_emp_code = pea_emp_code;
    }

    @Column
    private java.util.Date pea_update_date;

    public java.util.Date getPea_update_date() {
        return this.pea_update_date;
    }

    public final java.util.Date getPea_update_date_DIRECT() {
        return this.pea_update_date;
    }

    public void setPea_update_date(final java.util.Date pea_update_date) {
        this.pea_update_date = pea_update_date;
    }

    public final void setPea_update_date_DIRECT(final java.util.Date pea_update_date) {
        this.pea_update_date = pea_update_date;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}