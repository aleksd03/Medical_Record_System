package com.nbu.medicalrecords.service;

import com.nbu.medicalrecords.data.entity.Patient;
import com.nbu.medicalrecords.data.repository.PatientRepository;
import com.nbu.medicalrecords.exception.PatientNotFoundException;
import com.nbu.medicalrecords.service.impl.PatientServiceImpl;
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
public class PatientServiceImplTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Peter");
        patient.setLastName("Nicolov");
        patient.setEgn("8503191234");
        patient.setHealthInsured(false);
    }

    @Test
    void getAllPatients_ShouldReturnAllPatients() {
        when(patientRepository.findAll()).thenReturn(List.of(patient));

        List<Patient> result = patientService.getAllPatients();

        assertEquals(1, result.size());
        assertEquals("Peter", result.get(0).getFirstName());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void getPatientById_WhenExists_ShouldReturnPatient() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientById(1L);

        assertEquals("Peter", result.getFirstName());
        assertEquals("8503191234", result.getEgn());
    }

    @Test
    void getPatientById_WhenNotExists_ShouldThrowException() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(99L));
    }

    @Test
    void createPatient_ShouldSaveAndReturnPatient() {
        when(patientRepository.save(patient)).thenReturn(patient);

        Patient result = patientService.createPatient(patient);

        assertEquals("Peter", result.getFirstName());
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void deletePatient_ShouldCallRepository() {
        patientService.deletePatient(1L);

        verify(patientRepository, times(1)).deleteById(1L);
    }
}
