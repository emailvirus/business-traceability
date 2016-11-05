//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class San_com_whole_card_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.San_com_whole_card> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.San_com_whole_card.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_card Data type: int
         *
         */
        public Pk(final int id_card) {
            this.id_card = id_card;
        }

        @Column(name = "ID_CARD")
        protected int id_card;

        public int getId_card() {
            return this.id_card;
        }
    }

    //default constructor
    public San_com_whole_card_MAPPED() {
    }

    protected San_com_whole_card_MAPPED(final arrow.businesstraceability.persistence.entity.San_card_data san_card_data) {
        this.checkFKConsistency(san_card_data);
        this.pk = new Pk(san_card_data.getId_card());
        this.san_card_data = san_card_data;
        this.id_card = san_card_data.getId_card();
    }

    private void checkFKConsistency(arrow.businesstraceability.persistence.entity.San_card_data san_card_data) {
        if (san_card_data == null) {
            throw new IllegalArgumentException("Parameter san_card_data must not be null");
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
    protected int id_card;

    public int getId_card() {
        return this.id_card;
    }

    //Normal columns

    @Column
    private java.lang.String ac_user;

    public java.lang.String getAc_user() {
        return this.ac_user;
    }

    public final java.lang.String getAc_user_DIRECT() {
        return this.ac_user;
    }

    public void setAc_user(final java.lang.String ac_user) {
        this.ac_user = ac_user;
    }

    public final void setAc_user_DIRECT(final java.lang.String ac_user) {
        this.ac_user = ac_user;
    }

    @Column
    private java.util.Date date_card_deletion;

    public java.util.Date getDate_card_deletion() {
        return this.date_card_deletion;
    }

    public final java.util.Date getDate_card_deletion_DIRECT() {
        return this.date_card_deletion;
    }

    public void setDate_card_deletion(final java.util.Date date_card_deletion) {
        this.date_card_deletion = date_card_deletion;
    }

    public final void setDate_card_deletion_DIRECT(final java.util.Date date_card_deletion) {
        this.date_card_deletion = date_card_deletion;
    }

    @Column
    private java.util.Date date_exchange;

    public java.util.Date getDate_exchange() {
        return this.date_exchange;
    }

    public final java.util.Date getDate_exchange_DIRECT() {
        return this.date_exchange;
    }

    public void setDate_exchange(final java.util.Date date_exchange) {
        this.date_exchange = date_exchange;
    }

    public final void setDate_exchange_DIRECT(final java.util.Date date_exchange) {
        this.date_exchange = date_exchange;
    }

    @Column
    private java.util.Date date_register;

    public java.util.Date getDate_register() {
        return this.date_register;
    }

    public final java.util.Date getDate_register_DIRECT() {
        return this.date_register;
    }

    public void setDate_register(final java.util.Date date_register) {
        this.date_register = date_register;
    }

    public final void setDate_register_DIRECT(final java.util.Date date_register) {
        this.date_register = date_register;
    }

    @Column
    private java.lang.String flg_card_deletion;

    public java.lang.String getFlg_card_deletion() {
        return this.flg_card_deletion;
    }

    public final java.lang.String getFlg_card_deletion_DIRECT() {
        return this.flg_card_deletion;
    }

    public void setFlg_card_deletion(final java.lang.String flg_card_deletion) {
        this.flg_card_deletion = flg_card_deletion;
    }

    public final void setFlg_card_deletion_DIRECT(final java.lang.String flg_card_deletion) {
        this.flg_card_deletion = flg_card_deletion;
    }

    @Column
    private int id_company;

    public int getId_company() {
        return this.id_company;
    }

    public final int getId_company_DIRECT() {
        return this.id_company;
    }

    public void setId_company(final int id_company) {
        this.id_company = id_company;
    }

    public final void setId_company_DIRECT(final int id_company) {
        this.id_company = id_company;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_COMPANY", referencedColumnName = "ID_COMPANY", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.San_com_info san_com_info;

    public arrow.businesstraceability.persistence.entity.San_com_info getSan_com_info() {
        return this.san_com_info;
    }

    /**
     * Set San_com_info for San_com_whole_card_MAPPED.
     *
     * @param san_com_info San_com_info.
     *
     **/
    public void setSan_com_info(final arrow.businesstraceability.persistence.entity.San_com_info san_com_info) {
        if (san_com_info == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_whole_card_MAPPED.setSan_com_info(San_com_info san_com_info) must not be null");
        }
        else {
            this.id_company = san_com_info.getId_company();
        }
        this.san_com_info = san_com_info;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_CARD", referencedColumnName = "ID_CARD", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.San_card_data san_card_data;

    public arrow.businesstraceability.persistence.entity.San_card_data getSan_card_data() {
        return this.san_card_data;
    }

    /**
     * Set San_card_data for San_com_whole_card_MAPPED.
     *
     * @param san_card_data San_card_data.
     *
     **/
    public void setSan_card_data(final arrow.businesstraceability.persistence.entity.San_card_data san_card_data) {
        if (san_card_data == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_whole_card_MAPPED.setSan_card_data(San_card_data san_card_data) must not be null");
        }
        else {
            this.id_card = san_card_data.getId_card();
        }
        this.san_card_data = san_card_data;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}