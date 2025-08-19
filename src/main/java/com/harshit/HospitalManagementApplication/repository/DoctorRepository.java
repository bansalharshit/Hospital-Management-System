package com.harshit.HospitalManagementApplication.repository;

import com.harshit.HospitalManagementApplication.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}