package com.nbu.medicalrecords.controller.view.controller;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.data.entity.SickLeave;
import com.nbu.medicalrecords.dto.CreateSickLeaveDto;
import com.nbu.medicalrecords.dto.SickLeaveDto;
import com.nbu.medicalrecords.service.AppointmentService;
import com.nbu.medicalrecords.service.SickLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sick-leaves")
@RequiredArgsConstructor
public class SickLeaveViewController {
    private final SickLeaveService sickLeaveService;
    private final AppointmentService appointmentService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public String getAllSickLeaves(Model model) {
        List<SickLeaveDto> sickLeaves = modelMapperConfig
                .mapList(sickLeaveService.getAllSickLeaves(), SickLeaveDto.class);
        model.addAttribute("sickLeaves", sickLeaves);
        return "sickLeaves/sickLeaves";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("sickLeave", new CreateSickLeaveDto());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "sickLeaves/create-sickLeave";
    }

    @PostMapping("/create")
    public String createSickLeave(@ModelAttribute CreateSickLeaveDto dto) {
        SickLeave sickLeave = new SickLeave();

        sickLeave.setStartDate(dto.getStartDate());
        sickLeave.setNumberOfDays(dto.getNumberOfDays());
        sickLeave.setAppointment(appointmentService.getAppointmentById(dto.getAppointmentId()));

        sickLeaveService.createSickLeave(sickLeave);

        return "redirect:/sick-leaves";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        SickLeave sickLeave = sickLeaveService.getSickLeaveById(id);
        CreateSickLeaveDto dto = new CreateSickLeaveDto();

        dto.setStartDate(sickLeave.getStartDate());
        dto.setNumberOfDays(sickLeave.getNumberOfDays());
        dto.setAppointmentId(sickLeave.getAppointment().getId());

        model.addAttribute("sickLeave", dto);
        model.addAttribute("sickLeaveId", id);
        model.addAttribute("appointments", appointmentService.getAllAppointments());

        return "sickLeaves/edit-sickLeave";
    }

    @PostMapping("/edit/{id}")
    public String updateSickLeave(@PathVariable Long id, @ModelAttribute CreateSickLeaveDto dto) {
        SickLeave sickLeave = sickLeaveService.getSickLeaveById(id);

        sickLeave.setStartDate(dto.getStartDate());
        sickLeave.setNumberOfDays(dto.getNumberOfDays());
        sickLeave.setAppointment(appointmentService.getAppointmentById(dto.getAppointmentId()));

        sickLeaveService.updateSickLeave(id, sickLeave);

        return "redirect:/sick-leaves";
    }

    @GetMapping("/delete/{id}")
    public String deleteSickLeave(@PathVariable Long id) {
        sickLeaveService.deleteSickLeave(id);
        return "redirect:/sick-leaves";
    }
}
