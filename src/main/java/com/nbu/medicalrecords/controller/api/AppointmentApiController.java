package com.nbu.medicalrecords.controller.api;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.data.entity.User;
import com.nbu.medicalrecords.dto.AppointmentDto;
import com.nbu.medicalrecords.dto.CreateAppointmentDto;
import com.nbu.medicalrecords.service.*;
import com.nbu.medicalrecords.data.entity.Appointment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentApiController {
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final DiagnosisService diagnosisService;
    private final UserService userService;
    private final StatisticsService statisticsService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public List<AppointmentDto> getAllAppointments() {
        return modelMapperConfig.mapList(appointmentService.getAllAppointments(), AppointmentDto.class);
    }

    @GetMapping("/{id}")
    public AppointmentDto getAppointmentById(@PathVariable Long id) {
        return modelMapperConfig.modelMapper()
                .map(appointmentService.getAppointmentById(id), AppointmentDto.class);
    }

    @GetMapping("/my-appointments")
    public List<AppointmentDto> getMyAppointments() {
        User currentUser = userService.getCurrentUser();
        if (currentUser.getPatient() == null) {
            throw new RuntimeException("You are not a patient!");
        }
        return modelMapperConfig.mapList(
                statisticsService.getAppointmentHistoryForPatient(currentUser.getPatient().getId()),
                AppointmentDto.class);
    }

    @PostMapping
    public AppointmentDto createAppointment(@Valid @RequestBody CreateAppointmentDto createAppointmentDto) {
        Appointment appointment = new Appointment();
        appointment.setDate(createAppointmentDto.getDate());
        appointment.setTreatment(createAppointmentDto.getTreatment());
        appointment.setPrice(createAppointmentDto.getPrice());
        appointment.setDoctor(doctorService.getDoctorById(createAppointmentDto.getDoctorId()));
        appointment.setPatient(patientService.getPatientById(createAppointmentDto.getPatientId()));
        appointment.setDiagnosis(diagnosisService.getDiagnosisById(createAppointmentDto.getDiagnosisId()));

        return modelMapperConfig.modelMapper()
                .map(appointmentService.createAppointment(appointment), AppointmentDto.class);
    }

    @PutMapping("/{id}")
    public AppointmentDto updateAppointment(@PathVariable Long id, @RequestBody CreateAppointmentDto dto) {
        User currentUser = userService.getCurrentUser();
        Appointment existing = appointmentService.getAppointmentById(id);

        if (currentUser.getDoctor() != null &&
                !existing.getDoctor().getId().equals(currentUser.getDoctor().getId())) {
            throw new RuntimeException("You can only edit your own appointments!");
        }

        existing.setDate(dto.getDate());
        existing.setTreatment(dto.getTreatment());
        existing.setPrice(dto.getPrice());
        existing.setDoctor(doctorService.getDoctorById(dto.getDoctorId()));
        existing.setPatient(patientService.getPatientById(dto.getPatientId()));
        existing.setDiagnosis(diagnosisService.getDiagnosisById(dto.getDiagnosisId()));
        return modelMapperConfig.modelMapper().map(appointmentService.updateAppointment(id, existing), AppointmentDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();
        Appointment existing = appointmentService.getAppointmentById(id);

        if (currentUser.getDoctor() != null &&
                !existing.getDoctor().getId().equals(currentUser.getDoctor().getId())) {
            throw new RuntimeException("You can only delete your own appointments!");
        }

        appointmentService.deleteAppointment(id);
    }
}
