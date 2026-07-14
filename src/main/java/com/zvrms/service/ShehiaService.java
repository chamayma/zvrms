package com.zvrms.service;

import com.zvrms.dto.shehia.ShehiaResponse;
import com.zvrms.entity.District;
import com.zvrms.entity.Shehia;
import com.zvrms.entity.User;
import com.zvrms.repository.DistrictRepository;
import com.zvrms.repository.ShehiaRepository;
import com.zvrms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShehiaService {

    private final ShehiaRepository shehiaRepository;
    private final DistrictRepository districtRepository;
    private final UserRepository userRepository;

    public List<ShehiaResponse> getMyDistrictShehia(Authentication authentication){

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return shehiaRepository.findByDistrict(user.getDistrict())

                .stream()

                .map(this::map)

                .toList();

    }

    public List<ShehiaResponse> getByDistrict(Long districtId){

        District district = districtRepository.findById(districtId)
                .orElseThrow(() -> new RuntimeException("District not found"));

        return shehiaRepository.findByDistrict(district)

                .stream()

                .map(this::map)

                .toList();

    }

    private ShehiaResponse map(Shehia shehia){

        ShehiaResponse response = new ShehiaResponse();

        response.setId(shehia.getId());

        response.setName(shehia.getName());

        return response;

    }

}