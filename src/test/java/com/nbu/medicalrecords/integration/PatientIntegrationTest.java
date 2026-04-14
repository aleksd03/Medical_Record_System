package com.nbu.medicalrecords.integration;

import com.nbu.medicalrecords.data.entity.Patient;
import com.nbu.medicalrecords.data.repository.PatientRepository;
import com.nbu.medicalrecords.service.PatientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test", "test-local"})
public class PatientIntegrationTest {
    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @AfterEach
    void tearDown() {
        patientRepository.deleteAll();
    }

    @Test
    void createAndRetrievePatient() {
        Patient patient = new Patient();
        patient.setFirstName("Peter");
        patient.setLastName("Nicolov");
        patient.setEgn("8503191234");
        patient.setHealthInsured(false);

        Patient saved = patientService.createPatient(patient);

        assertNotNull(saved.getId());
        assertEquals("Peter", saved.getFirstName());

        Patient found = patientService.getPatientById(saved.getId());
        assertEquals("8503191234", found.getEgn());
    }

    @Test
    void getAllPatients_ShouldReturnAll() {
        Patient patient1 = new Patient();
        patient1.setFirstName("Peter");
        patient1.setLastName("Nicolov");
        patient1.setEgn("8503191234");
        patient1.setHealthInsured(false);

        Patient patient2 = new Patient();
        patient2.setFirstName("Maria");
        patient2.setLastName("Ivanova");
        patient2.setEgn("9001011234");
        patient2.setHealthInsured(true);

        patientService.createPatient(patient1);
        patientService.createPatient(patient2);

        assertEquals(2, patientService.getAllPatients().size());
    }

    @Test
    void deletePatient_ShouldRemoveFromDatabase() {
        Patient patient = new Patient();
        patient.setFirstName("Peter");
        patient.setLastName("Nicolov");
        patient.setEgn("8503191235");
        patient.setHealthInsured(false);

        Patient saved = patientService.createPatient(patient);
        patientService.deletePatient(saved.getId());

        assertEquals(0, patientRepository.count());
    }
}
