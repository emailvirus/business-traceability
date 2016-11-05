package arrow.businesstraceability.restfull.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface DailyReportMixin {
    @JsonProperty
    int getId();

    // Employee
    @JsonProperty
    int getEmp_name();

    // Branch
    @JsonProperty
    String getAdp_name();

    // Company
    @JsonProperty
    String getDai_company_name();

    // Purpose
    @JsonProperty
    String getDat_name();
}
