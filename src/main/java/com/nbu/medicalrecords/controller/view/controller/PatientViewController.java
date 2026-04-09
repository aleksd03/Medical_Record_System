package com.nbu.medicalrecords.controller.view.controller;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.dto.PatientDto;
import com.nbu.medicalrecords.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientViewController {
    private final PatientService patientService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public String getAllPatients(Model model) {
        List<PatientDto> patients = modelMapperConfig
                .mapList(patientService.getAllPatients(), PatientDto.class);
        model.addAttribute("patients", patients);
        return "patients/patients";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("patient", new PatientDto());
        return "patients/create-patient";
    }

    @PostMapping("/create")
    public String createPatient(@ModelAttribute PatientDto patientDto) {
        patientService.createPatient(modelMapperConfig.modelMapper()
                .map(patientDto, com.nbu.medicalrecords.data.entity.Patient.class));
        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", modelMapperConfig.modelMapper()
                .map(patientService.getPatientById(id), PatientDto.class));
        return "patients/edit-patient";
    }

    @PostMapping("/edit/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute PatientDto patientDto) {
        patientService.updatePatient(id, modelMapperConfig.modelMapper()
                .map(patientDto, com.nbu.medicalrecords.data.entity.Patient.class));
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }
}
