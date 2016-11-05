package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface CompanyMstSuggestMixin {


    @JsonProperty
    String getListed();

    @JsonProperty
    String getCom_company_name();

    @JsonProperty
    String getCom_company_code();

    @JsonProperty
    String getCom_company_furigana();

    @JsonProperty
    String getCom_customer_code();

    @JsonProperty
    String getWorktype();

    @JsonProperty
    String getCom_url();

    @JsonProperty
    String getFurigana();

    @JsonProperty
    String getName();

    @JsonProperty
    String getBig_name();

    @JsonProperty
    String getAdp_name();

    @JsonProperty
    String getCom_point_code();

    @JsonProperty
    String getCom_indbig_code();

    @JsonProperty
    String getCom_listed_code();

    @JsonProperty
    String getCom_limited_code();

    @JsonProperty
    String getDomain();

    @JsonProperty
    String getDomainExt();


}
