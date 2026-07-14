package com.zvrms.repository;

import com.zvrms.entity.District;
import com.zvrms.entity.Shehia;
import com.zvrms.entity.User;
import com.zvrms.entity.Voter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface VoterRepository extends JpaRepository<Voter, Long> {

    Optional<Voter> findByVoterNumber(String voterNumber);

    boolean existsByVoterNumber(String voterNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Voter> findByDistrict(District district);

    List<Voter> findByShehia(Shehia shehia);

    List<Voter> findByRegisteredBy(User user);

    List<Voter> findByDistrictAndShehia(District district, Shehia shehia);

    List<Voter> findByFullNameContainingIgnoreCase(String fullName);

    List<Voter> findByPhoneNumberContaining(String phoneNumber);

    List<Voter> findBySexIgnoreCase(String sex);

    long countBySexIgnoreCase(String sex);

    Page<Voter> findAll(Pageable pageable);

    List<Voter> findByCreatedAtBetween(LocalDateTime start,
                                   LocalDateTime end);

boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

boolean existsByVoterNumberAndIdNot(String voterNumber, Long id);    

long countByDistrict(District district);

long countByDistrictAndSexIgnoreCase(District district, String sex);

List<Voter> findTop10ByOrderByCreatedAtDesc();

List<Voter> findTop10ByDistrictOrderByCreatedAtDesc(District district);

long countByCreatedAtBetween(LocalDateTime start,
                             LocalDateTime end);

}