package com.nbu.medicalrecords.controller;

import com.nbu.medicalrecords.data.entity.SickLeave;
import com.nbu.medicalrecords.service.SickLeaveService;
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
public class SickLeaveApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SickLeaveService sickLeaveService;

    private SickLeave sickLeave;

    @BeforeEach
    void setUp() {
        sickLeave = new SickLeave();
        sickLeave.setId(1L);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getAllSickLeaves_ShouldReturn200() throws Exception {
        when(sickLeaveService.getAllSickLeaves()).thenReturn(List.of(sickLeave));
        mockMvc.perform(get("/api/sick-leaves"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "PATIENT")
    void deleteSickLeave_AsPatient_ShouldReturn403() throws Exception {
        mockMvc.perform(delete("/api/sick-leaves/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllSickLeaves_WithoutAuth_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/sick-leaves"))
                .andExpect(status().isUnauthorized());
    }
}
