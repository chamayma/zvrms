package com.zvrms.service;

import com.zvrms.dto.auth.ChangePasswordRequest;
import com.zvrms.dto.auth.LoginRequest;
import com.zvrms.dto.auth.LoginResponse;
import com.zvrms.dto.auth.ResetPasswordRequest;
import com.zvrms.entity.User;
import com.zvrms.repository.UserRepository;
import com.zvrms.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(
                        request.getUsername()
                );

        String token = jwtService.generateToken(userDetails);

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new LoginResponse(

        token,

        user.getUsername(),

        user.getRole().name(),

        user.getFullName(),

        user.getDistrict() == null
                ? null
                : user.getDistrict().getId(),

        user.getDistrict() == null
                ? null
                : user.getDistrict().getName()

);
    }

    public String changePassword(
        ChangePasswordRequest request,
        Authentication authentication) {

    User user = userRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
        throw new RuntimeException("Old password is incorrect.");
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));

    userRepository.save(user);

    return "Password changed successfully.";
    }

    public String resetPassword(
        Long userId,
        ResetPasswordRequest request) {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    user.setPassword(
            passwordEncoder.encode(request.getNewPassword())
    );

    userRepository.save(user);

    return "Password reset successfully.";
    }
}