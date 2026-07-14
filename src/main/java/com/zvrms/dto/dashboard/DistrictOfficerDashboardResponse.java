package com.zvrms.dto.dashboard;

import lombok.Data;

@Data
public class DistrictOfficerDashboardResponse {

    private String district;

    private long totalShehia;

    private long totalVoters;

    private long totalMale;

    private long totalFemale;

}