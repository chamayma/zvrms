package com.zvrms.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSystemOfficerRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String username;

    private boolean active;

}