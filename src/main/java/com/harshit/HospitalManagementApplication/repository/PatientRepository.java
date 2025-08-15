package com.harshit.HospitalManagementApplication.repository;

import com.harshit.HospitalManagementApplication.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    Patient findByName(String priyaSharma);
    Patient findByBirthDate(LocalDate birthDate);
}
