//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Acc_com_industry_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Acc_com_industry> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Acc_com_industry.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_industry Data type: int
         *
         */
        public Pk(final int id_industry) {
            this.id_industry = id_industry;
        }

        @Column(name = "ID_INDUSTRY")
        protected int id_industry;

        public int getId_industry() {
            return this.id_industry;
        }
    }

    //default constructor
    public Acc_com_industry_MAPPED() {
    }

    protected Acc_com_industry_MAPPED(final int id_industry) {
        this.checkFKConsistency(id_industry);
        this.pk = new Pk(id_industry);
        this.id_industry = id_industry;
    }

    private void checkFKConsistency(int id_industry) {
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
    protected int id_industry;

    public int getId_industry() {
        return this.id_industry;
    }

    //Normal columns

    @Column
    private java.lang.String code_industry_main;

    public java.lang.String getCode_industry_main() {
        return this.code_industry_main;
    }

    public final java.lang.String getCode_industry_main_DIRECT() {
        return this.code_industry_main;
    }

    public void setCode_industry_main(final java.lang.String code_industry_main) {
        this.code_industry_main = code_industry_main;
    }

    public final void setCode_industry_main_DIRECT(final java.lang.String code_industry_main) {
        this.code_industry_main = code_industry_main;
    }

    @Column
    private java.lang.String code_industry_sub;

    public java.lang.String getCode_industry_sub() {
        return this.code_industry_sub;
    }

    public final java.lang.String getCode_industry_sub_DIRECT() {
        return this.code_industry_sub;
    }

    public void setCode_industry_sub(final java.lang.String code_industry_sub) {
        this.code_industry_sub = code_industry_sub;
    }

    public final void setCode_industry_sub_DIRECT(final java.lang.String code_industry_sub) {
        this.code_industry_sub = code_industry_sub;
    }

    @Column
    private int id_com_entity;

    public int getId_com_entity() {
        return this.id_com_entity;
    }

    public final int getId_com_entity_DIRECT() {
        return this.id_com_entity;
    }

    public void setId_com_entity(final int id_com_entity) {
        this.id_com_entity = id_com_entity;
    }

    public final void setId_com_entity_DIRECT(final int id_com_entity) {
        this.id_com_entity = id_com_entity;
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
    private java.lang.Integer pop_industry_nation;

    public java.lang.Integer getPop_industry_nation() {
        return this.pop_industry_nation;
    }

    public final java.lang.Integer getPop_industry_nation_DIRECT() {
        return this.pop_industry_nation;
    }

    public void setPop_industry_nation(final java.lang.Integer pop_industry_nation) {
        this.pop_industry_nation = pop_industry_nation;
    }

    public final void setPop_industry_nation_DIRECT(final java.lang.Integer pop_industry_nation) {
        this.pop_industry_nation = pop_industry_nation;
    }

    @Column
    private java.lang.Integer pop_industry_pref;

    public java.lang.Integer getPop_industry_pref() {
        return this.pop_industry_pref;
    }

    public final java.lang.Integer getPop_industry_pref_DIRECT() {
        return this.pop_industry_pref;
    }

    public void setPop_industry_pref(final java.lang.Integer pop_industry_pref) {
        this.pop_industry_pref = pop_industry_pref;
    }

    public final void setPop_industry_pref_DIRECT(final java.lang.Integer pop_industry_pref) {
        this.pop_industry_pref = pop_industry_pref;
    }

    @Column
    private java.lang.Integer rank_industry_nation;

    public java.lang.Integer getRank_industry_nation() {
        return this.rank_industry_nation;
    }

    public final java.lang.Integer getRank_industry_nation_DIRECT() {
        return this.rank_industry_nation;
    }

    public void setRank_industry_nation(final java.lang.Integer rank_industry_nation) {
        this.rank_industry_nation = rank_industry_nation;
    }

    public final void setRank_industry_nation_DIRECT(final java.lang.Integer rank_industry_nation) {
        this.rank_industry_nation = rank_industry_nation;
    }

    @Column
    private java.lang.Integer rank_industry_pref;

    public java.lang.Integer getRank_industry_pref() {
        return this.rank_industry_pref;
    }

    public final java.lang.Integer getRank_industry_pref_DIRECT() {
        return this.rank_industry_pref;
    }

    public void setRank_industry_pref(final java.lang.Integer rank_industry_pref) {
        this.rank_industry_pref = rank_industry_pref;
    }

    public final void setRank_industry_pref_DIRECT(final java.lang.Integer rank_industry_pref) {
        this.rank_industry_pref = rank_industry_pref;
    }

    @Column
    private java.lang.Integer ref_industry_code;

    public java.lang.Integer getRef_industry_code() {
        return this.ref_industry_code;
    }

    public final java.lang.Integer getRef_industry_code_DIRECT() {
        return this.ref_industry_code;
    }

    public void setRef_industry_code(final java.lang.Integer ref_industry_code) {
        this.ref_industry_code = ref_industry_code;
    }

    public final void setRef_industry_code_DIRECT(final java.lang.Integer ref_industry_code) {
        this.ref_industry_code = ref_industry_code;
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
     * Set Acc_com_credit for Acc_com_industry_MAPPED.
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

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_COM_ENTITY", referencedColumnName = "ID_COM_ENTITY", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Acc_com_entity acc_com_entity;

    public arrow.businesstraceability.persistence.entity.Acc_com_entity getAcc_com_entity() {
        return this.acc_com_entity;
    }

    /**
     * Set Acc_com_entity for Acc_com_industry_MAPPED.
     *
     * @param acc_com_entity Acc_com_entity.
     *
     **/
    public void setAcc_com_entity(final arrow.businesstraceability.persistence.entity.Acc_com_entity acc_com_entity) {
        if (acc_com_entity == null) {
            throw new IllegalArgumentException(
                    "Param of Acc_com_industry_MAPPED.setAcc_com_entity(Acc_com_entity acc_com_entity) must not be null");
        }
        else {
            this.id_com_entity = acc_com_entity.getId_com_entity();
        }
        this.acc_com_entity = acc_com_entity;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}