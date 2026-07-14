package com.zvrms.dto.user;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String fullName;

    private String username;

    private String role;

    private String district;

    private boolean active;

}