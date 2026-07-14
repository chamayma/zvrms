package com.zvrms.config;

import com.zvrms.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(Customizer.withDefaults())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

.requestMatchers(
"/api/auth/**",
"/swagger-ui/**",
"/swagger-ui.html",
"/v3/api-docs/**"
).permitAll()

.requestMatchers("/api/system-officers/**")
.hasRole("DIRECTOR")

.requestMatchers("/api/district-officers/**")
.hasAnyRole("DIRECTOR","SYSTEM_OFFICER")

.requestMatchers("/api/voters/**")
.hasAnyRole("DIRECTOR","SYSTEM_OFFICER","DISTRICT_OFFICER")

.requestMatchers("/api/reports/**")
.hasAnyRole("DIRECTOR","SYSTEM_OFFICER")

.requestMatchers("/api/dashboard/**")
.hasAnyRole("DIRECTOR","SYSTEM_OFFICER","DISTRICT_OFFICER")

.anyRequest()
.authenticated())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();

    }
}