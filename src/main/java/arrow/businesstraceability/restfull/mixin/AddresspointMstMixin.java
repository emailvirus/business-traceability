package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface AddresspointMstMixin {

    @JsonProperty
    java.lang.String getAdp_code();

    @JsonProperty
    java.lang.String getAdp_name();

}
