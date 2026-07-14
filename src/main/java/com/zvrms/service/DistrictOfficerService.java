package com.zvrms.service;

import com.zvrms.dto.user.CreateDistrictOfficerRequest;
import com.zvrms.dto.user.DistrictOfficerResponse;
import com.zvrms.dto.user.UpdateDistrictOfficerRequest;
import com.zvrms.entity.District;
import com.zvrms.entity.User;
import com.zvrms.enums.Role;
import com.zvrms.repository.DistrictRepository;
import com.zvrms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictOfficerService {

    private final UserRepository userRepository;
    private final DistrictRepository districtRepository;
    private final PasswordEncoder passwordEncoder;

    public DistrictOfficerResponse create(CreateDistrictOfficerRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        District district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));

        User officer = new User();

        officer.setFullName(request.getFullName());
        officer.setUsername(request.getUsername());
        officer.setPassword(passwordEncoder.encode(request.getPassword()));
        officer.setRole(Role.DISTRICT_OFFICER);
        officer.setDistrict(district);
        officer.setActive(true);

        officer = userRepository.save(officer);

        return map(officer);
    }

    public List<DistrictOfficerResponse> getAll() {

        return userRepository.findByRole(Role.DISTRICT_OFFICER)
                .stream()
                .map(this::map)
                .toList();
    }

    public void delete(Long id) {

        userRepository.deleteById(id);

    }

    private DistrictOfficerResponse map(User user) {

        DistrictOfficerResponse response = new DistrictOfficerResponse();

        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setUsername(user.getUsername());
        response.setDistrict(user.getDistrict().getName());
        response.setActive(user.isActive());

        return response;
    }

    public DistrictOfficerResponse update(Long id,
                                      UpdateDistrictOfficerRequest request) {

    User officer = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("District Officer not found"));

    District district = districtRepository.findById(request.getDistrictId())
            .orElseThrow(() -> new RuntimeException("District not found"));

    officer.setFullName(request.getFullName());
    officer.setUsername(request.getUsername());
    officer.setDistrict(district);
    officer.setActive(request.isActive());

    officer = userRepository.save(officer);

    return map(officer);
    }
}