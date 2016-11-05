package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanComSiteMixin {

    @JsonProperty
    int getId_site();

    @JsonProperty
    java.lang.String getAddr_all();

    @JsonProperty
    java.lang.String getAddr_bldg();

    @JsonProperty
    java.lang.String getAddr_block();

    @JsonProperty
    java.lang.String getAddr_city();

    @JsonProperty
    java.lang.String getAddr_pref();

    @JsonProperty
    java.lang.String getCode_zip();

    @JsonProperty
    java.util.Date getDate_latest_exchange();

    @JsonProperty
    java.lang.Integer getId_branch();

    @JsonProperty
    int getId_card();

    @JsonProperty
    int getId_company();

    @JsonProperty
    int getN_dupinfo();

}
