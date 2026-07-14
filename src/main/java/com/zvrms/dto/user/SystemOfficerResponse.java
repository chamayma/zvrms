package com.zvrms.dto.user;

import lombok.Data;

@Data
public class SystemOfficerResponse {

    private Long id;

    private String fullName;

    private String username;

    private boolean active;

}