//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class San_card_tag_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.San_card_tag> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.San_card_tag.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_tagtbl Data type: int
         *
         */
        public Pk(final int id_tagtbl) {
            this.id_tagtbl = id_tagtbl;
        }

        @Column(name = "ID_TAGTBL")
        protected int id_tagtbl;

        public int getId_tagtbl() {
            return this.id_tagtbl;
        }
    }

    //default constructor
    public San_card_tag_MAPPED() {
    }

    protected San_card_tag_MAPPED(final int id_tagtbl) {
        this.checkFKConsistency(id_tagtbl);
        this.pk = new Pk(id_tagtbl);
        this.id_tagtbl = id_tagtbl;
    }

    private void checkFKConsistency(int id_tagtbl) {
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
    protected int id_tagtbl;

    public int getId_tagtbl() {
        return this.id_tagtbl;
    }

    //Normal columns

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
    private java.lang.Integer id_company;

    public java.lang.Integer getId_company() {
        return this.id_company;
    }

    public final java.lang.Integer getId_company_DIRECT() {
        return this.id_company;
    }

    public void setId_company(final java.lang.Integer id_company) {
        this.id_company = id_company;
    }

    public final void setId_company_DIRECT(final java.lang.Integer id_company) {
        this.id_company = id_company;
    }

    @Column
    private java.lang.String tag;

    public java.lang.String getTag() {
        return this.tag;
    }

    public final java.lang.String getTag_DIRECT() {
        return this.tag;
    }

    public void setTag(final java.lang.String tag) {
        this.tag = tag;
    }

    public final void setTag_DIRECT(final java.lang.String tag) {
        this.tag = tag;
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
     * Set San_com_info for San_card_tag_MAPPED.
     *
     * @param san_com_info San_com_info.
     *
     **/
    public void setSan_com_info(final arrow.businesstraceability.persistence.entity.San_com_info san_com_info) {
        if (san_com_info == null) {
            this.id_company = null;
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
     * Set San_card_data for San_card_tag_MAPPED.
     *
     * @param san_card_data San_card_data.
     *
     **/
    public void setSan_card_data(final arrow.businesstraceability.persistence.entity.San_card_data san_card_data) {
        if (san_card_data == null) {
            throw new IllegalArgumentException(
                    "Param of San_card_tag_MAPPED.setSan_card_data(San_card_data san_card_data) must not be null");
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