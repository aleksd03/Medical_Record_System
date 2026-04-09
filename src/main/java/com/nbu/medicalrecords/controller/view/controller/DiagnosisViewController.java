package com.nbu.medicalrecords.controller.view.controller;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.dto.DiagnosisDto;
import com.nbu.medicalrecords.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/diagnoses")
@RequiredArgsConstructor
public class DiagnosisViewController {
    private final DiagnosisService diagnosisService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public String getAllDiagnoses(Model model) {
        List<DiagnosisDto> diagnoses = modelMapperConfig
                .mapList(diagnosisService.getAllDiagnoses(), DiagnosisDto.class);
        model.addAttribute("diagnoses", diagnoses);
        return "diagnoses/diagnoses";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("diagnosis", new DiagnosisDto());
        return "diagnoses/create-diagnosis";
    }

    @PostMapping("/create")
    public String createDiagnosis(@ModelAttribute DiagnosisDto diagnosisDto) {
        diagnosisService.createDiagnosis(modelMapperConfig.modelMapper()
                .map(diagnosisDto, com.nbu.medicalrecords.data.entity.Diagnosis.class));
        return "redirect:/diagnoses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("diagnosis", modelMapperConfig.modelMapper()
                .map(diagnosisService.getDiagnosisById(id), DiagnosisDto.class));
        return "diagnoses/edit-diagnosis";
    }

    @PostMapping("/edit/{id}")
    public String updateDiagnosis(@PathVariable Long id, @ModelAttribute DiagnosisDto diagnosisDto) {
        diagnosisService.updateDiagnosis(id, modelMapperConfig.modelMapper()
                .map(diagnosisDto, com.nbu.medicalrecords.data.entity.Diagnosis.class));
        return "redirect:/diagnoses";
    }

    @GetMapping("/delete/{id}")
    public String deleteDiagnosis(@PathVariable Long id) {
        diagnosisService.deleteDiagnosis(id);
        return "redirect:/diagnoses";
    }
}
