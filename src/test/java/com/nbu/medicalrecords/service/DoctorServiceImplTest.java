package com.nbu.medicalrecords.service;

import com.nbu.medicalrecords.data.entity.Doctor;
import com.nbu.medicalrecords.data.repository.DoctorRepository;
import com.nbu.medicalrecords.exception.DoctorNotFoundException;
import com.nbu.medicalrecords.service.impl.DoctorServiceImpl;
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
public class DoctorServiceImplTest {
    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("Ivan");
        doctor.setLastName("Kolev");
        doctor.setIdentificationNumber("IK001");
        doctor.setSpecialty("Cardiology");
        doctor.setGp(true);
    }

    @Test
    void getAllDoctors_ShouldReturnAllDoctors() {
        when(doctorRepository.findAll()).thenReturn(List.of(doctor));

        List<Doctor> result = doctorService.getAllDoctors();

        assertEquals(1, result.size());
        assertEquals("Ivan", result.get(0).getFirstName());
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void getDoctorById_WhenExists_ShouldReturnDoctor() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        Doctor result = doctorService.getDoctorById(1L);

        assertEquals("Ivan", result.getFirstName());
        assertEquals("IK001", result.getIdentificationNumber());
    }

    @Test
    void getDoctorById_WhenNotExists_ShouldThrowException() {
        when(doctorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorById(99L));
    }

    @Test
    void createDoctor_ShouldSaveAndReturnDoctor() {
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        Doctor result = doctorService.createDoctor(doctor);

        assertEquals("Ivan", result.getFirstName());
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void deleteDoctor_ShouldCallRepository() {
        doctorService.deleteDoctor(1L);

        verify(doctorRepository, times(1)).deleteById(1L);
    }
}
