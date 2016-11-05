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
public class Industry_mdl_info_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Industry_mdl_info> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Industry_mdl_info.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param inm_company_code Data type: java.lang.String
         * @param inm_big_code Data type: java.lang.String
         * @param inm_mdl_code Data type: java.lang.String
         *
         */
        public Pk(final java.lang.String inm_company_code, final java.lang.String inm_big_code, final java.lang.String inm_mdl_code) {
            this.inm_company_code = inm_company_code;
            this.inm_big_code = inm_big_code;
            this.inm_mdl_code = inm_mdl_code;
        }

        @Column(name = "INM_COMPANY_CODE")
        protected java.lang.String inm_company_code;

        public java.lang.String getInm_company_code() {
            return this.inm_company_code;
        }

        @Column(name = "INM_BIG_CODE")
        protected java.lang.String inm_big_code;

        public java.lang.String getInm_big_code() {
            return this.inm_big_code;
        }

        @Column(name = "INM_MDL_CODE")
        protected java.lang.String inm_mdl_code;

        public java.lang.String getInm_mdl_code() {
            return this.inm_mdl_code;
        }
    }

    //default constructor
    public Industry_mdl_info_MAPPED() {
    }

    protected Industry_mdl_info_MAPPED(final arrow.businesstraceability.persistence.entity.Company_mst company_mst, final arrow.businesstraceability.persistence.entity.Industry_big_info industry_big_info, final java.lang.String inm_mdl_code) {
        this.checkFKConsistency(company_mst, industry_big_info, inm_mdl_code);
        this.pk = new Pk(company_mst.getCom_company_code(), industry_big_info.getInb_big_code(), inm_mdl_code);
        this.company_mst = company_mst;
        this.industry_big_info = industry_big_info;
        this.inm_mdl_code = inm_mdl_code;
        this.inm_company_code = company_mst.getCom_company_code();
        this.inm_big_code = industry_big_info.getInb_big_code();
    }

    private void checkFKConsistency(arrow.businesstraceability.persistence.entity.Company_mst company_mst, arrow.businesstraceability.persistence.entity.Industry_big_info industry_big_info, java.lang.String inm_mdl_code) {
        if (company_mst == null) {
            throw new IllegalArgumentException("Parameter company_mst must not be null");
        }
        if (industry_big_info == null) {
            throw new IllegalArgumentException("Parameter industry_big_info must not be null");
        }
        if (inm_mdl_code == null) {
            throw new IllegalArgumentException("Parameter inm_mdl_code must not be null");
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
    protected java.lang.String inm_company_code;

    public java.lang.String getInm_company_code() {
        return this.inm_company_code;
    }

    @Column(insertable = false, updatable = false)
    protected java.lang.String inm_big_code;

    public java.lang.String getInm_big_code() {
        return this.inm_big_code;
    }

    @Column(insertable = false, updatable = false)
    protected java.lang.String inm_mdl_code;

    public java.lang.String getInm_mdl_code() {
        return this.inm_mdl_code;
    }

    //Normal columns

    @Column
    private boolean inm_delete_flg;

    public boolean getInm_delete_flg() {
        return this.inm_delete_flg;
    }

    public final boolean getInm_delete_flg_DIRECT() {
        return this.inm_delete_flg;
    }

    public void setInm_delete_flg(final boolean inm_delete_flg) {
        this.inm_delete_flg = inm_delete_flg;
    }

    public final void setInm_delete_flg_DIRECT(final boolean inm_delete_flg) {
        this.inm_delete_flg = inm_delete_flg;
    }

    @Column
    private java.util.Date inm_update_date;

    public java.util.Date getInm_update_date() {
        return this.inm_update_date;
    }

    public final java.util.Date getInm_update_date_DIRECT() {
        return this.inm_update_date;
    }

    public void setInm_update_date(final java.util.Date inm_update_date) {
        this.inm_update_date = inm_update_date;
    }

    public final void setInm_update_date_DIRECT(final java.util.Date inm_update_date) {
        this.inm_update_date = inm_update_date;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "INM_COMPANY_CODE", referencedColumnName = "COM_COMPANY_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Company_mst company_mst;

    public arrow.businesstraceability.persistence.entity.Company_mst getCompany_mst() {
        return this.company_mst;
    }

    /**
     * Set Company_mst for Industry_mdl_info_MAPPED.
     *
     * @param company_mst Company_mst.
     *
     **/
    public void setCompany_mst(final arrow.businesstraceability.persistence.entity.Company_mst company_mst) {
        if (company_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Industry_mdl_info_MAPPED.setCompany_mst(Company_mst company_mst) must not be null");
        }
        else {
            this.inm_company_code = company_mst.getCom_company_code();
        }
        this.company_mst = company_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "INM_BIG_CODE", referencedColumnName = "INB_BIG_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Industry_big_info industry_big_info;

    public arrow.businesstraceability.persistence.entity.Industry_big_info getIndustry_big_info() {
        return this.industry_big_info;
    }

    /**
     * Set Industry_big_info for Industry_mdl_info_MAPPED.
     *
     * @param industry_big_info Industry_big_info.
     *
     **/
    public void setIndustry_big_info(final arrow.businesstraceability.persistence.entity.Industry_big_info industry_big_info) {
        if (industry_big_info == null) {
            throw new IllegalArgumentException(
                    "Param of Industry_mdl_info_MAPPED.setIndustry_big_info(Industry_big_info industry_big_info) must not be null");
        }
        else {
            this.inm_big_code = industry_big_info.getInb_big_code();
        }
        this.industry_big_info = industry_big_info;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}