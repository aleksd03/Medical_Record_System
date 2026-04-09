package com.nbu.medicalrecords.controller.view.controller;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.dto.DoctorDto;
import com.nbu.medicalrecords.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorViewController {

    private final DoctorService doctorService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public String getAllDoctors(Model model) {
        List<DoctorDto> doctors = modelMapperConfig.mapList(doctorService.getAllDoctors(), DoctorDto.class);
        model.addAttribute("doctors", doctors);
        return "doctors/doctors";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("doctor", new DoctorDto());
        return "doctors/create-doctor";
    }

    @PostMapping("/create")
    public String createDoctor(@ModelAttribute DoctorDto doctorDto) {
        doctorService.createDoctor(
                modelMapperConfig.modelMapper().map(doctorDto, com.nbu.medicalrecords.data.entity.Doctor.class));
        return "redirect:/doctors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", modelMapperConfig.modelMapper()
                .map(doctorService.getDoctorById(id), DoctorDto.class));
        return "doctors/edit-doctor";
    }

    @PostMapping("/edit/{id}")
    public String updateDoctor(@PathVariable Long id, @ModelAttribute DoctorDto doctorDto) {
        doctorService.updateDoctor(id,
                modelMapperConfig.modelMapper().map(doctorDto, com.nbu.medicalrecords.data.entity.Doctor.class));
        return "redirect:/doctors";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}
