package com.zvrms.dto.user;

import lombok.Data;

@Data
public class DistrictOfficerResponse {

    private Long id;

    private String fullName;

    private String username;

    private String district;

    private boolean active;

}