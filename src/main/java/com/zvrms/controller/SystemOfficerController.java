package com.zvrms.controller;

import com.zvrms.dto.user.CreateSystemOfficerRequest;
import com.zvrms.dto.user.SystemOfficerResponse;
import com.zvrms.dto.user.UpdateSystemOfficerRequest;
import com.zvrms.service.SystemOfficerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system-officers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SystemOfficerController {

    private final SystemOfficerService systemOfficerService;

    @PostMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<SystemOfficerResponse> create(
            @Valid @RequestBody CreateSystemOfficerRequest request) {

        return ResponseEntity.ok(
                systemOfficerService.create(request)
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<List<SystemOfficerResponse>> getAll() {

        return ResponseEntity.ok(
                systemOfficerService.getAll()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        systemOfficerService.delete(id);

        return ResponseEntity.ok("System Officer Deleted Successfully");
    }

    @PutMapping("/{id}")
@PreAuthorize("hasRole('DIRECTOR')")
public ResponseEntity<SystemOfficerResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateSystemOfficerRequest request) {

    return ResponseEntity.ok(

            systemOfficerService.update(id, request)

    );

    }

}