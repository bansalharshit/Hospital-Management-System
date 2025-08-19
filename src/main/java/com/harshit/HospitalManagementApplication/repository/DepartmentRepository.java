package com.harshit.HospitalManagementApplication.repository;

import com.harshit.HospitalManagementApplication.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}