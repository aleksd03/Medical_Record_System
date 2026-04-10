package com.nbu.medicalrecords.controller.api;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.dto.CreatePatientDto;
import com.nbu.medicalrecords.dto.PatientDto;
import com.nbu.medicalrecords.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientApiController {
    private final PatientService patientService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public List<PatientDto> getAllPatients() {
        return modelMapperConfig.mapList(patientService.getAllPatients(), PatientDto.class);
    }

    @GetMapping("/{id}")
    public PatientDto getPatientById(@PathVariable Long id) {
        return modelMapperConfig.modelMapper().map(patientService.getPatientById(id), PatientDto.class);
    }

    @PostMapping
    public PatientDto createPatient(@Valid @RequestBody CreatePatientDto createPatientDto) {
        return modelMapperConfig.modelMapper().map(
                patientService.createPatient(modelMapperConfig.modelMapper()
                        .map(createPatientDto, com.nbu.medicalrecords.data.entity.Patient.class)),
                PatientDto.class);
    }

    @PutMapping("/{id}")
    public PatientDto updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDto patientDto) {
        return modelMapperConfig.modelMapper().map(
                patientService.updatePatient(id, modelMapperConfig.modelMapper()
                        .map(patientDto, com.nbu.medicalrecords.data.entity.Patient.class)),
                PatientDto.class);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
}
