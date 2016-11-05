//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class San_com_site_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.San_com_site> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.San_com_site.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_site Data type: int
         *
         */
        public Pk(final int id_site) {
            this.id_site = id_site;
        }

        @Column(name = "ID_SITE")
        protected int id_site;

        public int getId_site() {
            return this.id_site;
        }
    }

    //default constructor
    public San_com_site_MAPPED() {
    }

    protected San_com_site_MAPPED(final int id_site) {
        this.checkFKConsistency(id_site);
        this.pk = new Pk(id_site);
        this.id_site = id_site;
    }

    private void checkFKConsistency(int id_site) {
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
    protected int id_site;

    public int getId_site() {
        return this.id_site;
    }

    //Normal columns

    @Column
    private java.lang.String addr_all;

    public java.lang.String getAddr_all() {
        return this.addr_all;
    }

    public final java.lang.String getAddr_all_DIRECT() {
        return this.addr_all;
    }

    public void setAddr_all(final java.lang.String addr_all) {
        this.addr_all = addr_all;
    }

    public final void setAddr_all_DIRECT(final java.lang.String addr_all) {
        this.addr_all = addr_all;
    }

    @Column
    private java.lang.String addr_bldg;

    public java.lang.String getAddr_bldg() {
        return this.addr_bldg;
    }

    public final java.lang.String getAddr_bldg_DIRECT() {
        return this.addr_bldg;
    }

    public void setAddr_bldg(final java.lang.String addr_bldg) {
        this.addr_bldg = addr_bldg;
    }

    public final void setAddr_bldg_DIRECT(final java.lang.String addr_bldg) {
        this.addr_bldg = addr_bldg;
    }

    @Column
    private java.lang.String addr_block;

    public java.lang.String getAddr_block() {
        return this.addr_block;
    }

    public final java.lang.String getAddr_block_DIRECT() {
        return this.addr_block;
    }

    public void setAddr_block(final java.lang.String addr_block) {
        this.addr_block = addr_block;
    }

    public final void setAddr_block_DIRECT(final java.lang.String addr_block) {
        this.addr_block = addr_block;
    }

    @Column
    private java.lang.String addr_city;

    public java.lang.String getAddr_city() {
        return this.addr_city;
    }

    public final java.lang.String getAddr_city_DIRECT() {
        return this.addr_city;
    }

    public void setAddr_city(final java.lang.String addr_city) {
        this.addr_city = addr_city;
    }

    public final void setAddr_city_DIRECT(final java.lang.String addr_city) {
        this.addr_city = addr_city;
    }

    @Column
    private java.lang.String addr_pref;

    public java.lang.String getAddr_pref() {
        return this.addr_pref;
    }

    public final java.lang.String getAddr_pref_DIRECT() {
        return this.addr_pref;
    }

    public void setAddr_pref(final java.lang.String addr_pref) {
        this.addr_pref = addr_pref;
    }

    public final void setAddr_pref_DIRECT(final java.lang.String addr_pref) {
        this.addr_pref = addr_pref;
    }

    @Column
    private java.lang.String code_zip;

    public java.lang.String getCode_zip() {
        return this.code_zip;
    }

    public final java.lang.String getCode_zip_DIRECT() {
        return this.code_zip;
    }

    public void setCode_zip(final java.lang.String code_zip) {
        this.code_zip = code_zip;
    }

    public final void setCode_zip_DIRECT(final java.lang.String code_zip) {
        this.code_zip = code_zip;
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
     * Set San_com_info for San_com_site_MAPPED.
     *
     * @param san_com_info San_com_info.
     *
     **/
    public void setSan_com_info(final arrow.businesstraceability.persistence.entity.San_com_info san_com_info) {
        if (san_com_info == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_site_MAPPED.setSan_com_info(San_com_info san_com_info) must not be null");
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
     * Set San_card_data for San_com_site_MAPPED.
     *
     * @param san_card_data San_card_data.
     *
     **/
    public void setSan_card_data(final arrow.businesstraceability.persistence.entity.San_card_data san_card_data) {
        if (san_card_data == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_site_MAPPED.setSan_card_data(San_card_data san_card_data) must not be null");
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
     * Set San_com_branch for San_com_site_MAPPED.
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

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}