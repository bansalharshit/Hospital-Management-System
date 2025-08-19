package com.harshit.HospitalManagementApplication.controller;

import com.harshit.HospitalManagementApplication.dto.AppointmentResponseDto;
import com.harshit.HospitalManagementApplication.dto.CreateAppointmentRequestDto;
import com.harshit.HospitalManagementApplication.dto.PatientResponseDto;
import com.harshit.HospitalManagementApplication.service.AppointmentService;
import com.harshit.HospitalManagementApplication.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto));
    }

    @GetMapping("/profile")
    public ResponseEntity<PatientResponseDto> getPatientProfile()
    {
        Long patientId =4L;
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }
}
