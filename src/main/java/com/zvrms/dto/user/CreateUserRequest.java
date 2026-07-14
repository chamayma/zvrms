package com.zvrms.dto.user;

import com.zvrms.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Role role;

    private Long districtId;

}