//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class San_card_data_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.San_card_data> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.San_card_data.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_san_card Data type: java.lang.String
         *
         */
        public Pk(final java.lang.String id_san_card) {
            this.id_san_card = id_san_card;
        }

        @Column(name = "ID_SAN_CARD")
        protected java.lang.String id_san_card;

        public java.lang.String getId_san_card() {
            return this.id_san_card;
        }
    }

    //default constructor
    public San_card_data_MAPPED() {
    }

    protected San_card_data_MAPPED(final java.lang.String id_san_card) {
        this.checkFKConsistency(id_san_card);
        this.pk = new Pk(id_san_card);
        this.id_san_card = id_san_card;
    }

    private void checkFKConsistency(java.lang.String id_san_card) {
        if (id_san_card == null) {
            throw new IllegalArgumentException("Parameter id_san_card must not be null");
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
    protected java.lang.String id_san_card;

    public java.lang.String getId_san_card() {
        return this.id_san_card;
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
    private java.lang.String addr_all2;

    public java.lang.String getAddr_all2() {
        return this.addr_all2;
    }

    public final java.lang.String getAddr_all2_DIRECT() {
        return this.addr_all2;
    }

    public void setAddr_all2(final java.lang.String addr_all2) {
        this.addr_all2 = addr_all2;
    }

    public final void setAddr_all2_DIRECT(final java.lang.String addr_all2) {
        this.addr_all2 = addr_all2;
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
    private java.lang.String addr_bldg2;

    public java.lang.String getAddr_bldg2() {
        return this.addr_bldg2;
    }

    public final java.lang.String getAddr_bldg2_DIRECT() {
        return this.addr_bldg2;
    }

    public void setAddr_bldg2(final java.lang.String addr_bldg2) {
        this.addr_bldg2 = addr_bldg2;
    }

    public final void setAddr_bldg2_DIRECT(final java.lang.String addr_bldg2) {
        this.addr_bldg2 = addr_bldg2;
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
    private java.lang.String addr_block2;

    public java.lang.String getAddr_block2() {
        return this.addr_block2;
    }

    public final java.lang.String getAddr_block2_DIRECT() {
        return this.addr_block2;
    }

    public void setAddr_block2(final java.lang.String addr_block2) {
        this.addr_block2 = addr_block2;
    }

    public final void setAddr_block2_DIRECT(final java.lang.String addr_block2) {
        this.addr_block2 = addr_block2;
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
    private java.lang.String addr_city2;

    public java.lang.String getAddr_city2() {
        return this.addr_city2;
    }

    public final java.lang.String getAddr_city2_DIRECT() {
        return this.addr_city2;
    }

    public void setAddr_city2(final java.lang.String addr_city2) {
        this.addr_city2 = addr_city2;
    }

    public final void setAddr_city2_DIRECT(final java.lang.String addr_city2) {
        this.addr_city2 = addr_city2;
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
    private java.lang.String addr_pref2;

    public java.lang.String getAddr_pref2() {
        return this.addr_pref2;
    }

    public final java.lang.String getAddr_pref2_DIRECT() {
        return this.addr_pref2;
    }

    public void setAddr_pref2(final java.lang.String addr_pref2) {
        this.addr_pref2 = addr_pref2;
    }

    public final void setAddr_pref2_DIRECT(final java.lang.String addr_pref2) {
        this.addr_pref2 = addr_pref2;
    }

    @Column
    private java.lang.String automemo;

    public java.lang.String getAutomemo() {
        return this.automemo;
    }

    public final java.lang.String getAutomemo_DIRECT() {
        return this.automemo;
    }

    public void setAutomemo(final java.lang.String automemo) {
        this.automemo = automemo;
    }

    public final void setAutomemo_DIRECT(final java.lang.String automemo) {
        this.automemo = automemo;
    }

    @Column
    private int cn_apiimport;

    public int getCn_apiimport() {
        return this.cn_apiimport;
    }

    public final int getCn_apiimport_DIRECT() {
        return this.cn_apiimport;
    }

    public void setCn_apiimport(final int cn_apiimport) {
        this.cn_apiimport = cn_apiimport;
    }

    public final void setCn_apiimport_DIRECT(final int cn_apiimport) {
        this.cn_apiimport = cn_apiimport;
    }

    @Column
    private int cn_cardinfo_update;

    public int getCn_cardinfo_update() {
        return this.cn_cardinfo_update;
    }

    public final int getCn_cardinfo_update_DIRECT() {
        return this.cn_cardinfo_update;
    }

    public void setCn_cardinfo_update(final int cn_cardinfo_update) {
        this.cn_cardinfo_update = cn_cardinfo_update;
    }

    public final void setCn_cardinfo_update_DIRECT(final int cn_cardinfo_update) {
        this.cn_cardinfo_update = cn_cardinfo_update;
    }

    @Column
    private int cn_comid_update;

    public int getCn_comid_update() {
        return this.cn_comid_update;
    }

    public final int getCn_comid_update_DIRECT() {
        return this.cn_comid_update;
    }

    public void setCn_comid_update(final int cn_comid_update) {
        this.cn_comid_update = cn_comid_update;
    }

    public final void setCn_comid_update_DIRECT(final int cn_comid_update) {
        this.cn_comid_update = cn_comid_update;
    }

    @Column
    private int cn_fileimport;

    public int getCn_fileimport() {
        return this.cn_fileimport;
    }

    public final int getCn_fileimport_DIRECT() {
        return this.cn_fileimport;
    }

    public void setCn_fileimport(final int cn_fileimport) {
        this.cn_fileimport = cn_fileimport;
    }

    public final void setCn_fileimport_DIRECT(final int cn_fileimport) {
        this.cn_fileimport = cn_fileimport;
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
    private java.lang.String cont_cat;

    public java.lang.String getCont_cat() {
        return this.cont_cat;
    }

    public final java.lang.String getCont_cat_DIRECT() {
        return this.cont_cat;
    }

    public void setCont_cat(final java.lang.String cont_cat) {
        this.cont_cat = cont_cat;
    }

    public final void setCont_cat_DIRECT(final java.lang.String cont_cat) {
        this.cont_cat = cont_cat;
    }

    @Column
    private java.lang.String cont_date;

    public java.lang.String getCont_date() {
        return this.cont_date;
    }

    public final java.lang.String getCont_date_DIRECT() {
        return this.cont_date;
    }

    public void setCont_date(final java.lang.String cont_date) {
        this.cont_date = cont_date;
    }

    public final void setCont_date_DIRECT(final java.lang.String cont_date) {
        this.cont_date = cont_date;
    }

    @Column
    private java.lang.String cont_location;

    public java.lang.String getCont_location() {
        return this.cont_location;
    }

    public final java.lang.String getCont_location_DIRECT() {
        return this.cont_location;
    }

    public void setCont_location(final java.lang.String cont_location) {
        this.cont_location = cont_location;
    }

    public final void setCont_location_DIRECT(final java.lang.String cont_location) {
        this.cont_location = cont_location;
    }

    @Column
    private java.lang.String cont_memo;

    public java.lang.String getCont_memo() {
        return this.cont_memo;
    }

    public final java.lang.String getCont_memo_DIRECT() {
        return this.cont_memo;
    }

    public void setCont_memo(final java.lang.String cont_memo) {
        this.cont_memo = cont_memo;
    }

    public final void setCont_memo_DIRECT(final java.lang.String cont_memo) {
        this.cont_memo = cont_memo;
    }

    @Column
    private java.lang.String cont_title;

    public java.lang.String getCont_title() {
        return this.cont_title;
    }

    public final java.lang.String getCont_title_DIRECT() {
        return this.cont_title;
    }

    public void setCont_title(final java.lang.String cont_title) {
        this.cont_title = cont_title;
    }

    public final void setCont_title_DIRECT(final java.lang.String cont_title) {
        this.cont_title = cont_title;
    }

    @Column
    private java.lang.Integer cp;

    public java.lang.Integer getCp() {
        return this.cp;
    }

    public final java.lang.Integer getCp_DIRECT() {
        return this.cp;
    }

    public void setCp(final java.lang.Integer cp) {
        this.cp = cp;
    }

    public final void setCp_DIRECT(final java.lang.Integer cp) {
        this.cp = cp;
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
    private java.lang.String email;

    public java.lang.String getEmail() {
        return this.email;
    }

    public final java.lang.String getEmail_DIRECT() {
        return this.email;
    }

    public void setEmail(final java.lang.String email) {
        this.email = email;
    }

    public final void setEmail_DIRECT(final java.lang.String email) {
        this.email = email;
    }

    @Column
    private java.lang.String email2;

    public java.lang.String getEmail2() {
        return this.email2;
    }

    public final java.lang.String getEmail2_DIRECT() {
        return this.email2;
    }

    public void setEmail2(final java.lang.String email2) {
        this.email2 = email2;
    }

    public final void setEmail2_DIRECT(final java.lang.String email2) {
        this.email2 = email2;
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
    private java.lang.String email_domain2;

    public java.lang.String getEmail_domain2() {
        return this.email_domain2;
    }

    public final java.lang.String getEmail_domain2_DIRECT() {
        return this.email_domain2;
    }

    public void setEmail_domain2(final java.lang.String email_domain2) {
        this.email_domain2 = email_domain2;
    }

    public final void setEmail_domain2_DIRECT(final java.lang.String email_domain2) {
        this.email_domain2 = email_domain2;
    }

    @Column
    private java.lang.String email_name;

    public java.lang.String getEmail_name() {
        return this.email_name;
    }

    public final java.lang.String getEmail_name_DIRECT() {
        return this.email_name;
    }

    public void setEmail_name(final java.lang.String email_name) {
        this.email_name = email_name;
    }

    public final void setEmail_name_DIRECT(final java.lang.String email_name) {
        this.email_name = email_name;
    }

    @Column
    private java.lang.String email_name2;

    public java.lang.String getEmail_name2() {
        return this.email_name2;
    }

    public final java.lang.String getEmail_name2_DIRECT() {
        return this.email_name2;
    }

    public void setEmail_name2(final java.lang.String email_name2) {
        this.email_name2 = email_name2;
    }

    public final void setEmail_name2_DIRECT(final java.lang.String email_name2) {
        this.email_name2 = email_name2;
    }

    @Column
    private char enum_import_proc;

    public char getEnum_import_proc() {
        return this.enum_import_proc;
    }

    public final char getEnum_import_proc_DIRECT() {
        return this.enum_import_proc;
    }

    public void setEnum_import_proc(final char enum_import_proc) {
        this.enum_import_proc = enum_import_proc;
    }

    public final void setEnum_import_proc_DIRECT(final char enum_import_proc) {
        this.enum_import_proc = enum_import_proc;
    }

    @Column
    private char enum_info_validation;

    public char getEnum_info_validation() {
        return this.enum_info_validation;
    }

    public final char getEnum_info_validation_DIRECT() {
        return this.enum_info_validation;
    }

    public void setEnum_info_validation(final char enum_info_validation) {
        this.enum_info_validation = enum_info_validation;
    }

    public final void setEnum_info_validation_DIRECT(final char enum_info_validation) {
        this.enum_info_validation = enum_info_validation;
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
    private java.lang.String fax2;

    public java.lang.String getFax2() {
        return this.fax2;
    }

    public final java.lang.String getFax2_DIRECT() {
        return this.fax2;
    }

    public void setFax2(final java.lang.String fax2) {
        this.fax2 = fax2;
    }

    public final void setFax2_DIRECT(final java.lang.String fax2) {
        this.fax2 = fax2;
    }

    @Column
    private java.lang.String fileout;

    public java.lang.String getFileout() {
        return this.fileout;
    }

    public final java.lang.String getFileout_DIRECT() {
        return this.fileout;
    }

    public void setFileout(final java.lang.String fileout) {
        this.fileout = fileout;
    }

    public final void setFileout_DIRECT(final java.lang.String fileout) {
        this.fileout = fileout;
    }

    @Column
    private java.lang.Short flg_udelivery_addr;

    public java.lang.Short getFlg_udelivery_addr() {
        return this.flg_udelivery_addr;
    }

    public final java.lang.Short getFlg_udelivery_addr_DIRECT() {
        return this.flg_udelivery_addr;
    }

    public void setFlg_udelivery_addr(final java.lang.Short flg_udelivery_addr) {
        this.flg_udelivery_addr = flg_udelivery_addr;
    }

    public final void setFlg_udelivery_addr_DIRECT(final java.lang.Short flg_udelivery_addr) {
        this.flg_udelivery_addr = flg_udelivery_addr;
    }

    @Column
    private java.lang.Short flg_udelivery_email;

    public java.lang.Short getFlg_udelivery_email() {
        return this.flg_udelivery_email;
    }

    public final java.lang.Short getFlg_udelivery_email_DIRECT() {
        return this.flg_udelivery_email;
    }

    public void setFlg_udelivery_email(final java.lang.Short flg_udelivery_email) {
        this.flg_udelivery_email = flg_udelivery_email;
    }

    public final void setFlg_udelivery_email_DIRECT(final java.lang.Short flg_udelivery_email) {
        this.flg_udelivery_email = flg_udelivery_email;
    }

    @Column
    private java.lang.Short flg_udelivery_fax;

    public java.lang.Short getFlg_udelivery_fax() {
        return this.flg_udelivery_fax;
    }

    public final java.lang.Short getFlg_udelivery_fax_DIRECT() {
        return this.flg_udelivery_fax;
    }

    public void setFlg_udelivery_fax(final java.lang.Short flg_udelivery_fax) {
        this.flg_udelivery_fax = flg_udelivery_fax;
    }

    public final void setFlg_udelivery_fax_DIRECT(final java.lang.Short flg_udelivery_fax) {
        this.flg_udelivery_fax = flg_udelivery_fax;
    }

    @Column
    private java.lang.Short flg_udelivery_tel;

    public java.lang.Short getFlg_udelivery_tel() {
        return this.flg_udelivery_tel;
    }

    public final java.lang.Short getFlg_udelivery_tel_DIRECT() {
        return this.flg_udelivery_tel;
    }

    public void setFlg_udelivery_tel(final java.lang.Short flg_udelivery_tel) {
        this.flg_udelivery_tel = flg_udelivery_tel;
    }

    public final void setFlg_udelivery_tel_DIRECT(final java.lang.Short flg_udelivery_tel) {
        this.flg_udelivery_tel = flg_udelivery_tel;
    }

    @Column
    private java.lang.Integer id_card;

    public java.lang.Integer getId_card() {
        return this.id_card;
    }

    public final java.lang.Integer getId_card_DIRECT() {
        return this.id_card;
    }

    public void setId_card(final java.lang.Integer id_card) {
        this.id_card = id_card;
    }

    public final void setId_card_DIRECT(final java.lang.Integer id_card) {
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
    private java.lang.Integer id_person;

    public java.lang.Integer getId_person() {
        return this.id_person;
    }

    public final java.lang.Integer getId_person_DIRECT() {
        return this.id_person;
    }

    public void setId_person(final java.lang.Integer id_person) {
        this.id_person = id_person;
    }

    public final void setId_person_DIRECT(final java.lang.Integer id_person) {
        this.id_person = id_person;
    }

    @Column
    private java.lang.String id_san_company;

    public java.lang.String getId_san_company() {
        return this.id_san_company;
    }

    public final java.lang.String getId_san_company_DIRECT() {
        return this.id_san_company;
    }

    public void setId_san_company(final java.lang.String id_san_company) {
        this.id_san_company = id_san_company;
    }

    public final void setId_san_company_DIRECT(final java.lang.String id_san_company) {
        this.id_san_company = id_san_company;
    }

    @Column
    private java.lang.String id_san_person;

    public java.lang.String getId_san_person() {
        return this.id_san_person;
    }

    public final java.lang.String getId_san_person_DIRECT() {
        return this.id_san_person;
    }

    public void setId_san_person(final java.lang.String id_san_person) {
        this.id_san_person = id_san_person;
    }

    public final void setId_san_person_DIRECT(final java.lang.String id_san_person) {
        this.id_san_person = id_san_person;
    }

    @Column
    private java.lang.String memo;

    public java.lang.String getMemo() {
        return this.memo;
    }

    public final java.lang.String getMemo_DIRECT() {
        return this.memo;
    }

    public void setMemo(final java.lang.String memo) {
        this.memo = memo;
    }

    public final void setMemo_DIRECT(final java.lang.String memo) {
        this.memo = memo;
    }

    @Column
    private java.lang.String mphone;

    public java.lang.String getMphone() {
        return this.mphone;
    }

    public final java.lang.String getMphone_DIRECT() {
        return this.mphone;
    }

    public void setMphone(final java.lang.String mphone) {
        this.mphone = mphone;
    }

    public final void setMphone_DIRECT(final java.lang.String mphone) {
        this.mphone = mphone;
    }

    @Column
    private java.lang.String mphone2;

    public java.lang.String getMphone2() {
        return this.mphone2;
    }

    public final java.lang.String getMphone2_DIRECT() {
        return this.mphone2;
    }

    public void setMphone2(final java.lang.String mphone2) {
        this.mphone2 = mphone2;
    }

    public final void setMphone2_DIRECT(final java.lang.String mphone2) {
        this.mphone2 = mphone2;
    }

    @Column
    private java.lang.String name_branch;

    public java.lang.String getName_branch() {
        return this.name_branch;
    }

    public final java.lang.String getName_branch_DIRECT() {
        return this.name_branch;
    }

    public void setName_branch(final java.lang.String name_branch) {
        this.name_branch = name_branch;
    }

    public final void setName_branch_DIRECT(final java.lang.String name_branch) {
        this.name_branch = name_branch;
    }

    @Column
    private java.lang.String name_client_person;

    public java.lang.String getName_client_person() {
        return this.name_client_person;
    }

    public final java.lang.String getName_client_person_DIRECT() {
        return this.name_client_person;
    }

    public void setName_client_person(final java.lang.String name_client_person) {
        this.name_client_person = name_client_person;
    }

    public final void setName_client_person_DIRECT(final java.lang.String name_client_person) {
        this.name_client_person = name_client_person;
    }

    @Column
    private java.lang.String name_cl_first;

    public java.lang.String getName_cl_first() {
        return this.name_cl_first;
    }

    public final java.lang.String getName_cl_first_DIRECT() {
        return this.name_cl_first;
    }

    public void setName_cl_first(final java.lang.String name_cl_first) {
        this.name_cl_first = name_cl_first;
    }

    public final void setName_cl_first_DIRECT(final java.lang.String name_cl_first) {
        this.name_cl_first = name_cl_first;
    }

    @Column
    private java.lang.String name_cl_first_kana;

    public java.lang.String getName_cl_first_kana() {
        return this.name_cl_first_kana;
    }

    public final java.lang.String getName_cl_first_kana_DIRECT() {
        return this.name_cl_first_kana;
    }

    public void setName_cl_first_kana(final java.lang.String name_cl_first_kana) {
        this.name_cl_first_kana = name_cl_first_kana;
    }

    public final void setName_cl_first_kana_DIRECT(final java.lang.String name_cl_first_kana) {
        this.name_cl_first_kana = name_cl_first_kana;
    }

    @Column
    private java.lang.String name_cl_kana;

    public java.lang.String getName_cl_kana() {
        return this.name_cl_kana;
    }

    public final java.lang.String getName_cl_kana_DIRECT() {
        return this.name_cl_kana;
    }

    public void setName_cl_kana(final java.lang.String name_cl_kana) {
        this.name_cl_kana = name_cl_kana;
    }

    public final void setName_cl_kana_DIRECT(final java.lang.String name_cl_kana) {
        this.name_cl_kana = name_cl_kana;
    }

    @Column
    private java.lang.String name_cl_last;

    public java.lang.String getName_cl_last() {
        return this.name_cl_last;
    }

    public final java.lang.String getName_cl_last_DIRECT() {
        return this.name_cl_last;
    }

    public void setName_cl_last(final java.lang.String name_cl_last) {
        this.name_cl_last = name_cl_last;
    }

    public final void setName_cl_last_DIRECT(final java.lang.String name_cl_last) {
        this.name_cl_last = name_cl_last;
    }

    @Column
    private java.lang.String name_cl_last_kana;

    public java.lang.String getName_cl_last_kana() {
        return this.name_cl_last_kana;
    }

    public final java.lang.String getName_cl_last_kana_DIRECT() {
        return this.name_cl_last_kana;
    }

    public void setName_cl_last_kana(final java.lang.String name_cl_last_kana) {
        this.name_cl_last_kana = name_cl_last_kana;
    }

    public final void setName_cl_last_kana_DIRECT(final java.lang.String name_cl_last_kana) {
        this.name_cl_last_kana = name_cl_last_kana;
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

    @Column
    private java.lang.String pcategory;

    public java.lang.String getPcategory() {
        return this.pcategory;
    }

    public final java.lang.String getPcategory_DIRECT() {
        return this.pcategory;
    }

    public void setPcategory(final java.lang.String pcategory) {
        this.pcategory = pcategory;
    }

    public final void setPcategory_DIRECT(final java.lang.String pcategory) {
        this.pcategory = pcategory;
    }

    @Column
    private java.lang.String position;

    public java.lang.String getPosition() {
        return this.position;
    }

    public final java.lang.String getPosition_DIRECT() {
        return this.position;
    }

    public void setPosition(final java.lang.String position) {
        this.position = position;
    }

    public final void setPosition_DIRECT(final java.lang.String position) {
        this.position = position;
    }

    @Column
    private java.lang.String tags;

    public java.lang.String getTags() {
        return this.tags;
    }

    public final java.lang.String getTags_DIRECT() {
        return this.tags;
    }

    public void setTags(final java.lang.String tags) {
        this.tags = tags;
    }

    public final void setTags_DIRECT(final java.lang.String tags) {
        this.tags = tags;
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

    @Column
    private java.lang.String tel12;

    public java.lang.String getTel12() {
        return this.tel12;
    }

    public final java.lang.String getTel12_DIRECT() {
        return this.tel12;
    }

    public void setTel12(final java.lang.String tel12) {
        this.tel12 = tel12;
    }

    public final void setTel12_DIRECT(final java.lang.String tel12) {
        this.tel12 = tel12;
    }

    @Column
    private java.lang.String tel21;

    public java.lang.String getTel21() {
        return this.tel21;
    }

    public final java.lang.String getTel21_DIRECT() {
        return this.tel21;
    }

    public void setTel21(final java.lang.String tel21) {
        this.tel21 = tel21;
    }

    public final void setTel21_DIRECT(final java.lang.String tel21) {
        this.tel21 = tel21;
    }

    @Column
    private java.lang.String tel22;

    public java.lang.String getTel22() {
        return this.tel22;
    }

    public final java.lang.String getTel22_DIRECT() {
        return this.tel22;
    }

    public void setTel22(final java.lang.String tel22) {
        this.tel22 = tel22;
    }

    public final void setTel22_DIRECT(final java.lang.String tel22) {
        this.tel22 = tel22;
    }

    @Column
    private java.util.Date ts_com_creation;

    public java.util.Date getTs_com_creation() {
        return this.ts_com_creation;
    }

    public final java.util.Date getTs_com_creation_DIRECT() {
        return this.ts_com_creation;
    }

    public void setTs_com_creation(final java.util.Date ts_com_creation) {
        this.ts_com_creation = ts_com_creation;
    }

    public final void setTs_com_creation_DIRECT(final java.util.Date ts_com_creation) {
        this.ts_com_creation = ts_com_creation;
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
    private java.lang.String url;

    public java.lang.String getUrl() {
        return this.url;
    }

    public final java.lang.String getUrl_DIRECT() {
        return this.url;
    }

    public void setUrl(final java.lang.String url) {
        this.url = url;
    }

    public final void setUrl_DIRECT(final java.lang.String url) {
        this.url = url;
    }

    @Column
    private java.lang.String url2;

    public java.lang.String getUrl2() {
        return this.url2;
    }

    public final java.lang.String getUrl2_DIRECT() {
        return this.url2;
    }

    public void setUrl2(final java.lang.String url2) {
        this.url2 = url2;
    }

    public final void setUrl2_DIRECT(final java.lang.String url2) {
        this.url2 = url2;
    }

    @Column
    private java.lang.String url_domain;

    public java.lang.String getUrl_domain() {
        return this.url_domain;
    }

    public final java.lang.String getUrl_domain_DIRECT() {
        return this.url_domain;
    }

    public void setUrl_domain(final java.lang.String url_domain) {
        this.url_domain = url_domain;
    }

    public final void setUrl_domain_DIRECT(final java.lang.String url_domain) {
        this.url_domain = url_domain;
    }

    @Column
    private java.lang.String url_domain2;

    public java.lang.String getUrl_domain2() {
        return this.url_domain2;
    }

    public final java.lang.String getUrl_domain2_DIRECT() {
        return this.url_domain2;
    }

    public void setUrl_domain2(final java.lang.String url_domain2) {
        this.url_domain2 = url_domain2;
    }

    public final void setUrl_domain2_DIRECT(final java.lang.String url_domain2) {
        this.url_domain2 = url_domain2;
    }

    @Column
    private java.lang.Integer vp;

    public java.lang.Integer getVp() {
        return this.vp;
    }

    public final java.lang.Integer getVp_DIRECT() {
        return this.vp;
    }

    public void setVp(final java.lang.Integer vp) {
        this.vp = vp;
    }

    public final void setVp_DIRECT(final java.lang.Integer vp) {
        this.vp = vp;
    }

    @Column
    private java.lang.String zipcode2;

    public java.lang.String getZipcode2() {
        return this.zipcode2;
    }

    public final java.lang.String getZipcode2_DIRECT() {
        return this.zipcode2;
    }

    public void setZipcode2(final java.lang.String zipcode2) {
        this.zipcode2 = zipcode2;
    }

    public final void setZipcode2_DIRECT(final java.lang.String zipcode2) {
        this.zipcode2 = zipcode2;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_SAN_COMPANY", referencedColumnName = "ID_SAN_COMPANY", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.San_com_info san_com_info;

    public arrow.businesstraceability.persistence.entity.San_com_info getSan_com_info() {
        return this.san_com_info;
    }

    /**
     * Set San_com_info for San_card_data_MAPPED.
     *
     * @param san_com_info San_com_info.
     *
     **/
    public void setSan_com_info(final arrow.businesstraceability.persistence.entity.San_com_info san_com_info) {
        if (san_com_info == null) {
            this.id_san_company = null;
        }
        else {
            this.id_san_company = san_com_info.getId_san_company();
        }
        this.san_com_info = san_com_info;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}