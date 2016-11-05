package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanIdmapCompanyMixin {
    @JsonProperty
    int getId_company();

    @JsonProperty
    java.lang.String getId_san_company();
}