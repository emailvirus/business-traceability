package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanCardDataMixin {

    @JsonProperty
    java.lang.String getId_san_card();

    @JsonProperty
    java.lang.String getAc_user();

    @JsonProperty
    java.lang.String getAddr_all();

    @JsonProperty
    java.lang.String getAddr_all2();

    @JsonProperty
    java.lang.String getAddr_bldg();

    @JsonProperty
    java.lang.String getAddr_bldg2();

    @JsonProperty
    java.lang.String getAddr_block();

    @JsonProperty
    java.lang.String getAddr_block2();

    @JsonProperty
    java.lang.String getAddr_city();

    @JsonProperty
    java.lang.String getAddr_city2();

    @JsonProperty
    java.lang.String getAddr_pref();

    @JsonProperty
    java.lang.String getAddr_pref2();

    @JsonProperty
    java.lang.String getAutomemo();

    @JsonProperty
    int getCn_apiimport();

    @JsonProperty
    int getCn_cardinfo_update();

    @JsonProperty
    int getCn_comid_update();

    @JsonProperty
    int getCn_fileimport();

    @JsonProperty
    java.lang.String getCode_zip();

    @JsonProperty
    java.lang.String getCont_cat();

    @JsonProperty
    java.lang.String getCont_date();

    @JsonProperty
    java.lang.String getCont_location();

    @JsonProperty
    java.lang.String getCont_memo();

    @JsonProperty
    java.lang.String getCont_title();

    @JsonProperty
    java.lang.Integer getCp();

    @JsonProperty
    java.util.Date getDate_exchange();

    @JsonProperty
    java.util.Date getDate_register();

    @JsonProperty
    java.lang.String getEmail();

    @JsonProperty
    java.lang.String getEmail2();

    @JsonProperty
    java.lang.String getEmail_domain();

    @JsonProperty
    java.lang.String getEmail_domain2();

    @JsonProperty
    java.lang.String getEmail_name();

    @JsonProperty
    java.lang.String getEmail_name2();

    @JsonProperty
    char getEnum_import_proc();

    @JsonProperty
    char getEnum_info_validation();

    @JsonProperty
    java.lang.String getFax();

    @JsonProperty
    java.lang.String getFax2();

    @JsonProperty
    java.lang.String getFileout();

    @JsonProperty
    java.lang.Short getFlg_udelivery_addr();

    @JsonProperty
    java.lang.Short getFlg_udelivery_email();

    @JsonProperty
    java.lang.Short getFlg_udelivery_fax();

    @JsonProperty
    java.lang.Short getFlg_udelivery_tel();

    @JsonProperty
    java.lang.Integer getId_card();

    @JsonProperty
    java.lang.Integer getId_company();

    @JsonProperty
    java.lang.Integer getId_person();

    @JsonProperty
    java.lang.String getId_san_company();

    @JsonProperty
    java.lang.String getId_san_person();

    @JsonProperty
    java.lang.String getMemo();

    @JsonProperty
    java.lang.String getMphone();

    @JsonProperty
    java.lang.String getMphone2();

    @JsonProperty
    java.lang.String getName_branch();

    @JsonProperty
    java.lang.String getName_client_person();

    @JsonProperty
    java.lang.String getName_cl_first();

    @JsonProperty
    java.lang.String getName_cl_first_kana();

    @JsonProperty
    java.lang.String getName_cl_kana();

    @JsonProperty
    java.lang.String getName_cl_last();

    @JsonProperty
    java.lang.String getName_cl_last_kana();

    @JsonProperty
    java.lang.String getName_company();

    @JsonProperty
    java.lang.String getName_com_eng();

    @JsonProperty
    java.lang.String getName_com_kana();

    @JsonProperty
    java.lang.String getName_user();

    @JsonProperty
    java.lang.String getPcategory();

    @JsonProperty
    java.lang.String getPosition();

    @JsonProperty
    java.lang.String getTags();

    @JsonProperty
    java.lang.String getTel11();

    @JsonProperty
    java.lang.String getTel12();

    @JsonProperty
    java.lang.String getTel21();

    @JsonProperty
    java.lang.String getTel22();

    @JsonProperty
    java.util.Date getTs_com_creation();

    @JsonProperty
    java.util.Date getTs_create();

    @JsonProperty
    java.lang.String getUrl();

    @JsonProperty
    java.lang.String getUrl2();

    @JsonProperty
    java.lang.String getUrl_domain();

    @JsonProperty
    java.lang.String getUrl_domain2();

    @JsonProperty
    java.lang.Integer getVp();

    @JsonProperty
    java.lang.String getZipcode2();

    @JsonProperty
    String isUpdated();

    @JsonProperty
    long getTs_last_updated();

}
