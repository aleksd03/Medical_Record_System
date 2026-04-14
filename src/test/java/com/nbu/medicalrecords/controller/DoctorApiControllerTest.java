package com.nbu.medicalrecords.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.medicalrecords.data.entity.Doctor;
import com.nbu.medicalrecords.dto.CreateDoctorDto;
import com.nbu.medicalrecords.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test", "test-local"})
public class DoctorApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DoctorService doctorService;

    private Doctor doctor;
    private CreateDoctorDto createDoctorDto;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("Ivan");
        doctor.setLastName("Kolev");
        doctor.setIdentificationNumber("IK001");
        doctor.setSpecialty("Cardiology");
        doctor.setGp(true);

        createDoctorDto = new CreateDoctorDto();
        createDoctorDto.setFirstName("Ivan");
        createDoctorDto.setLastName("Kolev");
        createDoctorDto.setIdentificationNumber("IK001");
        createDoctorDto.setSpecialty("Cardiology");
        createDoctorDto.setGp(true);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getAllDoctors_ShouldReturn200() throws Exception {
        when(doctorService.getAllDoctors()).thenReturn(List.of(doctor));

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getDoctorById_WhenExists_ShouldReturn200() throws Exception {
        when(doctorService.getDoctorById(1L)).thenReturn(doctor);

        mockMvc.perform(get("/api/doctors/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void createDoctor_ShouldReturn200() throws Exception {
        when(doctorService.createDoctor(any())).thenReturn(doctor);

        mockMvc.perform(post("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDoctorDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "PATIENT")
    void createDoctor_AsPatient_ShouldReturn403() throws Exception {
        mockMvc.perform(post("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDoctorDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllDoctors_WithoutAuth_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isUnauthorized());
    }
}
