package com.zvrms.dto.dashboard;

import lombok.Data;

@Data
public class DirectorDashboardResponse {

    private long totalSystemOfficers;

    private long totalDistrictOfficers;

    private long totalDistricts;

    private long totalShehia;

    private long totalVoters;

    private long totalMale;

    private long totalFemale;

}