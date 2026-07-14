package com.zvrms.controller;

import com.zvrms.dto.dashboard.ChartResponse;
import com.zvrms.dto.dashboard.DirectorDashboardResponse;
import com.zvrms.dto.dashboard.DistrictOfficerDashboardResponse;
import com.zvrms.dto.dashboard.SystemOfficerDashboardResponse;
import com.zvrms.service.DashboardService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/director")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<DirectorDashboardResponse> director() {

        return ResponseEntity.ok(
                dashboardService.directorDashboard());
    }

    @GetMapping("/system-officer")
    @PreAuthorize("hasRole('SYSTEM_OFFICER')")
    public ResponseEntity<SystemOfficerDashboardResponse> systemOfficer() {

        return ResponseEntity.ok(
                dashboardService.systemOfficerDashboard());
    }

    @GetMapping("/district-officer")
    @PreAuthorize("hasRole('DISTRICT_OFFICER')")
    public ResponseEntity<DistrictOfficerDashboardResponse> districtOfficer(
            Authentication authentication) {

        return ResponseEntity.ok(
                dashboardService.districtDashboard(authentication));
    }

    @GetMapping("/gender-chart")
@PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
public ResponseEntity<List<ChartResponse>> genderChart(){

    return ResponseEntity.ok(

            dashboardService.genderChart()

    );

}

@GetMapping("/today")
@PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
public ResponseEntity<Long> today(){

    return ResponseEntity.ok(

            dashboardService.todayRegistrations()

    );

}

@GetMapping("/district-chart")
@PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
public ResponseEntity<List<ChartResponse>> districtChart() {

    return ResponseEntity.ok(

            dashboardService.districtChart()

    );

}

@GetMapping("/monthly-chart")
@PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
public ResponseEntity<List<ChartResponse>> monthlyChart() {

    return ResponseEntity.ok(

            dashboardService.monthlyChart()

    );

}
}