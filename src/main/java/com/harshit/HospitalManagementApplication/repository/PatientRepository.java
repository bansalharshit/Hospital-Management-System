package com.harshit.HospitalManagementApplication.repository;

import com.harshit.HospitalManagementApplication.dto.BloodGroupCountResponseEntity;
import com.harshit.HospitalManagementApplication.entity.Patient;
import com.harshit.HospitalManagementApplication.entity.type.BloodGroupType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

    Patient findByName(String priyaSharma);

    List<Patient> findByBirthDateOrEmail(LocalDate birthDate, String email);

    List<Patient> findByBirthDateBetween(LocalDate startDate,LocalDate endDate);

    List<Patient> findByNameContainingOrderByIdDesc(String query);

    @Query("SELECT p from Patient p where p.bloodGroup = ?1")  // here ?1 is parameter place or index it could be ?2,?3
    List<Patient> findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroup);

    @Query("SELECT p from Patient p where p.birthDate > :birthDate") // here first birthDate is table column name and second bithDate is parameter pass in @Parm()
    List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate birthDate);

    @Query("SELECT p.bloodGroup, Count(p) from Patient p group by p.bloodGroup")
    List<Object[]> countEachBloodGroupType();

    @Query(value = "SELECT * from patient",nativeQuery = true)
    List<Patient> findAllPatients();


    @Transactional
    @Modifying
    @Query("UPDATE Patient p SET p.name = :name, p.email = :email where p.id = :id")
    int updateNameAndEmailWithId(@Param("name") String name,@Param("email") String email,@Param("id") Long id);

    // Important Concept Projection
  //  In Spring Boot (more precisely in Spring Data JPA), a projection is a way to fetch only specific fields from your database instead of the whole entity.
//    1️⃣ What is Projection?
//
//    Normally, if you fetch an entity:
//
//    Patient patient = patientRepository.findById(1L).get();
//
//
//    Hibernate will generate SQL like:
//
//    SELECT * FROM patient WHERE id = 1;
//
//
//    It loads all columns from the patient table — even if you only need name and email.
//
//    A projection tells JPA:
//
//            “I don’t want the whole entity, just these specific columns.”

    @Query("SELECT new com.harshit.HospitalManagementApplication.dto.BloodGroupCountResponseEntity(p.bloodGroup, COUNT(p)) " +
            "FROM Patient p GROUP BY p.bloodGroup")
    List<BloodGroupCountResponseEntity> countEachBloodGroupTypeUsingProjection();

    @Query(value = "SELECT * from patient",nativeQuery = true)
    Page<Patient> findAllPatientsUsingPage(Pageable pageable);

    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments") // this query is sufficient if you FETCH Type is Lazy over doctor column inside Appointment Class
   // @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments a LEFT JOIN FETCH a.doctor")// if you do not want to use lazy loading over doctor column inside appointment Class
    List<Patient>findAllPatientsWithAppointment();

}
