package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface CustomerInfoViewMixin {
    @JsonProperty
    String getId();

    // @JsonProperty
    // String getBase_office();
    //
    // @JsonProperty
    // String getBranch_office();

    @JsonProperty
    String getCom_company_code();

    // @JsonProperty
    // String getCom_company_furigana();
    //
    // @JsonProperty
    // String getCom_company_name();

    @JsonProperty
    String getCom_customer_code();

    // @JsonProperty
    // String getCom_indbig_code();
    //
    // @JsonProperty
    // String getCom_listed_code();

    // @JsonProperty
    // String getCom_url();

    @JsonProperty
    String getDai_compemp_name();

    @JsonProperty
    String getDai_employee_post();

    // @JsonProperty
    // String getField_name();

    @JsonProperty
    String getBranch_point_code();

}
