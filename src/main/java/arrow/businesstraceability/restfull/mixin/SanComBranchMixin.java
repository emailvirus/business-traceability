package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanComBranchMixin {

    @JsonProperty
    int getId_branch();

    @JsonProperty
    java.util.Date getDate_latest_exchange();

    @JsonProperty
    int getId_card();

    @JsonProperty
    int getId_company();

    @JsonProperty
    java.lang.String getName_branch();

    @JsonProperty
    int getN_dupinfo();

}
