package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanCardTagMixin {

    @JsonProperty
    int getId_tagtbl();

    @JsonProperty
    int getId_card();

    @JsonProperty
    java.lang.Integer getId_company();

    @JsonProperty
    java.lang.String getTag();
}
