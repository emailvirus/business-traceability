//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Acc_com_finance_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Acc_com_finance> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Acc_com_finance.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_finance Data type: int
         *
         */
        public Pk(final int id_finance) {
            this.id_finance = id_finance;
        }

        @Column(name = "ID_FINANCE")
        protected int id_finance;

        public int getId_finance() {
            return this.id_finance;
        }
    }

    //default constructor
    public Acc_com_finance_MAPPED() {
    }

    protected Acc_com_finance_MAPPED(final int id_finance) {
        this.checkFKConsistency(id_finance);
        this.pk = new Pk(id_finance);
        this.id_finance = id_finance;
    }

    private void checkFKConsistency(int id_finance) {
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
    protected int id_finance;

    public int getId_finance() {
        return this.id_finance;
    }

    //Normal columns

    @Column
    private java.lang.Integer current_profit;

    public java.lang.Integer getCurrent_profit() {
        return this.current_profit;
    }

    public final java.lang.Integer getCurrent_profit_DIRECT() {
        return this.current_profit;
    }

    public void setCurrent_profit(final java.lang.Integer current_profit) {
        this.current_profit = current_profit;
    }

    public final void setCurrent_profit_DIRECT(final java.lang.Integer current_profit) {
        this.current_profit = current_profit;
    }

    @Column
    private java.lang.Short flg_fstatement;

    public java.lang.Short getFlg_fstatement() {
        return this.flg_fstatement;
    }

    public final java.lang.Short getFlg_fstatement_DIRECT() {
        return this.flg_fstatement;
    }

    public void setFlg_fstatement(final java.lang.Short flg_fstatement) {
        this.flg_fstatement = flg_fstatement;
    }

    public final void setFlg_fstatement_DIRECT(final java.lang.Short flg_fstatement) {
        this.flg_fstatement = flg_fstatement;
    }

    @Column
    private java.lang.Integer gross_profit;

    public java.lang.Integer getGross_profit() {
        return this.gross_profit;
    }

    public final java.lang.Integer getGross_profit_DIRECT() {
        return this.gross_profit;
    }

    public void setGross_profit(final java.lang.Integer gross_profit) {
        this.gross_profit = gross_profit;
    }

    public final void setGross_profit_DIRECT(final java.lang.Integer gross_profit) {
        this.gross_profit = gross_profit;
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
    private java.lang.Integer m_fclose;

    public java.lang.Integer getM_fclose() {
        return this.m_fclose;
    }

    public final java.lang.Integer getM_fclose_DIRECT() {
        return this.m_fclose;
    }

    public void setM_fclose(final java.lang.Integer m_fclose) {
        this.m_fclose = m_fclose;
    }

    public final void setM_fclose_DIRECT(final java.lang.Integer m_fclose) {
        this.m_fclose = m_fclose;
    }

    @Column
    private java.lang.Integer op_profit;

    public java.lang.Integer getOp_profit() {
        return this.op_profit;
    }

    public final java.lang.Integer getOp_profit_DIRECT() {
        return this.op_profit;
    }

    public void setOp_profit(final java.lang.Integer op_profit) {
        this.op_profit = op_profit;
    }

    public final void setOp_profit_DIRECT(final java.lang.Integer op_profit) {
        this.op_profit = op_profit;
    }

    @Column
    private java.lang.Integer posttax_profit;

    public java.lang.Integer getPosttax_profit() {
        return this.posttax_profit;
    }

    public final java.lang.Integer getPosttax_profit_DIRECT() {
        return this.posttax_profit;
    }

    public void setPosttax_profit(final java.lang.Integer posttax_profit) {
        this.posttax_profit = posttax_profit;
    }

    public final void setPosttax_profit_DIRECT(final java.lang.Integer posttax_profit) {
        this.posttax_profit = posttax_profit;
    }

    @Column
    private java.lang.Integer pretax_profit;

    public java.lang.Integer getPretax_profit() {
        return this.pretax_profit;
    }

    public final java.lang.Integer getPretax_profit_DIRECT() {
        return this.pretax_profit;
    }

    public void setPretax_profit(final java.lang.Integer pretax_profit) {
        this.pretax_profit = pretax_profit;
    }

    public final void setPretax_profit_DIRECT(final java.lang.Integer pretax_profit) {
        this.pretax_profit = pretax_profit;
    }

    @Column
    private java.lang.Integer price_cost;

    public java.lang.Integer getPrice_cost() {
        return this.price_cost;
    }

    public final java.lang.Integer getPrice_cost_DIRECT() {
        return this.price_cost;
    }

    public void setPrice_cost(final java.lang.Integer price_cost) {
        this.price_cost = price_cost;
    }

    public final void setPrice_cost_DIRECT(final java.lang.Integer price_cost) {
        this.price_cost = price_cost;
    }

    @Column
    private java.lang.Integer r_capital;

    public java.lang.Integer getR_capital() {
        return this.r_capital;
    }

    public final java.lang.Integer getR_capital_DIRECT() {
        return this.r_capital;
    }

    public void setR_capital(final java.lang.Integer r_capital) {
        this.r_capital = r_capital;
    }

    public final void setR_capital_DIRECT(final java.lang.Integer r_capital) {
        this.r_capital = r_capital;
    }

    @Column
    private java.lang.Integer sales_amount;

    public java.lang.Integer getSales_amount() {
        return this.sales_amount;
    }

    public final java.lang.Integer getSales_amount_DIRECT() {
        return this.sales_amount;
    }

    public void setSales_amount(final java.lang.Integer sales_amount) {
        this.sales_amount = sales_amount;
    }

    public final void setSales_amount_DIRECT(final java.lang.Integer sales_amount) {
        this.sales_amount = sales_amount;
    }

    @Column
    private java.lang.Integer sga_cost;

    public java.lang.Integer getSga_cost() {
        return this.sga_cost;
    }

    public final java.lang.Integer getSga_cost_DIRECT() {
        return this.sga_cost;
    }

    public void setSga_cost(final java.lang.Integer sga_cost) {
        this.sga_cost = sga_cost;
    }

    public final void setSga_cost_DIRECT(final java.lang.Integer sga_cost) {
        this.sga_cost = sga_cost;
    }

    @Column
    private java.lang.Integer y_fclose;

    public java.lang.Integer getY_fclose() {
        return this.y_fclose;
    }

    public final java.lang.Integer getY_fclose_DIRECT() {
        return this.y_fclose;
    }

    public void setY_fclose(final java.lang.Integer y_fclose) {
        this.y_fclose = y_fclose;
    }

    public final void setY_fclose_DIRECT(final java.lang.Integer y_fclose) {
        this.y_fclose = y_fclose;
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
     * Set Acc_com_credit for Acc_com_finance_MAPPED.
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
     * Set Acc_com_entity for Acc_com_finance_MAPPED.
     *
     * @param acc_com_entity Acc_com_entity.
     *
     **/
    public void setAcc_com_entity(final arrow.businesstraceability.persistence.entity.Acc_com_entity acc_com_entity) {
        if (acc_com_entity == null) {
            throw new IllegalArgumentException(
                    "Param of Acc_com_finance_MAPPED.setAcc_com_entity(Acc_com_entity acc_com_entity) must not be null");
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