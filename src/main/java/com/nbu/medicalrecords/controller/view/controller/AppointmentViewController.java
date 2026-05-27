package com.nbu.medicalrecords.controller.view.controller;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.data.entity.Appointment;
import com.nbu.medicalrecords.data.entity.User;
import com.nbu.medicalrecords.dto.AppointmentDto;
import com.nbu.medicalrecords.dto.CreateAppointmentDto;
import com.nbu.medicalrecords.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentViewController {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final DiagnosisService diagnosisService;
    private final UserService userService;
    private final StatisticsService statisticsService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public String getAllAppointments(Model model) {
        List<AppointmentDto> appointments = modelMapperConfig
                .mapList(appointmentService.getAllAppointments(), AppointmentDto.class);
        model.addAttribute("appointments", appointments);
        return "appointments/appointments";
    }

    @GetMapping("/my-appointments")
    public String getMyAppointments(Model model) {
        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser.getPatient() == null) {
                return "redirect:/";
            }
            List<AppointmentDto> appointments = modelMapperConfig
                    .mapList(statisticsService.getAppointmentHistoryForPatient(
                            currentUser.getPatient().getId()), AppointmentDto.class);
            model.addAttribute("appointments", appointments);
            return "appointments/my-appointments";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("appointment", new CreateAppointmentDto());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());

        return "appointments/create-appointment";
    }

    @PostMapping("/create")
    public String createAppointment(@ModelAttribute CreateAppointmentDto dto) {
        Appointment appointment = new Appointment();

        appointment.setDate(dto.getDate());
        appointment.setTreatment(dto.getTreatment());
        appointment.setPrice(dto.getPrice());
        appointment.setDoctor(doctorService.getDoctorById(dto.getDoctorId()));
        appointment.setPatient(patientService.getPatientById(dto.getPatientId()));
        appointment.setDiagnosis(diagnosisService.getDiagnosisById(dto.getDiagnosisId()));

        appointmentService.createAppointment(appointment);

        return "redirect:/appointments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User currentUser = userService.getCurrentUser();
        Appointment appointment = appointmentService.getAppointmentById(id);

        if (currentUser.getDoctor() != null &&
                !appointment.getDoctor().getId().equals(currentUser.getDoctor().getId())) {
            return "redirect:/appointments";
        }

        CreateAppointmentDto dto = new CreateAppointmentDto();
        dto.setDate(appointment.getDate());
        dto.setTreatment(appointment.getTreatment());
        dto.setPrice(appointment.getPrice());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setDiagnosisId(appointment.getDiagnosis().getId());

        model.addAttribute("appointment", dto);
        model.addAttribute("appointmentId", id);
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());

        return "appointments/edit-appointment";
    }

    @PostMapping("/edit/{id}")
    public String updateAppointment(@PathVariable Long id, @ModelAttribute CreateAppointmentDto dto) {
        User currentUser = userService.getCurrentUser();
        Appointment appointment = appointmentService.getAppointmentById(id);

        if (currentUser.getDoctor() != null &&
                !appointment.getDoctor().getId().equals(currentUser.getDoctor().getId())) {
            return "redirect:/appointments";
        }

        appointment.setDate(dto.getDate());
        appointment.setTreatment(dto.getTreatment());
        appointment.setPrice(dto.getPrice());
        appointment.setDoctor(doctorService.getDoctorById(dto.getDoctorId()));
        appointment.setPatient(patientService.getPatientById(dto.getPatientId()));
        appointment.setDiagnosis(diagnosisService.getDiagnosisById(dto.getDiagnosisId()));

        appointmentService.updateAppointment(id, appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();
        Appointment appointment = appointmentService.getAppointmentById(id);

        if (currentUser.getDoctor() != null &&
                !appointment.getDoctor().getId().equals(currentUser.getDoctor().getId())) {
            return "redirect:/appointments";
        }

        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
}
