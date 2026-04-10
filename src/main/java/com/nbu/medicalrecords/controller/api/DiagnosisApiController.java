package com.nbu.medicalrecords.controller.api;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.dto.CreateDiagnosisDto;
import com.nbu.medicalrecords.dto.DiagnosisDto;
import com.nbu.medicalrecords.service.DiagnosisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnoses")
@RequiredArgsConstructor
public class DiagnosisApiController {
    private final DiagnosisService diagnosisService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public List<DiagnosisDto> getAllDiagnoses() {
        return modelMapperConfig.mapList(diagnosisService.getAllDiagnoses(), DiagnosisDto.class);
    }

    @GetMapping("/{id}")
    public DiagnosisDto getDiagnosisById(@PathVariable Long id) {
        return modelMapperConfig.modelMapper().map(diagnosisService.getDiagnosisById(id), DiagnosisDto.class);
    }

    @PostMapping
    public DiagnosisDto createDiagnosis(@Valid @RequestBody CreateDiagnosisDto createDiagnosisDto) {
        return modelMapperConfig.modelMapper().map(
                diagnosisService.createDiagnosis(modelMapperConfig.modelMapper()
                        .map(createDiagnosisDto, com.nbu.medicalrecords.data.entity.Diagnosis.class)),
                DiagnosisDto.class);
    }

    @PutMapping("/{id}")
    public DiagnosisDto updateDiagnosis(@PathVariable Long id, @Valid @RequestBody DiagnosisDto diagnosisDto) {
        return modelMapperConfig.modelMapper().map(
                diagnosisService.updateDiagnosis(id, modelMapperConfig.modelMapper()
                        .map(diagnosisDto, com.nbu.medicalrecords.data.entity.Diagnosis.class)),
                DiagnosisDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteDiagnosis(@PathVariable Long id) {
        diagnosisService.deleteDiagnosis(id);
    }
}
