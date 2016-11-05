package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface EmployeeMstMixin {

    @JsonProperty
    int getEmp_code();

    @JsonProperty
    java.lang.String getEmp_adpcode();

    @JsonProperty
    java.lang.Character getEmp_condi_code();

    @JsonProperty
    boolean getEmp_delete_flg();

    @JsonProperty
    java.util.Date getEmp_entery_date();

    @JsonProperty
    java.lang.String getEmp_mail();

    @JsonProperty
    java.lang.String getEmp_mobile();

    @JsonProperty
    java.lang.String getEmp_name();

    @JsonProperty
    short getEmp_poscode();

    @JsonProperty
    java.lang.String getEmp_post();

    @JsonProperty
    short getEmp_settle_authority();

    @JsonProperty
    boolean getEmp_system_authority();

    @JsonProperty
    java.lang.String getEmp_tel();

    @JsonProperty
    java.util.Date getEmp_update_date();

}
