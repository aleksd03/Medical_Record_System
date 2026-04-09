package com.nbu.medicalrecords.controller.view.controller;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.data.entity.Appointment;
import com.nbu.medicalrecords.dto.AppointmentDto;
import com.nbu.medicalrecords.dto.CreateAppointmentDto;
import com.nbu.medicalrecords.service.AppointmentService;
import com.nbu.medicalrecords.service.DiagnosisService;
import com.nbu.medicalrecords.service.DoctorService;
import com.nbu.medicalrecords.service.PatientService;
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
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public String getAllAppointments(Model model) {
        List<AppointmentDto> appointments = modelMapperConfig
                .mapList(appointmentService.getAllAppointments(), AppointmentDto.class);
        model.addAttribute("appointments", appointments);
        return "appointments/appointments";
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
        Appointment appointment = appointmentService.getAppointmentById(id);
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
        Appointment appointment = appointmentService.getAppointmentById(id);

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
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
}
