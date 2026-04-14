package com.nbu.medicalrecords.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.medicalrecords.data.entity.Patient;
import com.nbu.medicalrecords.dto.CreatePatientDto;
import com.nbu.medicalrecords.service.PatientService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test", "test-local"})
public class PatientApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    private Patient patient;
    private CreatePatientDto createPatientDto;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("Peter");
        patient.setLastName("Nicolov");
        patient.setEgn("8503191234");
        patient.setHealthInsured(false);

        createPatientDto = new CreatePatientDto();
        createPatientDto.setFirstName("Peter");
        createPatientDto.setLastName("Nicolov");
        createPatientDto.setEgn("8503191234");
        createPatientDto.setHealthInsured(false);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getAllPatients_ShouldReturn200() throws Exception {
        when(patientService.getAllPatients()).thenReturn(List.of(patient));
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getPatientById_WhenExists_ShouldReturn200() throws Exception {
        when(patientService.getPatientById(1L)).thenReturn(patient);
        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "PATIENT")
    void createPatient_AsPatient_ShouldReturn403() throws Exception {
        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPatientDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllPatients_WithoutAuth_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isUnauthorized());
    }
}
