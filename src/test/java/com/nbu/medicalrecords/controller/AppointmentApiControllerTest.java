package com.nbu.medicalrecords.controller;

import com.nbu.medicalrecords.data.entity.Appointment;
import com.nbu.medicalrecords.data.entity.Patient;
import com.nbu.medicalrecords.data.entity.User;
import com.nbu.medicalrecords.service.AppointmentService;
import com.nbu.medicalrecords.service.StatisticsService;
import com.nbu.medicalrecords.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test", "test-local"})
public class AppointmentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private UserService userService;

    @MockBean
    private StatisticsService statisticsService;

    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment = new Appointment();
        appointment.setId(1L);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getAllAppointments_ShouldReturn200() throws Exception {
        when(appointmentService.getAllAppointments()).thenReturn(List.of(appointment));
        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "PATIENT")
    void deleteAppointment_AsPatient_ShouldReturn403() throws Exception {
        mockMvc.perform(delete("/api/appointments/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "PATIENT")
    void getMyAppointments_AsPatient_ShouldReturn200() throws Exception {
        User mockUser = new User();
        Patient mockPatient = new Patient();
        mockPatient.setId(1L);
        mockUser.setPatient(mockPatient);

        when(userService.getCurrentUser()).thenReturn(mockUser);
        when(statisticsService.getAppointmentHistoryForPatient(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/appointments/my-appointments"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllAppointments_WithoutAuth_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isUnauthorized());
    }
}
