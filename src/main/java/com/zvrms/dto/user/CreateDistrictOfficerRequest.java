package com.zvrms.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDistrictOfficerRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Long districtId;

}