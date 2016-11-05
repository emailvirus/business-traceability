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
public class Industry_big_info_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Industry_big_info> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Industry_big_info.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param inb_big_code Data type: java.lang.String
         *
         */
        public Pk(final java.lang.String inb_big_code) {
            this.inb_big_code = inb_big_code;
        }

        @Column(name = "INB_BIG_CODE")
        protected java.lang.String inb_big_code;

        public java.lang.String getInb_big_code() {
            return this.inb_big_code;
        }
    }

    //default constructor
    public Industry_big_info_MAPPED() {
    }

    protected Industry_big_info_MAPPED(final java.lang.String inb_big_code) {
        this.checkFKConsistency(inb_big_code);
        this.pk = new Pk(inb_big_code);
        this.inb_big_code = inb_big_code;
    }

    private void checkFKConsistency(java.lang.String inb_big_code) {
        if (inb_big_code == null) {
            throw new IllegalArgumentException("Parameter inb_big_code must not be null");
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
    protected java.lang.String inb_big_code;

    public java.lang.String getInb_big_code() {
        return this.inb_big_code;
    }

    //Normal columns

    @Column
    private java.lang.String inb_company_code;

    public java.lang.String getInb_company_code() {
        return this.inb_company_code;
    }

    public final java.lang.String getInb_company_code_DIRECT() {
        return this.inb_company_code;
    }

    public void setInb_company_code(final java.lang.String inb_company_code) {
        this.inb_company_code = inb_company_code;
    }

    public final void setInb_company_code_DIRECT(final java.lang.String inb_company_code) {
        this.inb_company_code = inb_company_code;
    }

    @Column
    private boolean inb_delete_flg;

    public boolean getInb_delete_flg() {
        return this.inb_delete_flg;
    }

    public final boolean getInb_delete_flg_DIRECT() {
        return this.inb_delete_flg;
    }

    public void setInb_delete_flg(final boolean inb_delete_flg) {
        this.inb_delete_flg = inb_delete_flg;
    }

    public final void setInb_delete_flg_DIRECT(final boolean inb_delete_flg) {
        this.inb_delete_flg = inb_delete_flg;
    }

    @Column
    private java.util.Date inb_update_date;

    public java.util.Date getInb_update_date() {
        return this.inb_update_date;
    }

    public final java.util.Date getInb_update_date_DIRECT() {
        return this.inb_update_date;
    }

    public void setInb_update_date(final java.util.Date inb_update_date) {
        this.inb_update_date = inb_update_date;
    }

    public final void setInb_update_date_DIRECT(final java.util.Date inb_update_date) {
        this.inb_update_date = inb_update_date;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "INB_COMPANY_CODE", referencedColumnName = "COM_COMPANY_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Company_mst company_mst;

    public arrow.businesstraceability.persistence.entity.Company_mst getCompany_mst() {
        return this.company_mst;
    }

    /**
     * Set Company_mst for Industry_big_info_MAPPED.
     *
     * @param company_mst Company_mst.
     *
     **/
    public void setCompany_mst(final arrow.businesstraceability.persistence.entity.Company_mst company_mst) {
        if (company_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Industry_big_info_MAPPED.setCompany_mst(Company_mst company_mst) must not be null");
        }
        else {
            this.inb_company_code = company_mst.getCom_company_code();
        }
        this.company_mst = company_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}