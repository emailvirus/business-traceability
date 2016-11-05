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

import com.fasterxml.jackson.annotation.JsonIgnore;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Company_mst_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Company_mst> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Company_mst.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param com_company_code Data type: java.lang.String
         *
         */
        public Pk(final java.lang.String com_company_code) {
            this.com_company_code = com_company_code;
        }

        @Column(name = "COM_COMPANY_CODE")
        protected java.lang.String com_company_code;

        public java.lang.String getCom_company_code() {
            return this.com_company_code;
        }
    }

    //default constructor
    public Company_mst_MAPPED() {
    }

    protected Company_mst_MAPPED(final java.lang.String com_company_code) {
        this.checkFKConsistency(com_company_code);
        this.pk = new Pk(com_company_code);
        this.com_company_code = com_company_code;
    }

    private void checkFKConsistency(java.lang.String com_company_code) {
        if (com_company_code == null) {
            throw new IllegalArgumentException("Parameter com_company_code must not be null");
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
    protected java.lang.String com_company_code;

    public java.lang.String getCom_company_code() {
        return this.com_company_code;
    }

    //Normal columns

    @Column
    private java.lang.Integer com_capital_level;

    public java.lang.Integer getCom_capital_level() {
        return this.com_capital_level;
    }

    public final java.lang.Integer getCom_capital_level_DIRECT() {
        return this.com_capital_level;
    }

    public void setCom_capital_level(final java.lang.Integer com_capital_level) {
        this.com_capital_level = com_capital_level;
    }

    public final void setCom_capital_level_DIRECT(final java.lang.Integer com_capital_level) {
        this.com_capital_level = com_capital_level;
    }

    @Column
    private java.lang.String com_company_furigana;

    public java.lang.String getCom_company_furigana() {
        return this.com_company_furigana;
    }

    public final java.lang.String getCom_company_furigana_DIRECT() {
        return this.com_company_furigana;
    }

    public void setCom_company_furigana(final java.lang.String com_company_furigana) {
        this.com_company_furigana = com_company_furigana;
    }

    public final void setCom_company_furigana_DIRECT(final java.lang.String com_company_furigana) {
        this.com_company_furigana = com_company_furigana;
    }

    @Column
    private java.lang.String com_company_name;

    public java.lang.String getCom_company_name() {
        return this.com_company_name;
    }

    public final java.lang.String getCom_company_name_DIRECT() {
        return this.com_company_name;
    }

    public void setCom_company_name(final java.lang.String com_company_name) {
        this.com_company_name = com_company_name;
    }

    public final void setCom_company_name_DIRECT(final java.lang.String com_company_name) {
        this.com_company_name = com_company_name;
    }

    @Column
    private java.lang.String com_customer_code;

    public java.lang.String getCom_customer_code() {
        return this.com_customer_code;
    }

    public final java.lang.String getCom_customer_code_DIRECT() {
        return this.com_customer_code;
    }

    public void setCom_customer_code(final java.lang.String com_customer_code) {
        this.com_customer_code = com_customer_code;
    }

    public final void setCom_customer_code_DIRECT(final java.lang.String com_customer_code) {
        this.com_customer_code = com_customer_code;
    }

    @Column
    private boolean com_delete_flg;

    public boolean getCom_delete_flg() {
        return this.com_delete_flg;
    }

    public final boolean getCom_delete_flg_DIRECT() {
        return this.com_delete_flg;
    }

    public void setCom_delete_flg(final boolean com_delete_flg) {
        this.com_delete_flg = com_delete_flg;
    }

    public final void setCom_delete_flg_DIRECT(final boolean com_delete_flg) {
        this.com_delete_flg = com_delete_flg;
    }

    @Column
    private java.lang.String com_delete_reason;

    public java.lang.String getCom_delete_reason() {
        return this.com_delete_reason;
    }

    public final java.lang.String getCom_delete_reason_DIRECT() {
        return this.com_delete_reason;
    }

    public void setCom_delete_reason(final java.lang.String com_delete_reason) {
        this.com_delete_reason = com_delete_reason;
    }

    public final void setCom_delete_reason_DIRECT(final java.lang.String com_delete_reason) {
        this.com_delete_reason = com_delete_reason;
    }

    @Column
    private java.lang.Short com_indbig_code;

    public java.lang.Short getCom_indbig_code() {
        return this.com_indbig_code;
    }

    public final java.lang.Short getCom_indbig_code_DIRECT() {
        return this.com_indbig_code;
    }

    public void setCom_indbig_code(final java.lang.Short com_indbig_code) {
        this.com_indbig_code = com_indbig_code;
    }

    public final void setCom_indbig_code_DIRECT(final java.lang.Short com_indbig_code) {
        this.com_indbig_code = com_indbig_code;
    }

    @Column
    private java.lang.Short com_indmdl_code;

    public java.lang.Short getCom_indmdl_code() {
        return this.com_indmdl_code;
    }

    public final java.lang.Short getCom_indmdl_code_DIRECT() {
        return this.com_indmdl_code;
    }

    public void setCom_indmdl_code(final java.lang.Short com_indmdl_code) {
        this.com_indmdl_code = com_indmdl_code;
    }

    public final void setCom_indmdl_code_DIRECT(final java.lang.Short com_indmdl_code) {
        this.com_indmdl_code = com_indmdl_code;
    }

    @Column
    private java.lang.Short com_indsml_code;

    public java.lang.Short getCom_indsml_code() {
        return this.com_indsml_code;
    }

    public final java.lang.Short getCom_indsml_code_DIRECT() {
        return this.com_indsml_code;
    }

    public void setCom_indsml_code(final java.lang.Short com_indsml_code) {
        this.com_indsml_code = com_indsml_code;
    }

    public final void setCom_indsml_code_DIRECT(final java.lang.Short com_indsml_code) {
        this.com_indsml_code = com_indsml_code;
    }

    @Column
    private java.lang.Short com_limited_code;

    public java.lang.Short getCom_limited_code() {
        return this.com_limited_code;
    }

    public final java.lang.Short getCom_limited_code_DIRECT() {
        return this.com_limited_code;
    }

    public void setCom_limited_code(final java.lang.Short com_limited_code) {
        this.com_limited_code = com_limited_code;
    }

    public final void setCom_limited_code_DIRECT(final java.lang.Short com_limited_code) {
        this.com_limited_code = com_limited_code;
    }

    @Column
    private java.lang.Short com_listed_code;

    public java.lang.Short getCom_listed_code() {
        return this.com_listed_code;
    }

    public final java.lang.Short getCom_listed_code_DIRECT() {
        return this.com_listed_code;
    }

    public void setCom_listed_code(final java.lang.Short com_listed_code) {
        this.com_listed_code = com_listed_code;
    }

    public final void setCom_listed_code_DIRECT(final java.lang.Short com_listed_code) {
        this.com_listed_code = com_listed_code;
    }

    @Column
    private java.lang.String com_point_code;

    public java.lang.String getCom_point_code() {
        return this.com_point_code;
    }

    public final java.lang.String getCom_point_code_DIRECT() {
        return this.com_point_code;
    }

    public void setCom_point_code(final java.lang.String com_point_code) {
        this.com_point_code = com_point_code;
    }

    public final void setCom_point_code_DIRECT(final java.lang.String com_point_code) {
        this.com_point_code = com_point_code;
    }

    @Column
    private java.lang.String com_remarks;

    public java.lang.String getCom_remarks() {
        return this.com_remarks;
    }

    public final java.lang.String getCom_remarks_DIRECT() {
        return this.com_remarks;
    }

    public void setCom_remarks(final java.lang.String com_remarks) {
        this.com_remarks = com_remarks;
    }

    public final void setCom_remarks_DIRECT(final java.lang.String com_remarks) {
        this.com_remarks = com_remarks;
    }

    @Column
    private java.util.Date com_update_date;

    public java.util.Date getCom_update_date() {
        return this.com_update_date;
    }

    public final java.util.Date getCom_update_date_DIRECT() {
        return this.com_update_date;
    }

    public void setCom_update_date(final java.util.Date com_update_date) {
        this.com_update_date = com_update_date;
    }

    public final void setCom_update_date_DIRECT(final java.util.Date com_update_date) {
        this.com_update_date = com_update_date;
    }

    @Column
    private java.lang.String com_url;

    public java.lang.String getCom_url() {
        return this.com_url;
    }

    public final java.lang.String getCom_url_DIRECT() {
        return this.com_url;
    }

    public void setCom_url(final java.lang.String com_url) {
        this.com_url = com_url;
    }

    public final void setCom_url_DIRECT(final java.lang.String com_url) {
        this.com_url = com_url;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "COM_CAPITAL_LEVEL", referencedColumnName = "CAL_LEVEL", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Captital_level_mst captital_level_mst;

    public arrow.businesstraceability.persistence.entity.Captital_level_mst getCaptital_level_mst() {
        return this.captital_level_mst;
    }

    /**
     * Set Captital_level_mst for Company_mst_MAPPED.
     *
     * @param captital_level_mst Captital_level_mst.
     *
     **/
    public void setCaptital_level_mst(final arrow.businesstraceability.persistence.entity.Captital_level_mst captital_level_mst) {
        if (captital_level_mst == null) {
            this.com_capital_level = null;
        }
        else {
            this.com_capital_level = captital_level_mst.getCal_level();
        }
        this.captital_level_mst = captital_level_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "COM_INDBIG_CODE", referencedColumnName = "BIG_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Industry_big_mst industry_big_mst;

    public arrow.businesstraceability.persistence.entity.Industry_big_mst getIndustry_big_mst() {
        return this.industry_big_mst;
    }

    /**
     * Set Industry_big_mst for Company_mst_MAPPED.
     *
     * @param industry_big_mst Industry_big_mst.
     *
     **/
    public void setIndustry_big_mst(final arrow.businesstraceability.persistence.entity.Industry_big_mst industry_big_mst) {
        if (industry_big_mst == null) {
            this.com_indbig_code = null;
        }
        else {
            this.com_indbig_code = industry_big_mst.getBig_code();
        }
        this.industry_big_mst = industry_big_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "COM_POINT_CODE", referencedColumnName = "ADP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst;

    public arrow.businesstraceability.persistence.entity.Addresspoint_mst getAddresspoint_mst() {
        return this.addresspoint_mst;
    }

    /**
     * Set Addresspoint_mst for Company_mst_MAPPED.
     *
     * @param addresspoint_mst Addresspoint_mst.
     *
     **/
    public void setAddresspoint_mst(final arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst) {
        if (addresspoint_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Company_mst_MAPPED.setAddresspoint_mst(Addresspoint_mst addresspoint_mst) must not be null");
        }
        else {
            this.com_point_code = addresspoint_mst.getAdp_code();
        }
        this.addresspoint_mst = addresspoint_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}