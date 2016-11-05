//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class San_com_live_stat_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.San_com_live_stat> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.San_com_live_stat.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_company Data type: int
         *
         */
        public Pk(final int id_company) {
            this.id_company = id_company;
        }

        @Column(name = "ID_COMPANY")
        protected int id_company;

        public int getId_company() {
            return this.id_company;
        }
    }

    //default constructor
    public San_com_live_stat_MAPPED() {
    }

    protected San_com_live_stat_MAPPED(final arrow.businesstraceability.persistence.entity.San_com_info san_com_info) {
        this.checkFKConsistency(san_com_info);
        this.pk = new Pk(san_com_info.getId_company());
        this.san_com_info = san_com_info;
        this.id_company = san_com_info.getId_company();
    }

    private void checkFKConsistency(arrow.businesstraceability.persistence.entity.San_com_info san_com_info) {
        if (san_com_info == null) {
            throw new IllegalArgumentException("Parameter san_com_info must not be null");
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

    //Normal columns

    @Column
    private int cn_attr_correction;

    public int getCn_attr_correction() {
        return this.cn_attr_correction;
    }

    public final int getCn_attr_correction_DIRECT() {
        return this.cn_attr_correction;
    }

    public void setCn_attr_correction(final int cn_attr_correction) {
        this.cn_attr_correction = cn_attr_correction;
    }

    public final void setCn_attr_correction_DIRECT(final int cn_attr_correction) {
        this.cn_attr_correction = cn_attr_correction;
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
    private java.util.Date date_oldest_exchange;

    public java.util.Date getDate_oldest_exchange() {
        return this.date_oldest_exchange;
    }

    public final java.util.Date getDate_oldest_exchange_DIRECT() {
        return this.date_oldest_exchange;
    }

    public void setDate_oldest_exchange(final java.util.Date date_oldest_exchange) {
        this.date_oldest_exchange = date_oldest_exchange;
    }

    public final void setDate_oldest_exchange_DIRECT(final java.util.Date date_oldest_exchange) {
        this.date_oldest_exchange = date_oldest_exchange;
    }

    @Column
    private int id_latest_card;

    public int getId_latest_card() {
        return this.id_latest_card;
    }

    public final int getId_latest_card_DIRECT() {
        return this.id_latest_card;
    }

    public void setId_latest_card(final int id_latest_card) {
        this.id_latest_card = id_latest_card;
    }

    public final void setId_latest_card_DIRECT(final int id_latest_card) {
        this.id_latest_card = id_latest_card;
    }

    @Column
    private int id_max_cp_card;

    public int getId_max_cp_card() {
        return this.id_max_cp_card;
    }

    public final int getId_max_cp_card_DIRECT() {
        return this.id_max_cp_card;
    }

    public void setId_max_cp_card(final int id_max_cp_card) {
        this.id_max_cp_card = id_max_cp_card;
    }

    public final void setId_max_cp_card_DIRECT(final int id_max_cp_card) {
        this.id_max_cp_card = id_max_cp_card;
    }

    @Column
    private int id_max_vp_card;

    public int getId_max_vp_card() {
        return this.id_max_vp_card;
    }

    public final int getId_max_vp_card_DIRECT() {
        return this.id_max_vp_card;
    }

    public void setId_max_vp_card(final int id_max_vp_card) {
        this.id_max_vp_card = id_max_vp_card;
    }

    public final void setId_max_vp_card_DIRECT(final int id_max_vp_card) {
        this.id_max_vp_card = id_max_vp_card;
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
    private int sn_card;

    public int getSn_card() {
        return this.sn_card;
    }

    public final int getSn_card_DIRECT() {
        return this.sn_card;
    }

    public void setSn_card(final int sn_card) {
        this.sn_card = sn_card;
    }

    public final void setSn_card_DIRECT(final int sn_card) {
        this.sn_card = sn_card;
    }

    @Column
    private int sn_client;

    public int getSn_client() {
        return this.sn_client;
    }

    public final int getSn_client_DIRECT() {
        return this.sn_client;
    }

    public void setSn_client(final int sn_client) {
        this.sn_client = sn_client;
    }

    public final void setSn_client_DIRECT(final int sn_client) {
        this.sn_client = sn_client;
    }

    @Column
    private int sn_owner;

    public int getSn_owner() {
        return this.sn_owner;
    }

    public final int getSn_owner_DIRECT() {
        return this.sn_owner;
    }

    public void setSn_owner(final int sn_owner) {
        this.sn_owner = sn_owner;
    }

    public final void setSn_owner_DIRECT(final int sn_owner) {
        this.sn_owner = sn_owner;
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
     * Set San_com_info for San_com_live_stat_MAPPED.
     *
     * @param san_com_info San_com_info.
     *
     **/
    public void setSan_com_info(final arrow.businesstraceability.persistence.entity.San_com_info san_com_info) {
        if (san_com_info == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_live_stat_MAPPED.setSan_com_info(San_com_info san_com_info) must not be null");
        }
        else {
            this.id_company = san_com_info.getId_company();
        }
        this.san_com_info = san_com_info;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_LATEST_CARD", referencedColumnName = "ID_CARD", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.San_card_data latestCard;

    public arrow.businesstraceability.persistence.entity.San_card_data getLatestCard() {
        return this.latestCard;
    }

    /**
     * Set LatestCard for San_com_live_stat_MAPPED.
     *
     * @param latestCard San_card_data.
     *
     **/
    public void setLatestCard(final arrow.businesstraceability.persistence.entity.San_card_data latestCard) {
        if (latestCard == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_live_stat_MAPPED.setLatestCard(San_card_data latestCard) must not be null");
        }
        else {
            this.id_latest_card = latestCard.getId_card();
        }
        this.latestCard = latestCard;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_MAX_VP_CARD", referencedColumnName = "ID_CARD", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.San_card_data maxVPCard;

    public arrow.businesstraceability.persistence.entity.San_card_data getMaxVPCard() {
        return this.maxVPCard;
    }

    /**
     * Set MaxVPCard for San_com_live_stat_MAPPED.
     *
     * @param maxVPCard San_card_data.
     *
     **/
    public void setMaxVPCard(final arrow.businesstraceability.persistence.entity.San_card_data maxVPCard) {
        if (maxVPCard == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_live_stat_MAPPED.setMaxVPCard(San_card_data maxVPCard) must not be null");
        }
        else {
            this.id_max_vp_card = maxVPCard.getId_card();
        }
        this.maxVPCard = maxVPCard;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_MAX_CP_CARD", referencedColumnName = "ID_CARD", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.San_card_data maxCPCard;

    public arrow.businesstraceability.persistence.entity.San_card_data getMaxCPCard() {
        return this.maxCPCard;
    }

    /**
     * Set MaxCPCard for San_com_live_stat_MAPPED.
     *
     * @param maxCPCard San_card_data.
     *
     **/
    public void setMaxCPCard(final arrow.businesstraceability.persistence.entity.San_card_data maxCPCard) {
        if (maxCPCard == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_live_stat_MAPPED.setMaxCPCard(San_card_data maxCPCard) must not be null");
        }
        else {
            this.id_max_cp_card = maxCPCard.getId_card();
        }
        this.maxCPCard = maxCPCard;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}