package com.example.hotel_back.facility.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel_back.facility.entity.Facility;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
  Optional<Facility> findByServiceName(String serviceName);
}
