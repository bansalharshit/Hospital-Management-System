package com.harshit.HospitalManagementApplication;

import com.harshit.HospitalManagementApplication.entity.Patient;
import com.harshit.HospitalManagementApplication.repository.PatientRepository;
import com.harshit.HospitalManagementApplication.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        List<Patient> patientList = patientRepository.findAll();
        System.out.println(patientList);

        Patient patient = new Patient();
        patientRepository.save(patient);
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
        Patient patient = patientRepository.findByName("Priya Sharma");
        System.out.println(patient);
    }
}
