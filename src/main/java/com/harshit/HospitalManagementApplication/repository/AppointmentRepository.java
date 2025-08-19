package com.harshit.HospitalManagementApplication.repository;

import com.harshit.HospitalManagementApplication.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}