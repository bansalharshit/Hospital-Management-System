package com.harshit.HospitalManagementApplication.service;

import com.harshit.HospitalManagementApplication.entity.Patient;
import com.harshit.HospitalManagementApplication.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private  final PatientRepository patientRepository;

    @Transactional
    public Patient getPatientById(Long id)
    {
        Patient p1 = patientRepository.findById(id).orElseThrow();
        Patient p2 = patientRepository.findById(id).orElseThrow();
        Patient p3 = patientRepository.findById(id).orElseThrow();
        Patient p4 = patientRepository.findById(id).orElseThrow();
        System.out.println(p1==p2);
        p1.setName("Harshit Bansal");
        p4.setEmail("harshitbansal394@gmail.com");// as object is in the persistence stat so we do not need to call save or persistence method once again
        return p1;

    }
}
