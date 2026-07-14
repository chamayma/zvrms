package com.zvrms.service;

import com.zvrms.dto.user.CreateSystemOfficerRequest;
import com.zvrms.dto.user.SystemOfficerResponse;
import com.zvrms.dto.user.UpdateSystemOfficerRequest;
import com.zvrms.entity.User;
import com.zvrms.enums.Role;
import com.zvrms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemOfficerService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SystemOfficerResponse create(CreateSystemOfficerRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User officer = new User();

        officer.setFullName(request.getFullName());
        officer.setUsername(request.getUsername());
        officer.setPassword(passwordEncoder.encode(request.getPassword()));
        officer.setRole(Role.SYSTEM_OFFICER);
        officer.setActive(true);

        officer = userRepository.save(officer);

        return map(officer);
    }

    public List<SystemOfficerResponse> getAll() {

        return userRepository.findByRole(Role.SYSTEM_OFFICER)
                .stream()
                .map(this::map)
                .toList();
    }

    public void delete(Long id) {

        userRepository.deleteById(id);

    }

    private SystemOfficerResponse map(User user) {

        SystemOfficerResponse response = new SystemOfficerResponse();

        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setUsername(user.getUsername());
        response.setActive(user.isActive());

        return response;
    }

    public SystemOfficerResponse update(Long id,
                                    UpdateSystemOfficerRequest request) {

    User officer = userRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("System Officer not found"));

    officer.setFullName(request.getFullName());
    officer.setUsername(request.getUsername());
    officer.setActive(request.isActive());

    officer = userRepository.save(officer);

    return map(officer);

    }
}