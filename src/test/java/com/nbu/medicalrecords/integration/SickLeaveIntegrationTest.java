package com.nbu.medicalrecords.integration;

import com.nbu.medicalrecords.data.entity.*;
import com.nbu.medicalrecords.data.repository.*;
import com.nbu.medicalrecords.service.SickLeaveService;
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
public class SickLeaveIntegrationTest {
    @Autowired
    private SickLeaveService sickLeaveService;

    @Autowired
    private SickLeaveRepository sickLeaveRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    private Appointment appointment;

    @BeforeEach
    void setUp() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("Ivan");
        doctor.setLastName("Kolev");
        doctor.setIdentificationNumber("IK999");
        doctor.setSpecialty("Cardiology");
        doctor.setGp(false);
        doctor = doctorRepository.save(doctor);

        Patient patient = new Patient();
        patient.setFirstName("Peter");
        patient.setLastName("Nicolov");
        patient.setEgn("8503191234");
        patient.setHealthInsured(false);
        patient = patientRepository.save(patient);

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName("Grip");
        diagnosis.setDescription("Virusna infectsia");
        diagnosis = diagnosisRepository.save(diagnosis);

        appointment = new Appointment();
        appointment.setDate(LocalDate.of(2026, 4, 14));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDiagnosis(diagnosis);
        appointment.setTreatment("Pochivka");
        appointment.setPrice(BigDecimal.valueOf(50));
        appointment = appointmentRepository.save(appointment);
    }

    @AfterEach
    void tearDown() {
        sickLeaveRepository.deleteAll();
        appointmentRepository.deleteAll();
        patientRepository.deleteAll();
        diagnosisRepository.deleteAll();
        doctorRepository.deleteAll();
    }

    @Test
    void createAndRetrieveSickLeave() {
        SickLeave sickLeave = new SickLeave();
        sickLeave.setStartDate(LocalDate.of(2026, 4, 14));
        sickLeave.setNumberOfDays(5);
        sickLeave.setAppointment(appointment);

        SickLeave saved = sickLeaveService.createSickLeave(sickLeave);

        assertNotNull(saved.getId());
        assertEquals(5, saved.getNumberOfDays());

        SickLeave found = sickLeaveService.getSickLeaveById(saved.getId());
        assertEquals(LocalDate.of(2026, 4, 14), found.getStartDate());
    }

    @Test
    void deleteSickLeave_ShouldRemoveFromDatabase() {
        SickLeave sickLeave = new SickLeave();
        sickLeave.setStartDate(LocalDate.of(2026, 4, 14));
        sickLeave.setNumberOfDays(5);
        sickLeave.setAppointment(appointment);

        SickLeave saved = sickLeaveService.createSickLeave(sickLeave);
        sickLeaveService.deleteSickLeave(saved.getId());

        assertEquals(0, sickLeaveRepository.count());
    }
}
