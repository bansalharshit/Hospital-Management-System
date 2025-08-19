package com.harshit.HospitalManagementApplication.service;

import com.harshit.HospitalManagementApplication.dto.AppointmentResponseDto;
import com.harshit.HospitalManagementApplication.dto.CreateAppointmentRequestDto;
import com.harshit.HospitalManagementApplication.entity.Appointment;
import com.harshit.HospitalManagementApplication.entity.Doctor;
import com.harshit.HospitalManagementApplication.entity.Patient;
import com.harshit.HospitalManagementApplication.repository.AppointmentRepository;
import com.harshit.HospitalManagementApplication.repository.DoctorRepository;
import com.harshit.HospitalManagementApplication.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.print.Doc;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    @Transactional
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto createAppointmentRequestDto)
    {
        Long doctorId = createAppointmentRequestDto.getDoctorId();
        Long patientId = createAppointmentRequestDto.getPatientId();

        Doctor doctor =doctorRepository.findById(doctorId).orElseThrow(()->new IllegalArgumentException("Doctor not found withh id :"+doctorId));
        Patient patient = patientRepository.findById(patientId).orElseThrow(()->new IllegalArgumentException("Patient Not Found with Id :"+patientId));

        Appointment appointment = Appointment.builder().
                reason(createAppointmentRequestDto.getReason())
                .appointmentTime(createAppointmentRequestDto.
                        getAppointmentTime()).
                build();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        patient.getAppointments().add(appointment); // to maintain consistency
      appointment=appointmentRepository.save(appointment);
      return modelMapper.map(appointment,AppointmentResponseDto.class);

    }

    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId,Long doctorId)
    {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        appointment.setDoctor(doctor);
        doctor.getAppointments().add(appointment); // just for bidirectional
        return appointment;
    }
}
