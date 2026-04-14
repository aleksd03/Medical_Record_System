package com.nbu.medicalrecords.integration;

import com.nbu.medicalrecords.data.entity.Appointment;
import com.nbu.medicalrecords.data.entity.Diagnosis;
import com.nbu.medicalrecords.data.entity.Doctor;
import com.nbu.medicalrecords.data.entity.Patient;
import com.nbu.medicalrecords.data.repository.*;
import com.nbu.medicalrecords.service.AppointmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test", "test-local"})
public class AppointmentIntegrationTest {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    private Doctor doctor;
    private Patient patient;
    private Diagnosis diagnosis;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setFirstName("Ivan");
        doctor.setLastName("Kolev");
        doctor.setIdentificationNumber("IK999");
        doctor.setSpecialty("Cardiology");
        doctor.setGp(false);
        doctor = doctorRepository.save(doctor);

        patient = new Patient();
        patient.setFirstName("Peter");
        patient.setLastName("Nicolov");
        patient.setEgn("8503191234");
        patient.setHealthInsured(false);
        patient = patientRepository.save(patient);

        diagnosis = new Diagnosis();
        diagnosis.setName("Grip");
        diagnosis.setDescription("Virusna infectsia");
        diagnosis = diagnosisRepository.save(diagnosis);
    }

    @AfterEach
    void tearDown() {
        appointmentRepository.deleteAll();
        patientRepository.deleteAll();
        diagnosisRepository.deleteAll();
        doctorRepository.deleteAll();
    }

    @Test
    void createAndRetrieveAppointment() {
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDate.of(2026, 4, 14));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDiagnosis(diagnosis);
        appointment.setTreatment("Pochivka");
        appointment.setPrice(BigDecimal.valueOf(50));

        Appointment saved = appointmentService.createAppointment(appointment);

        assertNotNull(saved.getId());
        assertEquals("Pochivka", saved.getTreatment());

        Appointment found = appointmentService.getAppointmentById(saved.getId());
        assertEquals(0, BigDecimal.valueOf(50).compareTo(found.getPrice()));
    }

    @Test
    void deleteAppointment_ShouldRemoveFromDatabase() {
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDate.of(2026, 4, 14));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDiagnosis(diagnosis);
        appointment.setTreatment("Pochivka");
        appointment.setPrice(BigDecimal.valueOf(50));

        Appointment saved = appointmentService.createAppointment(appointment);
        appointmentService.deleteAppointment(saved.getId());

        assertEquals(0, appointmentRepository.count());
    }
}
