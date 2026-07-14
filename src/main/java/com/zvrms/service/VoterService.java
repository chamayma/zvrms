package com.zvrms.service;

import com.zvrms.dto.voter.CreateVoterRequest;
import com.zvrms.dto.voter.UpdateVoterRequest;
import com.zvrms.dto.voter.VoterResponse;
import com.zvrms.entity.District;
import com.zvrms.entity.Shehia;
import com.zvrms.entity.User;
import com.zvrms.entity.Voter;
import com.zvrms.repository.DistrictRepository;
import com.zvrms.repository.ShehiaRepository;
import com.zvrms.repository.UserRepository;
import com.zvrms.repository.VoterRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoterService {

    private final VoterRepository voterRepository;
    private final ShehiaRepository shehiaRepository;
    private final UserRepository userRepository;
    private final DistrictRepository districtRepository;

    public VoterResponse register(CreateVoterRequest request,
                              Authentication authentication) {

    if (voterRepository.existsByVoterNumber(request.getVoterNumber())) {
    throw new RuntimeException("Voter number already exists.");
}

if (voterRepository.existsByPhoneNumber(request.getPhoneNumber())) {
    throw new RuntimeException("Phone number already exists.");
}

    User officer = userRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

    Shehia shehia = shehiaRepository.findById(request.getShehiaId())
            .orElseThrow(() -> new RuntimeException("Shehia not found"));

    // SECURITY CHECK
    if (!shehia.getDistrict().getId().equals(officer.getDistrict().getId())) {
        throw new RuntimeException(
                "You are not allowed to register voters in another district."
        );
    }

    Voter voter = new Voter();

    voter.setFullName(request.getFullName());
    voter.setVoterNumber(request.getVoterNumber());
    voter.setDateOfBirth(request.getDateOfBirth());
    voter.setPhoneNumber(request.getPhoneNumber());
    voter.setAddress(request.getAddress());
    voter.setSex(request.getSex());

    // District is taken automatically from the logged-in officer
    voter.setDistrict(officer.getDistrict());

    voter.setShehia(shehia);

    voter.setRegisteredBy(officer);
    voter.setCreatedBy(officer.getUsername());

    voter = voterRepository.save(voter);

    return map(voter);
}

    public List<VoterResponse> getMyVoters(Authentication authentication) {

        User officer = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return voterRepository.findByDistrict(officer.getDistrict())
                .stream()
                .map(this::map)
                .toList();
    }

    public void delete(Long id) {

        voterRepository.deleteById(id);
    }

    private VoterResponse map(Voter voter) {

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
        response.setCreatedAt(voter.getCreatedAt());

        return response;
    }

    public List<VoterResponse> searchByName(String name) {

    return voterRepository.findByFullNameContainingIgnoreCase(name)
            .stream()
            .map(this::map)
            .toList();
}

public VoterResponse searchByVoterNumber(String voterNumber) {

    Voter voter = voterRepository.findByVoterNumber(voterNumber)
            .orElseThrow(() -> new RuntimeException("Voter not found"));

    return map(voter);
}

public List<VoterResponse> searchByPhone(String phone) {

    return voterRepository.findByPhoneNumberContaining(phone)
            .stream()
            .map(this::map)
            .toList();
}

public List<VoterResponse> searchBySex(String sex) {

    return voterRepository.findBySexIgnoreCase(sex)
            .stream()
            .map(this::map)
            .toList();
}

    public VoterResponse update(Long id,
                            UpdateVoterRequest request,
                            Authentication authentication) {

    User officer = userRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

    Voter voter = voterRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Voter not found"));

    // Prevent duplicate phone number
    if (voterRepository.existsByPhoneNumberAndIdNot(
            request.getPhoneNumber(),
            id)) {

        throw new RuntimeException("Phone number already exists.");
    }

    Shehia shehia = shehiaRepository.findById(request.getShehiaId())
            .orElseThrow(() -> new RuntimeException("Shehia not found"));

    // District Officer cannot assign another district's Shehia
    if (!shehia.getDistrict().getId().equals(officer.getDistrict().getId())) {

        throw new RuntimeException(
                "You are not allowed to register voters in another district."
        );

    }

    voter.setFullName(request.getFullName());
    voter.setPhoneNumber(request.getPhoneNumber());
    voter.setAddress(request.getAddress());
    voter.setSex(request.getSex());
    voter.setDateOfBirth(request.getDateOfBirth());
    voter.setShehia(shehia);
    voter.setUpdatedBy(officer.getUsername());

    voter = voterRepository.save(voter);

    return map(voter);

}

    public Page<VoterResponse> getAll(int page, int size) {

    return voterRepository.findAll(PageRequest.of(page, size))
            .map(this::map);

    }

    public List<VoterResponse> searchByDistrict(Long districtId) {

    District district = districtRepository.findById(districtId)
            .orElseThrow(() -> new RuntimeException("District not found"));

    return voterRepository.findByDistrict(district)
            .stream()
            .map(this::map)
            .toList();
    }

    public List<VoterResponse> searchByShehia(Long shehiaId) {

    Shehia shehia = shehiaRepository.findById(shehiaId)
            .orElseThrow(() -> new RuntimeException("Shehia not found"));

    return voterRepository.findByShehia(shehia)
            .stream()
            .map(this::map)
            .toList();
    }

    public List<VoterResponse> searchByOfficer(Long officerId) {

    User officer = userRepository.findById(officerId)
            .orElseThrow(() -> new RuntimeException("Officer not found"));

    return voterRepository.findByRegisteredBy(officer)
            .stream()
            .map(this::map)
            .toList();
    }

    public List<VoterResponse> searchByDate(
        LocalDateTime start,
        LocalDateTime end) {

    return voterRepository.findByCreatedAtBetween(start, end)
            .stream()
            .map(this::map)
            .toList();
}

}