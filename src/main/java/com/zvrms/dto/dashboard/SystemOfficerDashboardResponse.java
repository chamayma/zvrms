package com.zvrms.dto.dashboard;

import lombok.Data;

@Data
public class SystemOfficerDashboardResponse {

    private long totalDistrictOfficers;

    private long totalVoters;

    private long totalMale;

    private long totalFemale;

    private long totalDistricts;

    private long totalShehia;

}