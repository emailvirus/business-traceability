package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanComInfoMixin {

    @JsonProperty
    java.lang.String getId_san_company();

    @JsonProperty
    int getCn_update();

    @JsonProperty
    java.lang.Integer getId_company();

    @JsonProperty
    java.lang.String getId_sarscom();

    @JsonProperty
    java.lang.String getMynum_com();

    @JsonProperty
    java.lang.String getName_company();

    @JsonProperty
    java.lang.String getName_com_eng();

    @JsonProperty
    java.lang.String getName_com_kana();

    @JsonProperty
    java.util.Date getTs_create();

    @JsonProperty
    java.util.Date getTs_update();

    @JsonProperty
    java.util.Date getTs_export();

}
