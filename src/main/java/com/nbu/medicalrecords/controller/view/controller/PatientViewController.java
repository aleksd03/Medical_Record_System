package com.nbu.medicalrecords.controller.view.controller;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.data.entity.Appointment;
import com.nbu.medicalrecords.data.entity.User;
import com.nbu.medicalrecords.dto.PatientDto;
import com.nbu.medicalrecords.service.AppointmentService;
import com.nbu.medicalrecords.service.PatientService;
import com.nbu.medicalrecords.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientViewController {
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final UserService userService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public String getAllPatients(Model model) {
        User currentUser = userService.getCurrentUser();
        List<PatientDto> patients;

        if (currentUser.getDoctor() != null) {
            List<Appointment> doctorAppointments = appointmentService
                    .getAppointmentsByDoctor(currentUser.getDoctor());
            patients = doctorAppointments.stream()
                    .map(Appointment::getPatient)
                    .distinct()
                    .map(p -> modelMapperConfig.modelMapper().map(p, PatientDto.class))
                    .collect(Collectors.toList());
        } else {
            patients = modelMapperConfig.mapList(patientService.getAllPatients(), PatientDto.class);
        }

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
        User currentUser = userService.getCurrentUser();

        if (currentUser.getDoctor() != null) {
            List<Appointment> doctorAppointments = appointmentService
                    .getAppointmentsByDoctor(currentUser.getDoctor());
            boolean hasAccess = doctorAppointments.stream()
                    .anyMatch(a -> a.getPatient().getId().equals(id));
            if (!hasAccess) {
                return "redirect:/patients";
            }
        }

        model.addAttribute("patient", modelMapperConfig.modelMapper()
                .map(patientService.getPatientById(id), PatientDto.class));
        return "patients/edit-patient";
    }

    @PostMapping("/edit/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute PatientDto patientDto) {
        User currentUser = userService.getCurrentUser();

        if (currentUser.getDoctor() != null) {
            List<Appointment> doctorAppointments = appointmentService
                    .getAppointmentsByDoctor(currentUser.getDoctor());
            boolean hasAccess = doctorAppointments.stream()
                    .anyMatch(a -> a.getPatient().getId().equals(id));
            if (!hasAccess) {
                return "redirect:/patients";
            }
        }

        patientService.updatePatient(id, modelMapperConfig.modelMapper()
                .map(patientDto, com.nbu.medicalrecords.data.entity.Patient.class));
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();

        if (currentUser.getDoctor() != null) {
            List<Appointment> doctorAppointments = appointmentService
                    .getAppointmentsByDoctor(currentUser.getDoctor());
            boolean hasAccess = doctorAppointments.stream()
                    .anyMatch(a -> a.getPatient().getId().equals(id));
            if (!hasAccess) {
                return "redirect:/patients";
            }
        }

        patientService.deletePatient(id);
        return "redirect:/patients";
    }
}