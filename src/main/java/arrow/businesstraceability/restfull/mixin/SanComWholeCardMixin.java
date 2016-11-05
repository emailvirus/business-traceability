package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SanComWholeCardMixin {

    @JsonProperty
    int getId_card();

    @JsonProperty
    java.lang.String getAc_user();

    @JsonProperty
    java.util.Date getDate_card_deletion();

    @JsonProperty
    java.util.Date getDate_exchange();

    @JsonProperty
    java.util.Date getDate_register();

    @JsonProperty
    java.lang.String getFlg_card_deletion();

    @JsonProperty
    int getId_company();
}
