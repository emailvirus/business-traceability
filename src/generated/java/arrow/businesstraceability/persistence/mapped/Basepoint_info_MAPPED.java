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
public class Basepoint_info_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Basepoint_info> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Basepoint_info.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param bas_point_code Data type: java.lang.String
         * @param bas_company_code Data type: java.lang.String
         *
         */
        public Pk(final java.lang.String bas_point_code, final java.lang.String bas_company_code) {
            this.bas_point_code = bas_point_code;
            this.bas_company_code = bas_company_code;
        }

        @Column(name = "BAS_POINT_CODE")
        protected java.lang.String bas_point_code;

        public java.lang.String getBas_point_code() {
            return this.bas_point_code;
        }

        @Column(name = "BAS_COMPANY_CODE")
        protected java.lang.String bas_company_code;

        public java.lang.String getBas_company_code() {
            return this.bas_company_code;
        }
    }

    //default constructor
    public Basepoint_info_MAPPED() {
    }

    protected Basepoint_info_MAPPED(final arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst, final arrow.businesstraceability.persistence.entity.Company_mst company_mst) {
        this.checkFKConsistency(addresspoint_mst, company_mst);
        this.pk = new Pk(addresspoint_mst.getAdp_code(), company_mst.getCom_company_code());
        this.addresspoint_mst = addresspoint_mst;
        this.company_mst = company_mst;
        this.bas_point_code = addresspoint_mst.getAdp_code();
        this.bas_company_code = company_mst.getCom_company_code();
    }

    private void checkFKConsistency(arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst, arrow.businesstraceability.persistence.entity.Company_mst company_mst) {
        if (addresspoint_mst == null) {
            throw new IllegalArgumentException("Parameter addresspoint_mst must not be null");
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
    protected java.lang.String bas_point_code;

    public java.lang.String getBas_point_code() {
        return this.bas_point_code;
    }

    @Column(insertable = false, updatable = false)
    protected java.lang.String bas_company_code;

    public java.lang.String getBas_company_code() {
        return this.bas_company_code;
    }

    //Normal columns

    @Column
    private boolean bas_delete_flg;

    public boolean getBas_delete_flg() {
        return this.bas_delete_flg;
    }

    public final boolean getBas_delete_flg_DIRECT() {
        return this.bas_delete_flg;
    }

    public void setBas_delete_flg(final boolean bas_delete_flg) {
        this.bas_delete_flg = bas_delete_flg;
    }

    public final void setBas_delete_flg_DIRECT(final boolean bas_delete_flg) {
        this.bas_delete_flg = bas_delete_flg;
    }

    @Column
    private java.util.Date bas_update_date;

    public java.util.Date getBas_update_date() {
        return this.bas_update_date;
    }

    public final java.util.Date getBas_update_date_DIRECT() {
        return this.bas_update_date;
    }

    public void setBas_update_date(final java.util.Date bas_update_date) {
        this.bas_update_date = bas_update_date;
    }

    public final void setBas_update_date_DIRECT(final java.util.Date bas_update_date) {
        this.bas_update_date = bas_update_date;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "BAS_COMPANY_CODE", referencedColumnName = "COM_COMPANY_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Company_mst company_mst;

    public arrow.businesstraceability.persistence.entity.Company_mst getCompany_mst() {
        return this.company_mst;
    }

    /**
     * Set Company_mst for Basepoint_info_MAPPED.
     *
     * @param company_mst Company_mst.
     *
     **/
    public void setCompany_mst(final arrow.businesstraceability.persistence.entity.Company_mst company_mst) {
        if (company_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Basepoint_info_MAPPED.setCompany_mst(Company_mst company_mst) must not be null");
        }
        else {
            this.bas_company_code = company_mst.getCom_company_code();
        }
        this.company_mst = company_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "BAS_POINT_CODE", referencedColumnName = "ADP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst;

    public arrow.businesstraceability.persistence.entity.Addresspoint_mst getAddresspoint_mst() {
        return this.addresspoint_mst;
    }

    /**
     * Set Addresspoint_mst for Basepoint_info_MAPPED.
     *
     * @param addresspoint_mst Addresspoint_mst.
     *
     **/
    public void setAddresspoint_mst(final arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst) {
        if (addresspoint_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Basepoint_info_MAPPED.setAddresspoint_mst(Addresspoint_mst addresspoint_mst) must not be null");
        }
        else {
            this.bas_point_code = addresspoint_mst.getAdp_code();
        }
        this.addresspoint_mst = addresspoint_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}