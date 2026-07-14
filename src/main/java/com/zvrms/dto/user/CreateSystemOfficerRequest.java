package com.zvrms.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSystemOfficerRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}