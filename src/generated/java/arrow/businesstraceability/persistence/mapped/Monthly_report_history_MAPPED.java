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
public class Monthly_report_history_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Monthly_report_history> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Monthly_report_history.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param bajon_bangou Data type: int
         * @param shain_kodo Data type: int
         * @param repoto_no_getsudo Data type: int
         * @param repoto_no_nendo Data type: int
         * @param sousa Data type: java.lang.String
         *
         */
        public Pk(final int bajon_bangou, final int shain_kodo, final int repoto_no_getsudo, final int repoto_no_nendo, final java.lang.String sousa) {
            this.bajon_bangou = bajon_bangou;
            this.shain_kodo = shain_kodo;
            this.repoto_no_getsudo = repoto_no_getsudo;
            this.repoto_no_nendo = repoto_no_nendo;
            this.sousa = sousa;
        }

        @Column(name = "BAJON_BANGOU")
        protected int bajon_bangou;

        public int getBajon_bangou() {
            return this.bajon_bangou;
        }

        @Column(name = "SHAIN_KODO")
        protected int shain_kodo;

        public int getShain_kodo() {
            return this.shain_kodo;
        }

        @Column(name = "REPOTO_NO_GETSUDO")
        protected int repoto_no_getsudo;

        public int getRepoto_no_getsudo() {
            return this.repoto_no_getsudo;
        }

        @Column(name = "REPOTO_NO_NENDO")
        protected int repoto_no_nendo;

        public int getRepoto_no_nendo() {
            return this.repoto_no_nendo;
        }

        @Column(name = "SOUSA")
        protected java.lang.String sousa;

        public java.lang.String getSousa() {
            return this.sousa;
        }
    }

    //default constructor
    public Monthly_report_history_MAPPED() {
    }

    protected Monthly_report_history_MAPPED(final arrow.businesstraceability.persistence.entity.Monthly_report_revision monthly_report_revision, final java.lang.String sousa) {
        this.checkFKConsistency(monthly_report_revision, sousa);
        this.pk = new Pk(monthly_report_revision.getBajon_bangou(), monthly_report_revision.getShain_kodo(), monthly_report_revision.getRepoto_no_getsudo(), monthly_report_revision.getRepoto_no_nendo(), sousa);
        this.monthly_report_revision = monthly_report_revision;
        this.sousa = sousa;
        this.bajon_bangou = monthly_report_revision.getBajon_bangou();
        this.shain_kodo = monthly_report_revision.getShain_kodo();
        this.repoto_no_getsudo = monthly_report_revision.getRepoto_no_getsudo();
        this.repoto_no_nendo = monthly_report_revision.getRepoto_no_nendo();
    }

    private void checkFKConsistency(arrow.businesstraceability.persistence.entity.Monthly_report_revision monthly_report_revision, java.lang.String sousa) {
        if (monthly_report_revision == null) {
            throw new IllegalArgumentException("Parameter monthly_report_revision must not be null");
        }
        if (sousa == null) {
            throw new IllegalArgumentException("Parameter sousa must not be null");
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
    protected int bajon_bangou;

    public int getBajon_bangou() {
        return this.bajon_bangou;
    }

    @Column(insertable = false, updatable = false)
    protected int shain_kodo;

    public int getShain_kodo() {
        return this.shain_kodo;
    }

    @Column(insertable = false, updatable = false)
    protected int repoto_no_getsudo;

    public int getRepoto_no_getsudo() {
        return this.repoto_no_getsudo;
    }

    @Column(insertable = false, updatable = false)
    protected int repoto_no_nendo;

    public int getRepoto_no_nendo() {
        return this.repoto_no_nendo;
    }

    @Column(insertable = false, updatable = false)
    protected java.lang.String sousa;

    public java.lang.String getSousa() {
        return this.sousa;
    }

    //Normal columns

    @Column
    private java.lang.String comment;

    public java.lang.String getComment() {
        return this.comment;
    }

    public final java.lang.String getComment_DIRECT() {
        return this.comment;
    }

    public void setComment(final java.lang.String comment) {
        this.comment = comment;
    }

    public final void setComment_DIRECT(final java.lang.String comment) {
        this.comment = comment;
    }

    @Column
    private int shouninsha_kodo;

    public int getShouninsha_kodo() {
        return this.shouninsha_kodo;
    }

    public final int getShouninsha_kodo_DIRECT() {
        return this.shouninsha_kodo;
    }

    public void setShouninsha_kodo(final int shouninsha_kodo) {
        this.shouninsha_kodo = shouninsha_kodo;
    }

    public final void setShouninsha_kodo_DIRECT(final int shouninsha_kodo) {
        this.shouninsha_kodo = shouninsha_kodo;
    }

    @Column
    private java.util.Date sousa_jiten;

    public java.util.Date getSousa_jiten() {
        return this.sousa_jiten;
    }

    public final java.util.Date getSousa_jiten_DIRECT() {
        return this.sousa_jiten;
    }

    public void setSousa_jiten(final java.util.Date sousa_jiten) {
        this.sousa_jiten = sousa_jiten;
    }

    public final void setSousa_jiten_DIRECT(final java.util.Date sousa_jiten) {
        this.sousa_jiten = sousa_jiten;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "BAJON_BANGOU", referencedColumnName = "BAJON_BANGOU", insertable = false, updatable = false), 
        @JoinColumn(name = "SHAIN_KODO", referencedColumnName = "SHAIN_KODO", insertable = false, updatable = false), 
        @JoinColumn(name = "REPOTO_NO_GETSUDO", referencedColumnName = "REPOTO_NO_GETSUDO", insertable = false, updatable = false), 
        @JoinColumn(name = "REPOTO_NO_NENDO", referencedColumnName = "REPOTO_NO_NENDO", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Monthly_report_revision monthly_report_revision;

    public arrow.businesstraceability.persistence.entity.Monthly_report_revision getMonthly_report_revision() {
        return this.monthly_report_revision;
    }

    /**
     * Set Monthly_report_revision for Monthly_report_history_MAPPED.
     *
     * @param monthly_report_revision Monthly_report_revision.
     *
     **/
    public void setMonthly_report_revision(final arrow.businesstraceability.persistence.entity.Monthly_report_revision monthly_report_revision) {
        if (monthly_report_revision == null) {
            throw new IllegalArgumentException(
                    "Param of Monthly_report_history_MAPPED.setMonthly_report_revision(Monthly_report_revision monthly_report_revision) must not be null");
        }
        else {
            this.bajon_bangou = monthly_report_revision.getBajon_bangou();
            this.shain_kodo = monthly_report_revision.getShain_kodo();
            this.repoto_no_getsudo = monthly_report_revision.getRepoto_no_getsudo();
            this.repoto_no_nendo = monthly_report_revision.getRepoto_no_nendo();
        }
        this.monthly_report_revision = monthly_report_revision;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "SHOUNINSHA_KODO", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Employee_mst employee_mst;

    public arrow.businesstraceability.persistence.entity.Employee_mst getEmployee_mst() {
        return this.employee_mst;
    }

    /**
     * Set Employee_mst for Monthly_report_history_MAPPED.
     *
     * @param employee_mst Employee_mst.
     *
     **/
    public void setEmployee_mst(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst) {
        if (employee_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Monthly_report_history_MAPPED.setEmployee_mst(Employee_mst employee_mst) must not be null");
        }
        else {
            this.shouninsha_kodo = employee_mst.getEmp_code();
        }
        this.employee_mst = employee_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}