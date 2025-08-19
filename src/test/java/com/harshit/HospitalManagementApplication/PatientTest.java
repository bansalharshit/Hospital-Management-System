package com.harshit.HospitalManagementApplication;

import com.harshit.HospitalManagementApplication.dto.BloodGroupCountResponseEntity;
import com.harshit.HospitalManagementApplication.entity.Patient;
import com.harshit.HospitalManagementApplication.entity.type.BloodGroupType;
import com.harshit.HospitalManagementApplication.repository.PatientRepository;
import com.harshit.HospitalManagementApplication.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class PatientTest {

    @Autowired
    public PatientRepository patientRepository;

    @Autowired
    public PatientService patientService;

    @Test
    public void testPatientRepository()
    {
        List<Patient> patientList = patientRepository.findAllPatientsWithAppointment();// if we use findALl() then we have to face problem of N+1 quries and to solve this problem we will make our custom function and then write jpql query on it
        System.out.println(patientList);

//        Patient patient = new Patient();
//        patientRepository.save(patient);
    }

    @Test
    public void testPatientMethods()
    {
        Patient patient = patientService.getPatientById(1L);
        Patient patient1 = patientRepository.findById(1L).orElseThrow(()->new EntityNotFoundException("Patient Not Found With Id : 1"));
        System.out.println(patient1);
    }

    @Test
    public void testTransactionMethods()
    {

        System.out.println("============findByName===================");

        Patient patient = patientRepository.findByName("Priya Sharma");
        System.out.println(patient);

        System.out.println("=============findByBirthDateOrEmail==================");

        List<Patient> patientList1 = patientRepository.findByBirthDateOrEmail(LocalDate.of(1998,07,13),"mohansharma@gmail.com");
        for(Patient patient1:patientList1)
            System.out.println(patient1);

        System.out.println("==============findByBirthDateBetween===================");

        List<Patient> patientList2 = patientRepository.findByBirthDateBetween(LocalDate.of(1995,01,01),LocalDate.of(2000,01,01));
        for(Patient patient2:patientList2)
            System.out.println(patient2);

        System.out.println("===============findByBloodGroup==================");

        List<Patient> patientList3 = patientRepository.findByBloodGroup(BloodGroupType.AB_NEGATIVE);
        for(Patient patient3:patientList3)
            System.out.println(patient3);

        System.out.println("================findByBornAfterDate=================");

        List<Patient> patientList4 = patientRepository.findByBornAfterDate(LocalDate.of(1997,01,01));
        for(Patient patient4:patientList4)
            System.out.println(patient4);

        System.out.println("==================countEachBloodGroupType===============");

        List<Object[]> bloodGroupList = patientRepository.countEachBloodGroupType();
        for(Object[] objects:bloodGroupList)
            System.out.println(Arrays.toString(objects));

        System.out.println("====================findAllPatients===================");

        List<Patient> patientList5 = patientRepository.findAllPatients();
        for(Patient patient5:patientList5)
            System.out.println(patient5);

        System.out.println("==============updateNameAndEmailWithId=============");

        int totalRowsEffected = patientRepository.updateNameAndEmailWithId("Neha Sharma","nehasharma@gmail.com",2L);
        System.out.println("Rows Effected "+totalRowsEffected);

        System.out.println("===========countEachBloodGroupTypeUsingProjection============");

        List<BloodGroupCountResponseEntity> bloodGroupList1 = patientRepository.countEachBloodGroupTypeUsingProjection();
        for(BloodGroupCountResponseEntity bloodGroupCountResponse:bloodGroupList1)
            System.out.println(bloodGroupCountResponse);

        System.out.println("====================findAllPatientsUsingPage===================");

        Page<Patient> patientsListByPage = patientRepository.findAllPatientsUsingPage(PageRequest.of(0,2, Sort.by("name")));
        for(Patient patient6:patientsListByPage)
            System.out.println(patient6);
    }
}
