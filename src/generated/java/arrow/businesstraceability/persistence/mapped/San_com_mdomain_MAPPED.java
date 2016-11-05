//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class San_com_mdomain_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.San_com_mdomain> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.San_com_mdomain.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_maildomain Data type: int
         *
         */
        public Pk(final int id_maildomain) {
            this.id_maildomain = id_maildomain;
        }

        @Column(name = "ID_MAILDOMAIN")
        protected int id_maildomain;

        public int getId_maildomain() {
            return this.id_maildomain;
        }
    }

    //default constructor
    public San_com_mdomain_MAPPED() {
    }

    protected San_com_mdomain_MAPPED(final int id_maildomain) {
        this.checkFKConsistency(id_maildomain);
        this.pk = new Pk(id_maildomain);
        this.id_maildomain = id_maildomain;
    }

    private void checkFKConsistency(int id_maildomain) {
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
    protected int id_maildomain;

    public int getId_maildomain() {
        return this.id_maildomain;
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
    private java.lang.String email_domain;

    public java.lang.String getEmail_domain() {
        return this.email_domain;
    }

    public final java.lang.String getEmail_domain_DIRECT() {
        return this.email_domain;
    }

    public void setEmail_domain(final java.lang.String email_domain) {
        this.email_domain = email_domain;
    }

    public final void setEmail_domain_DIRECT(final java.lang.String email_domain) {
        this.email_domain = email_domain;
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
     * Set San_com_info for San_com_mdomain_MAPPED.
     *
     * @param san_com_info San_com_info.
     *
     **/
    public void setSan_com_info(final arrow.businesstraceability.persistence.entity.San_com_info san_com_info) {
        if (san_com_info == null) {
            throw new IllegalArgumentException(
                    "Param of San_com_mdomain_MAPPED.setSan_com_info(San_com_info san_com_info) must not be null");
        }
        else {
            this.id_company = san_com_info.getId_company();
        }
        this.san_com_info = san_com_info;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}