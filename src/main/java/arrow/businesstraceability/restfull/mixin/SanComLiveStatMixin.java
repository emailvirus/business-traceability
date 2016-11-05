package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanComLiveStatMixin {

    @JsonProperty
    int getId_company();

    @JsonProperty
    int getCn_attr_correction();

    @JsonProperty
    int getCn_cp();

    @JsonProperty
    int getCn_vp();

    @JsonProperty
    java.util.Date getDate_latest_exchange();

    @JsonProperty
    java.util.Date getDate_oldest_exchange();

    @JsonProperty
    int getId_latest_card();

    @JsonProperty
    int getId_max_cp_card();

    @JsonProperty
    int getId_max_vp_card();

    @JsonProperty
    int getMaxn_cp();

    @JsonProperty
    int getMaxn_vp();

    @JsonProperty
    int getSn_card();

    @JsonProperty
    int getSn_client();

    @JsonProperty
    int getSn_owner();

}
