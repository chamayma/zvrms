package com.zvrms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "voters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true, length = 9)
    @Pattern(regexp="^\\d{9}$")

    private String voterNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Pattern(regexp="^\\d{10}$")
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String sex;

    @Column
    private String placeOfBirth;

    @Column
    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "shehia_id", nullable = false)
    private Shehia shehia;

    @ManyToOne
    @JoinColumn(name = "registered_by", nullable = false)
    private User registeredBy;

    @Column(nullable = false, updatable = false)
private LocalDateTime createdAt;

private LocalDateTime updatedAt;

@Column(nullable = false)
private String createdBy;

private String updatedBy;

@PrePersist
public void prePersist() {
    createdAt = LocalDateTime.now();
}

@PreUpdate
public void preUpdate() {
    updatedAt = LocalDateTime.now();
}
}