package com.harshit.HospitalManagementApplication;

import com.harshit.HospitalManagementApplication.entity.Appointment;
import com.harshit.HospitalManagementApplication.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AppointmentTest
{
    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testCreateAppointment()
    {
        Appointment appointment = Appointment.builder().
                appointmentTime(LocalDateTime.of(2025,12,1,14,00)).
                reason("Cancer").
                build();

//        var newAppointment = appointmentService.createNewAppointment(appointment,1L,2L);
//        System.out.println(newAppointment);
//        var updatedAppointment=appointmentService.reAssignAppointmentToAnotherDoctor(newAppointment.getId(), 3L);
//        System.out.println(updatedAppointment);
    }

}
