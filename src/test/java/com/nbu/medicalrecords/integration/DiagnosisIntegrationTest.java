package com.nbu.medicalrecords.integration;

import com.nbu.medicalrecords.data.entity.Diagnosis;
import com.nbu.medicalrecords.data.repository.DiagnosisRepository;
import com.nbu.medicalrecords.service.DiagnosisService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test", "test-local"})
public class DiagnosisIntegrationTest {
    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @AfterEach
    void tearDown() {
        diagnosisRepository.deleteAll();
    }

    @Test
    void createAndRetrieveDiagnosis() {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName("Grip");
        diagnosis.setDescription("Virusna infectsia");

        Diagnosis saved = diagnosisService.createDiagnosis(diagnosis);

        assertNotNull(saved.getId());
        assertEquals("Grip", saved.getName());

        Diagnosis found = diagnosisService.getDiagnosisById(saved.getId());
        assertEquals("Virusna infectsia", found.getDescription());
    }

    @Test
    void getAllDiagnoses_ShouldReturnAll() {
        Diagnosis diagnosis1 = new Diagnosis();
        diagnosis1.setName("Grip");
        diagnosis1.setDescription("Virusna infectsia");

        Diagnosis diagnosis2 = new Diagnosis();
        diagnosis2.setName("Bronhit");
        diagnosis2.setDescription("Vazpalenie na bronhite");

        diagnosisService.createDiagnosis(diagnosis1);
        diagnosisService.createDiagnosis(diagnosis2);

        assertEquals(2, diagnosisService.getAllDiagnoses().size());
    }

    @Test
    void deleteDiagnosis_ShouldRemoveFromDatabase() {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setName("Grip2");
        diagnosis.setDescription("Virusna infectsia");

        Diagnosis saved = diagnosisService.createDiagnosis(diagnosis);
        diagnosisService.deleteDiagnosis(saved.getId());

        assertEquals(0, diagnosisRepository.count());
    }
}
