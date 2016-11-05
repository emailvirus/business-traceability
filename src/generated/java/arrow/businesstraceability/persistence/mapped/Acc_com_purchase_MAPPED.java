//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Acc_com_purchase_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Acc_com_purchase> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Acc_com_purchase.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_com_relation Data type: int
         *
         */
        public Pk(final int id_com_relation) {
            this.id_com_relation = id_com_relation;
        }

        @Column(name = "ID_COM_RELATION")
        protected int id_com_relation;

        public int getId_com_relation() {
            return this.id_com_relation;
        }
    }

    //default constructor
    public Acc_com_purchase_MAPPED() {
    }

    protected Acc_com_purchase_MAPPED(final int id_com_relation) {
        this.checkFKConsistency(id_com_relation);
        this.pk = new Pk(id_com_relation);
        this.id_com_relation = id_com_relation;
    }

    private void checkFKConsistency(int id_com_relation) {
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
    protected int id_com_relation;

    public int getId_com_relation() {
        return this.id_com_relation;
    }

    //Normal columns

    @Column
    private java.lang.String code_acc_surveyer;

    public java.lang.String getCode_acc_surveyer() {
        return this.code_acc_surveyer;
    }

    public final java.lang.String getCode_acc_surveyer_DIRECT() {
        return this.code_acc_surveyer;
    }

    public void setCode_acc_surveyer(final java.lang.String code_acc_surveyer) {
        this.code_acc_surveyer = code_acc_surveyer;
    }

    public final void setCode_acc_surveyer_DIRECT(final java.lang.String code_acc_surveyer) {
        this.code_acc_surveyer = code_acc_surveyer;
    }

    @Column
    private java.util.Date date_survey;

    public java.util.Date getDate_survey() {
        return this.date_survey;
    }

    public final java.util.Date getDate_survey_DIRECT() {
        return this.date_survey;
    }

    public void setDate_survey(final java.util.Date date_survey) {
        this.date_survey = date_survey;
    }

    public final void setDate_survey_DIRECT(final java.util.Date date_survey) {
        this.date_survey = date_survey;
    }

    @Column
    private char enum_com_relation;

    public char getEnum_com_relation() {
        return this.enum_com_relation;
    }

    public final char getEnum_com_relation_DIRECT() {
        return this.enum_com_relation;
    }

    public void setEnum_com_relation(final char enum_com_relation) {
        this.enum_com_relation = enum_com_relation;
    }

    public final void setEnum_com_relation_DIRECT(final char enum_com_relation) {
        this.enum_com_relation = enum_com_relation;
    }

    @Column
    private java.lang.Integer id_credit;

    public java.lang.Integer getId_credit() {
        return this.id_credit;
    }

    public final java.lang.Integer getId_credit_DIRECT() {
        return this.id_credit;
    }

    public void setId_credit(final java.lang.Integer id_credit) {
        this.id_credit = id_credit;
    }

    public final void setId_credit_DIRECT(final java.lang.Integer id_credit) {
        this.id_credit = id_credit;
    }

    @Column
    private java.lang.String indx_customer;

    public java.lang.String getIndx_customer() {
        return this.indx_customer;
    }

    public final java.lang.String getIndx_customer_DIRECT() {
        return this.indx_customer;
    }

    public void setIndx_customer(final java.lang.String indx_customer) {
        this.indx_customer = indx_customer;
    }

    public final void setIndx_customer_DIRECT(final java.lang.String indx_customer) {
        this.indx_customer = indx_customer;
    }

    @Column
    private java.lang.String name_com_relation;

    public java.lang.String getName_com_relation() {
        return this.name_com_relation;
    }

    public final java.lang.String getName_com_relation_DIRECT() {
        return this.name_com_relation;
    }

    public void setName_com_relation(final java.lang.String name_com_relation) {
        this.name_com_relation = name_com_relation;
    }

    public final void setName_com_relation_DIRECT(final java.lang.String name_com_relation) {
        this.name_com_relation = name_com_relation;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_CREDIT", referencedColumnName = "ID_CREDIT", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Acc_com_credit acc_com_credit;

    public arrow.businesstraceability.persistence.entity.Acc_com_credit getAcc_com_credit() {
        return this.acc_com_credit;
    }

    /**
     * Set Acc_com_credit for Acc_com_purchase_MAPPED.
     *
     * @param acc_com_credit Acc_com_credit.
     *
     **/
    public void setAcc_com_credit(final arrow.businesstraceability.persistence.entity.Acc_com_credit acc_com_credit) {
        if (acc_com_credit == null) {
            this.id_credit = null;
        }
        else {
            this.id_credit = acc_com_credit.getId_credit();
        }
        this.acc_com_credit = acc_com_credit;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}