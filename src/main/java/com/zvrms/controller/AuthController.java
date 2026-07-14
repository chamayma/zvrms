package com.zvrms.controller;

import com.zvrms.dto.auth.ChangePasswordRequest;
import com.zvrms.dto.auth.LoginRequest;
import com.zvrms.dto.auth.LoginResponse;
import com.zvrms.dto.auth.ResetPasswordRequest;
import com.zvrms.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authenticationService.login(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<String> changePassword(
        @RequestBody ChangePasswordRequest request,
        Authentication authentication) {

    return ResponseEntity.ok(
            authenticationService.changePassword(request, authentication)
    );
    }

    @PutMapping("/reset-password/{userId}")
@PreAuthorize("hasAnyRole('DIRECTOR','SYSTEM_OFFICER')")
public ResponseEntity<String> resetPassword(
        @PathVariable Long userId,
        @RequestBody ResetPasswordRequest request) {

    return ResponseEntity.ok(
            authenticationService.resetPassword(userId, request)
    );
    }
}