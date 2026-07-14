package com.zvrms.service;

import com.zvrms.entity.District;
import com.zvrms.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;

    public District create(District district) {

        if (districtRepository.findByName(district.getName()).isPresent()) {
            throw new RuntimeException("District already exists");
        }

        return districtRepository.save(district);
    }

    public List<District> getAll() {
        return districtRepository.findAll();
    }

    public District getById(Long id) {

        return districtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("District not found"));
    }

    public District update(Long id, District request) {

        District district = getById(id);

        district.setName(request.getName());

        return districtRepository.save(district);
    }

    public void delete(Long id) {

        District district = getById(id);

        districtRepository.delete(district);
    }
}