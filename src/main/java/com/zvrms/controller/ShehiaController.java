package com.zvrms.controller;

import com.zvrms.dto.shehia.ShehiaResponse;
import com.zvrms.service.ShehiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shehia")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ShehiaController {

    private final ShehiaService shehiaService;

    @GetMapping("/my")
    @PreAuthorize("hasRole('DISTRICT_OFFICER')")
    public ResponseEntity<List<ShehiaResponse>> myShehia(
            Authentication authentication){

        return ResponseEntity.ok(

                shehiaService.getMyDistrictShehia(authentication)

        );

    }

    @GetMapping("/district/{districtId}")
    @PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
    public ResponseEntity<List<ShehiaResponse>> districtShehia(
            @PathVariable Long districtId){

        return ResponseEntity.ok(

                shehiaService.getByDistrict(districtId)

        );

    }

}