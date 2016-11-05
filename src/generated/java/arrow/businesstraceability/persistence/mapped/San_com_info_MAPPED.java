//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class San_com_info_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.San_com_info> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.San_com_info.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_san_company Data type: java.lang.String
         *
         */
        public Pk(final java.lang.String id_san_company) {
            this.id_san_company = id_san_company;
        }

        @Column(name = "ID_SAN_COMPANY")
        protected java.lang.String id_san_company;

        public java.lang.String getId_san_company() {
            return this.id_san_company;
        }
    }

    //default constructor
    public San_com_info_MAPPED() {
    }

    protected San_com_info_MAPPED(final java.lang.String id_san_company) {
        this.checkFKConsistency(id_san_company);
        this.pk = new Pk(id_san_company);
        this.id_san_company = id_san_company;
    }

    private void checkFKConsistency(java.lang.String id_san_company) {
        if (id_san_company == null) {
            throw new IllegalArgumentException("Parameter id_san_company must not be null");
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
    protected java.lang.String id_san_company;

    public java.lang.String getId_san_company() {
        return this.id_san_company;
    }

    //Normal columns

    @Column
    private int cn_update;

    public int getCn_update() {
        return this.cn_update;
    }

    public final int getCn_update_DIRECT() {
        return this.cn_update;
    }

    public void setCn_update(final int cn_update) {
        this.cn_update = cn_update;
    }

    public final void setCn_update_DIRECT(final int cn_update) {
        this.cn_update = cn_update;
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
    private java.lang.String id_sarscom;

    public java.lang.String getId_sarscom() {
        return this.id_sarscom;
    }

    public final java.lang.String getId_sarscom_DIRECT() {
        return this.id_sarscom;
    }

    public void setId_sarscom(final java.lang.String id_sarscom) {
        this.id_sarscom = id_sarscom;
    }

    public final void setId_sarscom_DIRECT(final java.lang.String id_sarscom) {
        this.id_sarscom = id_sarscom;
    }

    @Column
    private java.lang.String mynum_com;

    public java.lang.String getMynum_com() {
        return this.mynum_com;
    }

    public final java.lang.String getMynum_com_DIRECT() {
        return this.mynum_com;
    }

    public void setMynum_com(final java.lang.String mynum_com) {
        this.mynum_com = mynum_com;
    }

    public final void setMynum_com_DIRECT(final java.lang.String mynum_com) {
        this.mynum_com = mynum_com;
    }

    @Column
    private java.lang.String name_company;

    public java.lang.String getName_company() {
        return this.name_company;
    }

    public final java.lang.String getName_company_DIRECT() {
        return this.name_company;
    }

    public void setName_company(final java.lang.String name_company) {
        this.name_company = name_company;
    }

    public final void setName_company_DIRECT(final java.lang.String name_company) {
        this.name_company = name_company;
    }

    @Column
    private java.lang.String name_com_eng;

    public java.lang.String getName_com_eng() {
        return this.name_com_eng;
    }

    public final java.lang.String getName_com_eng_DIRECT() {
        return this.name_com_eng;
    }

    public void setName_com_eng(final java.lang.String name_com_eng) {
        this.name_com_eng = name_com_eng;
    }

    public final void setName_com_eng_DIRECT(final java.lang.String name_com_eng) {
        this.name_com_eng = name_com_eng;
    }

    @Column
    private java.lang.String name_com_kana;

    public java.lang.String getName_com_kana() {
        return this.name_com_kana;
    }

    public final java.lang.String getName_com_kana_DIRECT() {
        return this.name_com_kana;
    }

    public void setName_com_kana(final java.lang.String name_com_kana) {
        this.name_com_kana = name_com_kana;
    }

    public final void setName_com_kana_DIRECT(final java.lang.String name_com_kana) {
        this.name_com_kana = name_com_kana;
    }

    @Column
    private java.util.Date ts_create;

    public java.util.Date getTs_create() {
        return this.ts_create;
    }

    public final java.util.Date getTs_create_DIRECT() {
        return this.ts_create;
    }

    public void setTs_create(final java.util.Date ts_create) {
        this.ts_create = ts_create;
    }

    public final void setTs_create_DIRECT(final java.util.Date ts_create) {
        this.ts_create = ts_create;
    }

    @Column
    private java.util.Date ts_export;

    public java.util.Date getTs_export() {
        return this.ts_export;
    }

    public final java.util.Date getTs_export_DIRECT() {
        return this.ts_export;
    }

    public void setTs_export(final java.util.Date ts_export) {
        this.ts_export = ts_export;
    }

    public final void setTs_export_DIRECT(final java.util.Date ts_export) {
        this.ts_export = ts_export;
    }

    @Column
    private java.util.Date ts_update;

    public java.util.Date getTs_update() {
        return this.ts_update;
    }

    public final java.util.Date getTs_update_DIRECT() {
        return this.ts_update;
    }

    public void setTs_update(final java.util.Date ts_update) {
        this.ts_update = ts_update;
    }

    public final void setTs_update_DIRECT(final java.util.Date ts_update) {
        this.ts_update = ts_update;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_SARSCOM", referencedColumnName = "COM_COMPANY_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Company_mst company_mst;

    public arrow.businesstraceability.persistence.entity.Company_mst getCompany_mst() {
        return this.company_mst;
    }

    /**
     * Set Company_mst for San_com_info_MAPPED.
     *
     * @param company_mst Company_mst.
     *
     **/
    public void setCompany_mst(final arrow.businesstraceability.persistence.entity.Company_mst company_mst) {
        if (company_mst == null) {
            this.id_sarscom = null;
        }
        else {
            this.id_sarscom = company_mst.getCom_company_code();
        }
        this.company_mst = company_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}