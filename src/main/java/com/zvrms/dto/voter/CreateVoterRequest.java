package com.zvrms.dto.voter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateVoterRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
    private String fullName;

    @NotBlank(message = "Voter number is required")
    @Pattern(regexp = "^\\d{9}$", message = "Voter number must contain exactly 9 digits")
    private String voterNumber;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must contain exactly 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Size(min = 3, max = 150)
    private String address;

    @NotBlank(message = "Sex is required")
    private String sex;

    @NotBlank(message = "Place of birth is required")
    private String placeOfBirth;

    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;

    @NotNull(message = "Please select Shehia")
    private Long shehiaId;

}