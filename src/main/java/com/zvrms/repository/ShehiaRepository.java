package com.zvrms.repository;

import com.zvrms.entity.District;
import com.zvrms.entity.Shehia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShehiaRepository extends JpaRepository<Shehia, Long> {

    List<Shehia> findByDistrict(District district);

    Optional<Shehia> findByName(String name);

}