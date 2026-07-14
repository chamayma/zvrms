package com.zvrms.service;

import com.zvrms.dto.dashboard.ChartResponse;
import com.zvrms.dto.dashboard.DirectorDashboardResponse;
import com.zvrms.dto.dashboard.DistrictOfficerDashboardResponse;
import com.zvrms.dto.dashboard.SystemOfficerDashboardResponse;
import com.zvrms.dto.voter.VoterResponse;
import com.zvrms.entity.User;
import com.zvrms.enums.Role;
import com.zvrms.repository.DistrictRepository;
import com.zvrms.repository.ShehiaRepository;
import com.zvrms.repository.UserRepository;
import com.zvrms.repository.VoterRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final DistrictRepository districtRepository;
    private final ShehiaRepository shehiaRepository;
    private final VoterRepository voterRepository;

    public DirectorDashboardResponse directorDashboard() {

        DirectorDashboardResponse response = new DirectorDashboardResponse();

        response.setTotalSystemOfficers(
                userRepository.findByRole(Role.SYSTEM_OFFICER).size());

        response.setTotalDistrictOfficers(
                userRepository.findByRole(Role.DISTRICT_OFFICER).size());

        response.setTotalDistricts(
                districtRepository.count());

        response.setTotalShehia(
                shehiaRepository.count());

        response.setTotalVoters(
                voterRepository.count());

        response.setTotalMale(
                voterRepository.countBySexIgnoreCase("Male"));

        response.setTotalFemale(
                voterRepository.countBySexIgnoreCase("Female"));

        return response;

    }

    public SystemOfficerDashboardResponse systemOfficerDashboard() {

        SystemOfficerDashboardResponse response =
                new SystemOfficerDashboardResponse();

        response.setTotalDistrictOfficers(
                userRepository.findByRole(Role.DISTRICT_OFFICER).size());

        response.setTotalVoters(
                voterRepository.count());

        response.setTotalMale(
                voterRepository.countBySexIgnoreCase("Male"));

        response.setTotalFemale(
                voterRepository.countBySexIgnoreCase("Female"));

        return response;

    }

    public DistrictOfficerDashboardResponse districtDashboard(
            Authentication authentication) {

        User officer = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DistrictOfficerDashboardResponse response =
                new DistrictOfficerDashboardResponse();

        response.setDistrict(
                officer.getDistrict().getName());

        response.setTotalShehia(
                shehiaRepository.findByDistrict(officer.getDistrict()).size());

        response.setTotalVoters(
                voterRepository.findByDistrict(officer.getDistrict()).size());

        response.setTotalMale(
                voterRepository.findByDistrict(officer.getDistrict())
                        .stream()
                        .filter(v -> v.getSex().equalsIgnoreCase("Male"))
                        .count());

        response.setTotalFemale(
                voterRepository.findByDistrict(officer.getDistrict())
                        .stream()
                        .filter(v -> v.getSex().equalsIgnoreCase("Female"))
                        .count());

        return response;

    }

    public List<ChartResponse> genderChart(){

    return List.of(

            new ChartResponse(
                    "Male",
                    voterRepository.countBySexIgnoreCase("Male")
            ),

            new ChartResponse(
                    "Female",
                    voterRepository.countBySexIgnoreCase("Female")
            )

    );

}

public Long todayRegistrations(){

    LocalDate today = LocalDate.now();

    return voterRepository.countByCreatedAtBetween(

            today.atStartOfDay(),

            today.plusDays(1).atStartOfDay()

    );

}

public List<VoterResponse> recentRegistrations(){

    return voterRepository.findTop10ByOrderByCreatedAtDesc()

        .stream()

        .map(voter -> {

            VoterResponse response = new VoterResponse();

            response.setId(voter.getId());
            response.setFullName(voter.getFullName());
            response.setVoterNumber(voter.getVoterNumber());
            response.setDateOfBirth(voter.getDateOfBirth());
            response.setPhoneNumber(voter.getPhoneNumber());
            response.setAddress(voter.getAddress());
            response.setSex(voter.getSex());
            response.setDistrict(voter.getDistrict().getName());
            response.setShehia(voter.getShehia().getName());
            response.setRegisteredBy(voter.getRegisteredBy().getFullName());

            return response;

        })

        .toList();

}

public List<ChartResponse> districtChart() {

    return districtRepository.findAll()

            .stream()

            .map(district ->

                    new ChartResponse(

                            district.getName(),

                            voterRepository.countByDistrict(district)

                    )
            )

            .toList();

}

public List<ChartResponse> monthlyChart() {

    List<ChartResponse> chart = new ArrayList<>();

    int year = LocalDate.now().getYear();

    for (int month = 1; month <= 12; month++) {

        LocalDate start = LocalDate.of(year, month, 1);

        LocalDate end = start.plusMonths(1);

        long total = voterRepository.countByCreatedAtBetween(
                start.atStartOfDay(),
                end.atStartOfDay()
        );

        chart.add(new ChartResponse(
                start.getMonth().name(),
                total
        ));
    }

    return chart;

}



}