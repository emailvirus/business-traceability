package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanComUDomainMixin {

    @JsonProperty
    int getId_urldomain();

    @JsonProperty
    java.util.Date getDate_latest_exchange();

    @JsonProperty
    int getId_company();

    @JsonProperty
    int getN_dupinfo();

    @JsonProperty
    java.lang.String getUrl_domain();
}
