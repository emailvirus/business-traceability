//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.San_com_info;

@StaticMetamodel(San_card_data_MAPPED.class)
public class San_card_data_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<San_card_data_MAPPED, San_card_data_MAPPED.Pk> pk;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> id_san_card;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> ac_user;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> addr_all;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> addr_all2;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> addr_bldg;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> addr_bldg2;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> addr_block;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> addr_block2;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> addr_city;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> addr_city2;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> addr_pref;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> addr_pref2;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> automemo;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Integer> cn_apiimport;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Integer> cn_cardinfo_update;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Integer> cn_comid_update;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Integer> cn_fileimport;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> code_zip;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> cont_cat;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> cont_date;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> cont_location;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> cont_memo;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> cont_title;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Integer> cp;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.util.Date> date_exchange;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.util.Date> date_register;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> email;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> email2;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> email_domain;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> email_domain2;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> email_name;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> email_name2;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Character> enum_import_proc;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Character> enum_info_validation;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> fax;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> fax2;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> fileout;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Short> flg_udelivery_addr;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Short> flg_udelivery_email;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Short> flg_udelivery_fax;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Short> flg_udelivery_tel;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Integer> id_card;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Integer> id_company;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Integer> id_person;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> id_san_company;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> id_san_person;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> memo;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> mphone;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> mphone2;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> name_branch;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> name_client_person;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> name_cl_first;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> name_cl_first_kana;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> name_cl_kana;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> name_cl_last;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> name_cl_last_kana;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> name_company;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> name_com_eng;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> name_com_kana;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> name_user;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> pcategory;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> position;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> tags;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> tel11;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> tel12;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> tel21;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> tel22;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.util.Date> ts_com_creation;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.util.Date> ts_create;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> url;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> url2;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> url_domain;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> url_domain2;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.Integer> vp;
    public static volatile SingularAttribute<San_card_data_MAPPED, java.lang.String> zipcode2;
    public static volatile SingularAttribute<San_card_data_MAPPED, San_com_info> san_com_info;
}