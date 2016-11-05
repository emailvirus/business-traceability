package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanComMDomainMixin {

    @JsonProperty
    int getId_maildomain();

    @JsonProperty
    java.util.Date getDate_latest_exchange();

    @JsonProperty
    java.lang.String getEmail_domain();

    @JsonProperty
    int getId_company();

    @JsonProperty
    int getN_dupinfo();
}
