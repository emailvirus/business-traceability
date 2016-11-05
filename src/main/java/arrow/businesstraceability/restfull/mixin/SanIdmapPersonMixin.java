package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanIdmapPersonMixin {
    @JsonProperty
    int getId_person();

    @JsonProperty
    java.lang.String getId_san_person();
}