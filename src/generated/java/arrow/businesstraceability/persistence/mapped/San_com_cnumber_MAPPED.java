//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class San_com_cnumber_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.San_com_cnumber> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.San_com_cnumber.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_cnumber Data type: int
         *
         */
        public Pk(final int id_cnumber) {
            this.id_cnumber = id_cnumber;
        }

        @Column(name = "ID_CNUMBER")
        protected int id_cnumber;

        public int getId_cnumber() {
            return this.id_cnumber;
        }
    }

    //default constructor
    public San_com_cnumber_MAPPED() {
    }

    protected San_com_cnumber_MAPPED(final int id_cnumber) {
        this.checkFKConsistency(id_cnumber);
        this.pk = new Pk(id_cnumber);
        this.id_cnumber = id_cnumber;
    }

    private void checkFKConsistency(int id_cnumber) {
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
    protected int id_cnumber;

    public int getId_cnumber() {
        return this.id_cnumber;
    }

    //Normal columns

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
    private java.lang.String fax;

    public java.lang.String getFax() {
        return this.fax;
    }

    public final java.lang.String getFax_DIRECT() {
        return this.fax;
    }

    public void setFax(final java.lang.String fax) {
        this.fax = fax;
    }

    public final void setFax_DIRECT(final java.lang.String fax) {
        this.fax = fax;
    }

    @Column
    private java.lang.Integer id_branch;

    public java.lang.Integer getId_branch() {
        return this.id_branch;
    }

    public final java.lang.Integer getId_branch_DIRECT() {
        return this.id_branch;
    }

    public void setId_branch(final java.lang.Integer id_branch) {
        this.id_branch = id_branch;
    }

    public final void setId_branch_DIRECT(final java.lang.Integer id_branch) {
        this.id_branch = id_branch;
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

    @Column
    private java.lang.Integer id_site;

    public java.lang.Integer getId_site() {
        return this.id_site;
    }

    public final java.lang.Integer getId_site_DIRECT() {
        return this.id_site;
    }

    public void setId_site(final java.lang.Integer id_site) {
        this.id_site = id_site;
    }

    public final void setId_site_DIRECT(final java.lang.Integer id_site) {
        this.id_site = id_site;
    }

    @Column
    private int n_dupinfo;

    public int getN_dupinfo() {
        return this.n_dupinfo;
    }

    public final int getN_dupinfo_DIRECT() {
        return this.n_dupinfo;
    }

    public void setN_dupinfo(final int n_dupinfo) {
        this.n_dupinfo = n_dupinfo;
    }

    public final void setN_dupinfo_DIRECT(final int n_dupinfo) {
        this.n_dupinfo = n_dupinfo;
    }

    @Column
    private java.lang.String tel11;

    public java.lang.String getTel11() {
        return this.tel11;
    }

    public final java.lang.String getTel11_DIRECT() {
        return this.tel11;
    }

    public void setTel11(final java.lang.String tel11) {
        this.tel11 = tel11;
    }

    public final void setTel11_DIRECT(final java.lang.String tel11) {
        this.tel11 = tel11;
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
     * Set San_com_info for San_com_cnumber_MAPPED.
     *
     * @param san_com_info San_com_info.
     *
     **/
    public void setSan_com_info(final arrow.businesstraceability.persistence.entity.San_com_info san_com_info) {
        if (san_com_info == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_cnumber_MAPPED.setSan_com_info(San_com_info san_com_info) must not be null");
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
     * Set San_card_data for San_com_cnumber_MAPPED.
     *
     * @param san_card_data San_card_data.
     *
     **/
    public void setSan_card_data(final arrow.businesstraceability.persistence.entity.San_card_data san_card_data) {
        if (san_card_data == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_cnumber_MAPPED.setSan_card_data(San_card_data san_card_data) must not be null");
        }
        else {
            this.id_card = san_card_data.getId_card();
        }
        this.san_card_data = san_card_data;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_BRANCH", referencedColumnName = "ID_BRANCH", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.San_com_branch san_com_branch;

    public arrow.businesstraceability.persistence.entity.San_com_branch getSan_com_branch() {
        return this.san_com_branch;
    }

    /**
     * Set San_com_branch for San_com_cnumber_MAPPED.
     *
     * @param san_com_branch San_com_branch.
     *
     **/
    public void setSan_com_branch(final arrow.businesstraceability.persistence.entity.San_com_branch san_com_branch) {
        if (san_com_branch == null) {
            this.id_branch = null;
        }
        else {
            this.id_branch = san_com_branch.getId_branch();
        }
        this.san_com_branch = san_com_branch;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_SITE", referencedColumnName = "ID_SITE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.San_com_site san_com_site;

    public arrow.businesstraceability.persistence.entity.San_com_site getSan_com_site() {
        return this.san_com_site;
    }

    /**
     * Set San_com_site for San_com_cnumber_MAPPED.
     *
     * @param san_com_site San_com_site.
     *
     **/
    public void setSan_com_site(final arrow.businesstraceability.persistence.entity.San_com_site san_com_site) {
        if (san_com_site == null) {
            this.id_site = null;
        }
        else {
            this.id_site = san_com_site.getId_site();
        }
        this.san_com_site = san_com_site;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}