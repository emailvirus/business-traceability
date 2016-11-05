package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanComLiveOwnerMixin {

    @JsonProperty
    int getId_company();

    @JsonProperty
    java.lang.String getAc_user();

    @JsonProperty
    int getCn_card_perowner();

    @JsonProperty
    int getCn_cp();

    @JsonProperty
    int getCn_vp();

    @JsonProperty
    java.util.Date getDate_latest_exchange();

    @JsonProperty
    int getId_card();

    @JsonProperty
    int getMaxn_cp();

    @JsonProperty
    int getMaxn_vp();

    @JsonProperty
    java.lang.String getName_user();
}
