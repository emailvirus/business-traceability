//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class San_com_live_owner_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.San_com_live_owner> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.San_com_live_owner.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_company Data type: int
         * @param ac_user Data type: java.lang.String
         *
         */
        public Pk(final int id_company, final java.lang.String ac_user) {
            this.id_company = id_company;
            this.ac_user = ac_user;
        }

        @Column(name = "ID_COMPANY")
        protected int id_company;

        public int getId_company() {
            return this.id_company;
        }

        @Column(name = "AC_USER")
        protected java.lang.String ac_user;

        public java.lang.String getAc_user() {
            return this.ac_user;
        }
    }

    //default constructor
    public San_com_live_owner_MAPPED() {
    }

    protected San_com_live_owner_MAPPED(final arrow.businesstraceability.persistence.entity.San_com_info san_com_info, final java.lang.String ac_user) {
        this.checkFKConsistency(san_com_info, ac_user);
        this.pk = new Pk(san_com_info.getId_company(), ac_user);
        this.san_com_info = san_com_info;
        this.ac_user = ac_user;
        this.id_company = san_com_info.getId_company();
    }

    private void checkFKConsistency(arrow.businesstraceability.persistence.entity.San_com_info san_com_info, java.lang.String ac_user) {
        if (san_com_info == null) {
            throw new IllegalArgumentException("Parameter san_com_info must not be null");
        }
        if (ac_user == null) {
            throw new IllegalArgumentException("Parameter ac_user must not be null");
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
    protected int id_company;

    public int getId_company() {
        return this.id_company;
    }

    @Column(insertable = false, updatable = false)
    protected java.lang.String ac_user;

    public java.lang.String getAc_user() {
        return this.ac_user;
    }

    //Normal columns

    @Column
    private int cn_card_perowner;

    public int getCn_card_perowner() {
        return this.cn_card_perowner;
    }

    public final int getCn_card_perowner_DIRECT() {
        return this.cn_card_perowner;
    }

    public void setCn_card_perowner(final int cn_card_perowner) {
        this.cn_card_perowner = cn_card_perowner;
    }

    public final void setCn_card_perowner_DIRECT(final int cn_card_perowner) {
        this.cn_card_perowner = cn_card_perowner;
    }

    @Column
    private int cn_cp;

    public int getCn_cp() {
        return this.cn_cp;
    }

    public final int getCn_cp_DIRECT() {
        return this.cn_cp;
    }

    public void setCn_cp(final int cn_cp) {
        this.cn_cp = cn_cp;
    }

    public final void setCn_cp_DIRECT(final int cn_cp) {
        this.cn_cp = cn_cp;
    }

    @Column
    private int cn_vp;

    public int getCn_vp() {
        return this.cn_vp;
    }

    public final int getCn_vp_DIRECT() {
        return this.cn_vp;
    }

    public void setCn_vp(final int cn_vp) {
        this.cn_vp = cn_vp;
    }

    public final void setCn_vp_DIRECT(final int cn_vp) {
        this.cn_vp = cn_vp;
    }

    @Column
    private java.util.Date date_latest_exchange;

    public java.util.Date getDate_latest_exchange() {
        return this.date_latest_exchange;
    }

    public final java.util.Date getDate_latest_exchange_DIRECT() {
        return this.date_latest_exchange;
    }

    public void setDate_latest_exchange(final java.util.Date date_latest_exchange) {
        this.date_latest_exchange = date_latest_exchange;
    }

    public final void setDate_latest_exchange_DIRECT(final java.util.Date date_latest_exchange) {
        this.date_latest_exchange = date_latest_exchange;
    }

    @Column
    private int id_card;

    public int getId_card() {
        return this.id_card;
    }

    public final int getId_card_DIRECT() {
        return this.id_card;
    }

    public void setId_card(final int id_card) {
        this.id_card = id_card;
    }

    public final void setId_card_DIRECT(final int id_card) {
        this.id_card = id_card;
    }

    @Column
    private int maxn_cp;

    public int getMaxn_cp() {
        return this.maxn_cp;
    }

    public final int getMaxn_cp_DIRECT() {
        return this.maxn_cp;
    }

    public void setMaxn_cp(final int maxn_cp) {
        this.maxn_cp = maxn_cp;
    }

    public final void setMaxn_cp_DIRECT(final int maxn_cp) {
        this.maxn_cp = maxn_cp;
    }

    @Column
    private int maxn_vp;

    public int getMaxn_vp() {
        return this.maxn_vp;
    }

    public final int getMaxn_vp_DIRECT() {
        return this.maxn_vp;
    }

    public void setMaxn_vp(final int maxn_vp) {
        this.maxn_vp = maxn_vp;
    }

    public final void setMaxn_vp_DIRECT(final int maxn_vp) {
        this.maxn_vp = maxn_vp;
    }

    @Column
    private java.lang.String name_user;

    public java.lang.String getName_user() {
        return this.name_user;
    }

    public final java.lang.String getName_user_DIRECT() {
        return this.name_user;
    }

    public void setName_user(final java.lang.String name_user) {
        this.name_user = name_user;
    }

    public final void setName_user_DIRECT(final java.lang.String name_user) {
        this.name_user = name_user;
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
     * Set San_com_info for San_com_live_owner_MAPPED.
     *
     * @param san_com_info San_com_info.
     *
     **/
    public void setSan_com_info(final arrow.businesstraceability.persistence.entity.San_com_info san_com_info) {
        if (san_com_info == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_live_owner_MAPPED.setSan_com_info(San_com_info san_com_info) must not be null");
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
     * Set San_card_data for San_com_live_owner_MAPPED.
     *
     * @param san_card_data San_card_data.
     *
     **/
    public void setSan_card_data(final arrow.businesstraceability.persistence.entity.San_card_data san_card_data) {
        if (san_card_data == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_live_owner_MAPPED.setSan_card_data(San_card_data san_card_data) must not be null");
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