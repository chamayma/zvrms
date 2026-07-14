package com.zvrms.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateDistrictOfficerRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String username;

    @NotNull
    private Long districtId;

    private boolean active;

}