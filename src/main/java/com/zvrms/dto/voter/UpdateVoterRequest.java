package com.zvrms.dto.voter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateVoterRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100)
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must contain exactly 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Size(min = 3, max = 150)
    private String address;

    @NotBlank(message = "Sex is required")
    private String sex;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Please select Shehia")
    private Long shehiaId;

}