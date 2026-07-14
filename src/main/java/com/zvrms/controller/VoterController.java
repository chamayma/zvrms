package com.zvrms.controller;

import com.zvrms.dto.voter.CreateVoterRequest;
import com.zvrms.dto.voter.UpdateVoterRequest;
import com.zvrms.dto.voter.VoterResponse;
import com.zvrms.service.VoterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/voters")
@RequiredArgsConstructor
@CrossOrigin("*")
public class VoterController {

    private final VoterService voterService;

    // District Officer registers voters
    @PostMapping
    @PreAuthorize("hasRole('DISTRICT_OFFICER')")
    public ResponseEntity<VoterResponse> register(
            @Valid @RequestBody CreateVoterRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                voterService.register(request, authentication)
        );
    }

    // District Officer views only his/her district voters
    @GetMapping("/my")
    @PreAuthorize("hasRole('DISTRICT_OFFICER')")
    public ResponseEntity<List<VoterResponse>> getMyVoters(
            Authentication authentication) {

        return ResponseEntity.ok(
                voterService.getMyVoters(authentication)
        );
    }

    @GetMapping("/search/name")
    @PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
    public ResponseEntity<List<VoterResponse>> searchByName(
            @RequestParam String name) {

        return ResponseEntity.ok(
                voterService.searchByName(name)
        );
    }

    @GetMapping("/search/voter-number")
    @PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
    public ResponseEntity<VoterResponse> searchByVoterNumber(
            @RequestParam String voterNumber) {

        return ResponseEntity.ok(
                voterService.searchByVoterNumber(voterNumber)
        );
    }

    @GetMapping("/search/phone")
    @PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
    public ResponseEntity<List<VoterResponse>> searchByPhone(
            @RequestParam String phone) {

        return ResponseEntity.ok(
                voterService.searchByPhone(phone)
        );
    }

     @GetMapping
@PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
public ResponseEntity<Page<VoterResponse>> getAll(

        @RequestParam(defaultValue = "0") int page,

        @RequestParam(defaultValue = "10") int size) {

    return ResponseEntity.ok(

            voterService.getAll(page, size)

    );

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        voterService.delete(id);

        return ResponseEntity.ok("Voter Deleted Successfully");
    }

    @PutMapping("/{id}")
@PreAuthorize("hasRole('DISTRICT_OFFICER')")
public ResponseEntity<VoterResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateVoterRequest request,
        Authentication authentication) {

    return ResponseEntity.ok(

            voterService.update(id, request, authentication)

    );

    }

    @GetMapping("/search/district")
@PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
public ResponseEntity<List<VoterResponse>> district(
        @RequestParam Long districtId) {

    return ResponseEntity.ok(
            voterService.searchByDistrict(districtId)
    );
    }

    @GetMapping("/search/officer")
@PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
public ResponseEntity<List<VoterResponse>> officer(
        @RequestParam Long officerId) {

    return ResponseEntity.ok(
            voterService.searchByOfficer(officerId)
    );
    }

    @GetMapping("/search/shehia")
@PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
public ResponseEntity<List<VoterResponse>> shehia(
        @RequestParam Long shehiaId) {

    return ResponseEntity.ok(
            voterService.searchByShehia(shehiaId)
    );
    }

    @GetMapping("/search/date")
@PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
public ResponseEntity<List<VoterResponse>> date(

        @RequestParam LocalDateTime start,

        @RequestParam LocalDateTime end) {

    return ResponseEntity.ok(
            voterService.searchByDate(start, end)
    );
}

   
}