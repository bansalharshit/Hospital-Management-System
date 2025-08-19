package com.harshit.HospitalManagementApplication.service;


import com.harshit.HospitalManagementApplication.entity.Insurance;
import com.harshit.HospitalManagementApplication.entity.Patient;
import com.harshit.HospitalManagementApplication.repository.InsuranceRepository;
import com.harshit.HospitalManagementApplication.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

        private final InsuranceRepository insuranceRepository;
        private final PatientRepository patientRepository;

        @Transactional
        public Patient assignInsuranceToPatient(Insurance insurance,Long patientId)
        {
            Patient patient = patientRepository.findById(patientId).orElseThrow(()->new EntityNotFoundException("patient not found with id :"+patientId));
            patient.setInsurance(insurance);
            insurance.setPatient(patient);
            return patient;
        }
        @Transactional
        public Patient disassociateInsuranceFromPatient(Long patientId)
        {
            Patient patient = patientRepository.findById(patientId).orElseThrow(()->new EntityNotFoundException("Patient Not Found With Id: "+ patientId));
            patient.setInsurance(null);
            return patient;
        }
}
