package com.nbu.medicalrecords.service;

import com.nbu.medicalrecords.data.entity.Appointment;
import com.nbu.medicalrecords.data.entity.Doctor;
import com.nbu.medicalrecords.data.entity.Patient;
import com.nbu.medicalrecords.data.entity.Diagnosis;
import com.nbu.medicalrecords.data.repository.AppointmentRepository;
import com.nbu.medicalrecords.exception.AppointmentNotFoundException;
import com.nbu.medicalrecords.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private Appointment appointment;

    @BeforeEach
    void setUp() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("Ivan");
        doctor.setLastName("Kolev");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Peter");
        patient.setLastName("Nicolov");

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setId(1L);
        diagnosis.setName("Grip");

        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDate(LocalDate.of(2026, 4, 10));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDiagnosis(diagnosis);
        appointment.setTreatment("Pochivka");
        appointment.setPrice(BigDecimal.valueOf(50));
    }

    @Test
    void getAllAppointments_ShouldReturnAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(List.of(appointment));

        List<Appointment> result = appointmentService.getAllAppointments();

        assertEquals(1, result.size());
        assertEquals(LocalDate.of(2026, 4, 10), result.get(0).getDate());
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void getAppointmentById_WhenExists_ShouldReturnAppointment() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        Appointment result = appointmentService.getAppointmentById(1L);

        assertEquals("Pochivka", result.getTreatment());
        assertEquals(BigDecimal.valueOf(50), result.getPrice());
    }

    @Test
    void getAppointmentById_WhenNotExists_ShouldThrowException() {
        when(appointmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.getAppointmentById(99L));
    }

    @Test
    void createAppointment_ShouldSaveAndReturnAppointment() {
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        Appointment result = appointmentService.createAppointment(appointment);

        assertEquals("Pochivka", result.getTreatment());
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void deleteAppointment_ShouldCallRepository() {
        appointmentService.deleteAppointment(1L);

        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}
