package com.nbu.medicalrecords.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.medicalrecords.data.entity.Diagnosis;
import com.nbu.medicalrecords.dto.CreateDiagnosisDto;
import com.nbu.medicalrecords.service.DiagnosisService;
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
public class DiagnosisApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DiagnosisService diagnosisService;

    private Diagnosis diagnosis;
    private CreateDiagnosisDto createDiagnosisDto;

    @BeforeEach
    void setUp() {
        diagnosis = new Diagnosis();
        diagnosis.setId(1L);
        diagnosis.setName("Grip");
        diagnosis.setDescription("Virusna infectsia");

        createDiagnosisDto = new CreateDiagnosisDto();
        createDiagnosisDto.setName("Grip");
        createDiagnosisDto.setDescription("Virusna infectsia");
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getAllDiagnoses_ShouldReturn200() throws Exception {
        when(diagnosisService.getAllDiagnoses()).thenReturn(List.of(diagnosis));
        mockMvc.perform(get("/api/diagnoses"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getDiagnosisById_WhenExists_ShouldReturn200() throws Exception {
        when(diagnosisService.getDiagnosisById(1L)).thenReturn(diagnosis);
        mockMvc.perform(get("/api/diagnoses/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "PATIENT")
    void createDiagnosis_AsPatient_ShouldReturn403() throws Exception {
        mockMvc.perform(post("/api/diagnoses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDiagnosisDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllDiagnoses_WithoutAuth_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/diagnoses"))
                .andExpect(status().isUnauthorized());
    }
}
