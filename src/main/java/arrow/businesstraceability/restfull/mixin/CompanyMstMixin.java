package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface CompanyMstMixin {
    @JsonProperty
    String getCom_company_name();

    @JsonProperty
    String getCom_company_code();

    @JsonProperty
    String getCom_capital_level();

    @JsonProperty
    String getCom_company_furigana();

    @JsonProperty
    String getCom_customer_code();

    @JsonProperty
    String com_delete_reason();

    @JsonProperty
    String getCom_indbig_code();

    @JsonProperty
    String getCom_indmdl_code();

    @JsonProperty
    String getCom_indsml_code();

    @JsonProperty
    String getCom_limited_code();

    @JsonProperty
    String getCom_listed_code();


}
