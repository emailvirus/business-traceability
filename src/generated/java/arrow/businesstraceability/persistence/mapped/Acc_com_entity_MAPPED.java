//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Acc_com_entity_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Acc_com_entity> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Acc_com_entity.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_com_entity Data type: int
         *
         */
        public Pk(final int id_com_entity) {
            this.id_com_entity = id_com_entity;
        }

        @Column(name = "ID_COM_ENTITY")
        protected int id_com_entity;

        public int getId_com_entity() {
            return this.id_com_entity;
        }
    }

    //default constructor
    public Acc_com_entity_MAPPED() {
    }

    protected Acc_com_entity_MAPPED(final int id_com_entity) {
        this.checkFKConsistency(id_com_entity);
        this.pk = new Pk(id_com_entity);
        this.id_com_entity = id_com_entity;
    }

    private void checkFKConsistency(final int id_com_entity) {
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
    protected int id_com_entity;

    public int getId_com_entity() {
        return this.id_com_entity;
    }

    //Normal columns

    @Column
    private java.lang.Integer ac_creation;

    public java.lang.Integer getAc_creation() {
        return this.ac_creation;
    }

    public final java.lang.Integer getAc_creation_DIRECT() {
        return this.ac_creation;
    }

    public void setAc_creation(final java.lang.Integer ac_creation) {
        this.ac_creation = ac_creation;
    }

    public final void setAc_creation_DIRECT(final java.lang.Integer ac_creation) {
        this.ac_creation = ac_creation;
    }

    @Column
    private java.lang.Integer ac_update;

    public java.lang.Integer getAc_update() {
        return this.ac_update;
    }

    public final java.lang.Integer getAc_update_DIRECT() {
        return this.ac_update;
    }

    public void setAc_update(final java.lang.Integer ac_update) {
        this.ac_update = ac_update;
    }

    public final void setAc_update_DIRECT(final java.lang.Integer ac_update) {
        this.ac_update = ac_update;
    }

    @Column
    private java.lang.String code_acc_client;

    public java.lang.String getCode_acc_client() {
        return this.code_acc_client;
    }

    public final java.lang.String getCode_acc_client_DIRECT() {
        return this.code_acc_client;
    }

    public void setCode_acc_client(final java.lang.String code_acc_client) {
        this.code_acc_client = code_acc_client;
    }

    public final void setCode_acc_client_DIRECT(final java.lang.String code_acc_client) {
        this.code_acc_client = code_acc_client;
    }

    @Column
    private java.util.Date date_creation;

    public java.util.Date getDate_creation() {
        return this.date_creation;
    }

    public final java.util.Date getDate_creation_DIRECT() {
        return this.date_creation;
    }

    public void setDate_creation(final java.util.Date date_creation) {
        this.date_creation = date_creation;
    }

    public final void setDate_creation_DIRECT(final java.util.Date date_creation) {
        this.date_creation = date_creation;
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
    private java.lang.Integer id_int_san_company;

    public java.lang.Integer getId_int_san_company() {
        return this.id_int_san_company;
    }

    public final java.lang.Integer getId_int_san_company_DIRECT() {
        return this.id_int_san_company;
    }

    public void setId_int_san_company(final java.lang.Integer id_int_san_company) {
        this.id_int_san_company = id_int_san_company;
    }

    public final void setId_int_san_company_DIRECT(final java.lang.Integer id_int_san_company) {
        this.id_int_san_company = id_int_san_company;
    }

    @Column
    private java.lang.String indx_customer;

    public java.lang.String getIndx_customer() {
        return this.indx_customer;
    }

    public final java.lang.String getIndx_customer_DIRECT() {
        return this.indx_customer;
    }

    public void setIndx_customer(final java.lang.String indx_customer) {
        this.indx_customer = indx_customer;
    }

    public final void setIndx_customer_DIRECT(final java.lang.String indx_customer) {
        this.indx_customer = indx_customer;
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

    @Column
    private java.lang.Integer y_establish;

    public java.lang.Integer getY_establish() {
        return this.y_establish;
    }

    public final java.lang.Integer getY_establish_DIRECT() {
        return this.y_establish;
    }

    public void setY_establish(final java.lang.Integer y_establish) {
        this.y_establish = y_establish;
    }

    public final void setY_establish_DIRECT(final java.lang.Integer y_establish) {
        this.y_establish = y_establish;
    }

    @Column
    private java.lang.Integer y_start;

    public java.lang.Integer getY_start() {
        return this.y_start;
    }

    public final java.lang.Integer getY_start_DIRECT() {
        return this.y_start;
    }

    public void setY_start(final java.lang.Integer y_start) {
        this.y_start = y_start;
    }

    public final void setY_start_DIRECT(final java.lang.Integer y_start) {
        this.y_start = y_start;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_INT_SAN_COMPANY", referencedColumnName = "ID_COMPANY", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.San_com_info san_com_info;

    public arrow.businesstraceability.persistence.entity.San_com_info getSan_com_info() {
        return this.san_com_info;
    }

    /**
     * Set San_com_info for Acc_com_entity_MAPPED.
     *
     * @param san_com_info San_com_info.
     *
     **/
    public void setSan_com_info(final arrow.businesstraceability.persistence.entity.San_com_info san_com_info) {
        if (san_com_info == null) {
            this.id_int_san_company = null;
        }
        else {
            this.id_int_san_company = san_com_info.getId_company();
        }
        this.san_com_info = san_com_info;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */


    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}