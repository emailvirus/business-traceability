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
public class Business_company_mst_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Business_company_mst> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Business_company_mst.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param buc_business_code Data type: java.lang.String
         * @param buc_company_code Data type: java.lang.String
         *
         */
        public Pk(final java.lang.String buc_business_code, final java.lang.String buc_company_code) {
            this.buc_business_code = buc_business_code;
            this.buc_company_code = buc_company_code;
        }

        @Column(name = "BUC_BUSINESS_CODE")
        protected java.lang.String buc_business_code;

        public java.lang.String getBuc_business_code() {
            return this.buc_business_code;
        }

        @Column(name = "BUC_COMPANY_CODE")
        protected java.lang.String buc_company_code;

        public java.lang.String getBuc_company_code() {
            return this.buc_company_code;
        }
    }

    //default constructor
    public Business_company_mst_MAPPED() {
    }

    protected Business_company_mst_MAPPED(final java.lang.String buc_business_code, final arrow.businesstraceability.persistence.entity.Company_mst company_mst) {
        this.checkFKConsistency(buc_business_code, company_mst);
        this.pk = new Pk(buc_business_code, company_mst.getCom_company_code());
        this.buc_business_code = buc_business_code;
        this.company_mst = company_mst;
        this.buc_company_code = company_mst.getCom_company_code();
    }

    private void checkFKConsistency(java.lang.String buc_business_code, arrow.businesstraceability.persistence.entity.Company_mst company_mst) {
        if (buc_business_code == null) {
            throw new IllegalArgumentException("Parameter buc_business_code must not be null");
        }
        if (company_mst == null) {
            throw new IllegalArgumentException("Parameter company_mst must not be null");
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
    protected java.lang.String buc_business_code;

    public java.lang.String getBuc_business_code() {
        return this.buc_business_code;
    }

    @Column(insertable = false, updatable = false)
    protected java.lang.String buc_company_code;

    public java.lang.String getBuc_company_code() {
        return this.buc_company_code;
    }

    //Normal columns

    @Column
    private boolean buc_delete_flg;

    public boolean getBuc_delete_flg() {
        return this.buc_delete_flg;
    }

    public final boolean getBuc_delete_flg_DIRECT() {
        return this.buc_delete_flg;
    }

    public void setBuc_delete_flg(final boolean buc_delete_flg) {
        this.buc_delete_flg = buc_delete_flg;
    }

    public final void setBuc_delete_flg_DIRECT(final boolean buc_delete_flg) {
        this.buc_delete_flg = buc_delete_flg;
    }

    @Column
    private java.util.Date buc_update_date;

    public java.util.Date getBuc_update_date() {
        return this.buc_update_date;
    }

    public final java.util.Date getBuc_update_date_DIRECT() {
        return this.buc_update_date;
    }

    public void setBuc_update_date(final java.util.Date buc_update_date) {
        this.buc_update_date = buc_update_date;
    }

    public final void setBuc_update_date_DIRECT(final java.util.Date buc_update_date) {
        this.buc_update_date = buc_update_date;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "BUC_COMPANY_CODE", referencedColumnName = "COM_COMPANY_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Company_mst company_mst;

    public arrow.businesstraceability.persistence.entity.Company_mst getCompany_mst() {
        return this.company_mst;
    }

    /**
     * Set Company_mst for Business_company_mst_MAPPED.
     *
     * @param company_mst Company_mst.
     *
     **/
    public void setCompany_mst(final arrow.businesstraceability.persistence.entity.Company_mst company_mst) {
        if (company_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Business_company_mst_MAPPED.setCompany_mst(Company_mst company_mst) must not be null");
        }
        else {
            this.buc_company_code = company_mst.getCom_company_code();
        }
        this.company_mst = company_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}