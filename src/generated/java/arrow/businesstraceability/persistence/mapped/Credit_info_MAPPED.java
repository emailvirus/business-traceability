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
public class Credit_info_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Credit_info> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Credit_info.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param crd_company_code Data type: java.lang.String
         * @param crd_date Data type: java.util.Date
         *
         */
        public Pk(final java.lang.String crd_company_code, final java.util.Date crd_date) {
            this.crd_company_code = crd_company_code;
            this.crd_date = crd_date;
        }

        @Column(name = "CRD_COMPANY_CODE")
        protected java.lang.String crd_company_code;

        public java.lang.String getCrd_company_code() {
            return this.crd_company_code;
        }

        @Column(name = "CRD_DATE")
        protected java.util.Date crd_date;

        public java.util.Date getCrd_date() {
            return this.crd_date;
        }
    }

    //default constructor
    public Credit_info_MAPPED() {
    }

    protected Credit_info_MAPPED(final arrow.businesstraceability.persistence.entity.Company_mst company_mst, final java.util.Date crd_date) {
        this.checkFKConsistency(company_mst, crd_date);
        this.pk = new Pk(company_mst.getCom_company_code(), crd_date);
        this.company_mst = company_mst;
        this.crd_date = crd_date;
        this.crd_company_code = company_mst.getCom_company_code();
    }

    private void checkFKConsistency(arrow.businesstraceability.persistence.entity.Company_mst company_mst, java.util.Date crd_date) {
        if (company_mst == null) {
            throw new IllegalArgumentException("Parameter company_mst must not be null");
        }
        if (crd_date == null) {
            throw new IllegalArgumentException("Parameter crd_date must not be null");
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
    protected java.lang.String crd_company_code;

    public java.lang.String getCrd_company_code() {
        return this.crd_company_code;
    }

    @Column(insertable = false, updatable = false)
    protected java.util.Date crd_date;

    public java.util.Date getCrd_date() {
        return this.crd_date;
    }

    //Normal columns

    @Column
    private java.lang.Boolean crd_business_state;

    public java.lang.Boolean getCrd_business_state() {
        return this.crd_business_state;
    }

    public final java.lang.Boolean getCrd_business_state_DIRECT() {
        return this.crd_business_state;
    }

    public void setCrd_business_state(final java.lang.Boolean crd_business_state) {
        this.crd_business_state = crd_business_state;
    }

    public final void setCrd_business_state_DIRECT(final java.lang.Boolean crd_business_state) {
        this.crd_business_state = crd_business_state;
    }

    @Column
    private java.lang.String crd_details;

    public java.lang.String getCrd_details() {
        return this.crd_details;
    }

    public final java.lang.String getCrd_details_DIRECT() {
        return this.crd_details;
    }

    public void setCrd_details(final java.lang.String crd_details) {
        this.crd_details = crd_details;
    }

    public final void setCrd_details_DIRECT(final java.lang.String crd_details) {
        this.crd_details = crd_details;
    }

    @Column
    private java.util.Date crd_update_date;

    public java.util.Date getCrd_update_date() {
        return this.crd_update_date;
    }

    public final java.util.Date getCrd_update_date_DIRECT() {
        return this.crd_update_date;
    }

    public void setCrd_update_date(final java.util.Date crd_update_date) {
        this.crd_update_date = crd_update_date;
    }

    public final void setCrd_update_date_DIRECT(final java.util.Date crd_update_date) {
        this.crd_update_date = crd_update_date;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "CRD_COMPANY_CODE", referencedColumnName = "COM_COMPANY_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Company_mst company_mst;

    public arrow.businesstraceability.persistence.entity.Company_mst getCompany_mst() {
        return this.company_mst;
    }

    /**
     * Set Company_mst for Credit_info_MAPPED.
     *
     * @param company_mst Company_mst.
     *
     **/
    public void setCompany_mst(final arrow.businesstraceability.persistence.entity.Company_mst company_mst) {
        if (company_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Credit_info_MAPPED.setCompany_mst(Company_mst company_mst) must not be null");
        }
        else {
            this.crd_company_code = company_mst.getCom_company_code();
        }
        this.company_mst = company_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}