package com.nbu.medicalrecords.service;

import com.nbu.medicalrecords.data.entity.Appointment;
import com.nbu.medicalrecords.data.entity.SickLeave;
import com.nbu.medicalrecords.data.repository.SickLeaveRepository;
import com.nbu.medicalrecords.exception.SickLeaveNotFoundException;
import com.nbu.medicalrecords.service.impl.SickLeaveServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SickLeaveServiceImplTest {
    @Mock
    private SickLeaveRepository sickLeaveRepository;

    @InjectMocks
    private SickLeaveServiceImpl sickLeaveService;

    private SickLeave sickLeave;

    @BeforeEach
    void setUp() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);

        sickLeave = new SickLeave();
        sickLeave.setId(1L);
        sickLeave.setStartDate(LocalDate.of(2026, 4, 10));
        sickLeave.setNumberOfDays(3);
        sickLeave.setAppointment(appointment);
    }

    @Test
    void getAllSickLeaves_ShouldReturnAllSickLeaves() {
        when(sickLeaveRepository.findAll()).thenReturn(List.of(sickLeave));

        List<SickLeave> result = sickLeaveService.getAllSickLeaves();

        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getNumberOfDays());
        verify(sickLeaveRepository, times(1)).findAll();
    }

    @Test
    void getSickLeaveById_WhenExists_ShouldReturnSickLeave() {
        when(sickLeaveRepository.findById(1L)).thenReturn(Optional.of(sickLeave));

        SickLeave result = sickLeaveService.getSickLeaveById(1L);

        assertEquals(3, result.getNumberOfDays());
        assertEquals(LocalDate.of(2026, 4, 10), result.getStartDate());
    }

    @Test
    void getSickLeaveById_WhenNotExists_ShouldThrowException() {
        when(sickLeaveRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(SickLeaveNotFoundException.class, () -> sickLeaveService.getSickLeaveById(99L));
    }

    @Test
    void createSickLeave_ShouldSaveAndReturnSickLeave() {
        when(sickLeaveRepository.save(sickLeave)).thenReturn(sickLeave);

        SickLeave result = sickLeaveService.createSickLeave(sickLeave);

        assertEquals(3, result.getNumberOfDays());
        verify(sickLeaveRepository, times(1)).save(sickLeave);
    }

    @Test
    void deleteSickLeave_ShouldCallRepository() {
        sickLeaveService.deleteSickLeave(1L);

        verify(sickLeaveRepository, times(1)).deleteById(1L);
    }
}
