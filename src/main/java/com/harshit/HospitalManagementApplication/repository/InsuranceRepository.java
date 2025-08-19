package com.harshit.HospitalManagementApplication.repository;

import com.harshit.HospitalManagementApplication.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}