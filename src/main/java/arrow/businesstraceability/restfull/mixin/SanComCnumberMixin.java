package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanComCnumberMixin {

    @JsonProperty
    int getId_cnumber();

    @JsonProperty
    java.util.Date getDate_latest_exchange();

    @JsonProperty
    java.lang.String getFax();

    @JsonProperty
    java.lang.Integer getId_branch();

    @JsonProperty
    int getId_card();

    @JsonProperty
    int getId_company();

    @JsonProperty
    java.lang.Integer getId_site();

    @JsonProperty
    int getN_dupinfo();

    @JsonProperty
    java.lang.String getTel11();

}
