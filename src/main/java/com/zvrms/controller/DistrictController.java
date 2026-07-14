package com.zvrms.controller;

import com.zvrms.entity.District;
import com.zvrms.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DistrictController {

    private final DistrictService districtService;

    @PostMapping
    public ResponseEntity<District> create(
            @RequestBody District district) {

        return ResponseEntity.ok(
                districtService.create(district)
        );
    }

    @GetMapping
    public ResponseEntity<List<District>> getAll() {

        return ResponseEntity.ok(
                districtService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<District> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                districtService.getById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<District> update(
            @PathVariable Long id,
            @RequestBody District district) {

        return ResponseEntity.ok(
                districtService.update(id, district)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        districtService.delete(id);

        return ResponseEntity.ok("District Deleted Successfully");
    }
}