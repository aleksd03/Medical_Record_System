package com.nbu.medicalrecords.service;

import com.nbu.medicalrecords.data.entity.Diagnosis;
import com.nbu.medicalrecords.data.repository.DiagnosisRepository;
import com.nbu.medicalrecords.exception.DiagnosisNotFoundException;
import com.nbu.medicalrecords.service.impl.DiagnosisServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiagnosisServiceImplTest {
    @Mock
    private DiagnosisRepository diagnosisRepository;

    @InjectMocks
    private DiagnosisServiceImpl diagnosisService;

    private Diagnosis diagnosis;

    @BeforeEach
    void setUp() {
        diagnosis = new Diagnosis();
        diagnosis.setId(1L);
        diagnosis.setName("Grip");
        diagnosis.setDescription("Virusna infectsia");
    }

    @Test
    void getAllDiagnoses_ShouldReturnAllDiagnoses() {
        when(diagnosisRepository.findAll()).thenReturn(List.of(diagnosis));

        List<Diagnosis> result = diagnosisService.getAllDiagnoses();

        assertEquals(1, result.size());
        assertEquals("Grip", result.get(0).getName());
        verify(diagnosisRepository, times(1)).findAll();
    }

    @Test
    void getDiagnosisById_WhenExists_ShouldReturnDiagnosis() {
        when(diagnosisRepository.findById(1L)).thenReturn(Optional.of(diagnosis));

        Diagnosis result = diagnosisService.getDiagnosisById(1L);

        assertEquals("Grip", result.getName());
    }

    @Test
    void getDiagnosisById_WhenNotExists_ShouldThrowException() {
        when(diagnosisRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DiagnosisNotFoundException.class, () -> diagnosisService.getDiagnosisById(99L));
    }

    @Test
    void createDiagnosis_ShouldSaveAndReturnDiagnosis() {
        when(diagnosisRepository.save(diagnosis)).thenReturn(diagnosis);

        Diagnosis result = diagnosisService.createDiagnosis(diagnosis);

        assertEquals("Grip", result.getName());
        verify(diagnosisRepository, times(1)).save(diagnosis);
    }

    @Test
    void deleteDiagnosis_ShouldCallRepository() {
        diagnosisService.deleteDiagnosis(1L);

        verify(diagnosisRepository, times(1)).deleteById(1L);
    }
}
