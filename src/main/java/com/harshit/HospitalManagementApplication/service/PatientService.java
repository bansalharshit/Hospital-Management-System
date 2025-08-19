package com.harshit.HospitalManagementApplication.service;

import com.harshit.HospitalManagementApplication.dto.PatientResponseDto;
import com.harshit.HospitalManagementApplication.entity.Patient;
import com.harshit.HospitalManagementApplication.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private  final PatientRepository patientRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public PatientResponseDto getPatientById(Long patientId)
    {
        Patient patient = patientRepository.findById(patientId).orElseThrow(()->new EntityNotFoundException("No Patient Found with Id :"+patientId));
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    public List<PatientResponseDto> getAllPatients(Integer PageNumber,Integer PageSize)
    {
        return patientRepository.findAllPatientsUsingPage(PageRequest.of(PageNumber,PageSize)).
                stream().map(patient -> modelMapper.map(patient,PatientResponseDto.class)).collect(Collectors.toList());
    }
}
