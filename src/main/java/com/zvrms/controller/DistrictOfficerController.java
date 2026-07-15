package com.zvrms.controller;

import com.zvrms.dto.user.CreateDistrictOfficerRequest;
import com.zvrms.dto.user.DistrictOfficerResponse;
import com.zvrms.dto.user.UpdateDistrictOfficerRequest;
import com.zvrms.service.DistrictOfficerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/district-officers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DistrictOfficerController {

    private final DistrictOfficerService districtOfficerService;

    @PostMapping
    @PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
    public ResponseEntity<DistrictOfficerResponse> create(
            @Valid @RequestBody CreateDistrictOfficerRequest request) {

        return ResponseEntity.ok(
                districtOfficerService.create(request)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
    public ResponseEntity<List<DistrictOfficerResponse>> getAll() {

        return ResponseEntity.ok(
                districtOfficerService.getAll()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_OFFICER')")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        districtOfficerService.delete(id);

        return ResponseEntity.ok("District Officer Deleted Successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
    public ResponseEntity<DistrictOfficerResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateDistrictOfficerRequest request) {

    return ResponseEntity.ok(
            districtOfficerService.update(id, request)
    );
    }


}