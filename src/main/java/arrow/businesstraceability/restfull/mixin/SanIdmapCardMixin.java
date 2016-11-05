package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanIdmapCardMixin {

    @JsonProperty
    int getId_card();

    @JsonProperty
    java.lang.String getId_san_card();
}