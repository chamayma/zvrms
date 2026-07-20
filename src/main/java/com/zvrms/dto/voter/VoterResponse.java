package com.zvrms.dto.voter;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VoterResponse {

    private Long id;

    private String fullName;

    private String voterNumber;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    private String address;

    private String sex;

    private String placeOfBirth;

    private LocalDate issueDate;

    private String district;

    private String shehia;

    private String registeredBy;

    private LocalDateTime createdAt;

}