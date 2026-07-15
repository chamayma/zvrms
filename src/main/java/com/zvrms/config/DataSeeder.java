package com.zvrms.config;

import com.zvrms.entity.User;
import com.zvrms.enums.Role;
import com.zvrms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.count() > 0) {
            return;
        }

        User director = new User();

        director.setFullName("System Director");
        director.setUsername("director");
        director.setPassword(passwordEncoder.encode("director123"));
        director.setRole(Role.DIRECTOR);
        director.setActive(true);

        userRepository.save(director);

        User systemOfficer = new User();

        systemOfficer.setFullName("System Officer");
        systemOfficer.setUsername("system");
        systemOfficer.setPassword(passwordEncoder.encode("system123"));
        systemOfficer.setRole(Role.SYSTEM_OFFICER);
        systemOfficer.setActive(true);

        userRepository.save(systemOfficer);

        System.out.println("====================================");
        System.out.println("Default Users Created Successfully");
        System.out.println("====================================");
    }

}