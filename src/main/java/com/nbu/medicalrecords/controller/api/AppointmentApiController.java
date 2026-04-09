package com.nbu.medicalrecords.controller.api;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.dto.AppointmentDto;
import com.nbu.medicalrecords.dto.CreateAppointmentDto;
import com.nbu.medicalrecords.service.AppointmentService;
import com.nbu.medicalrecords.service.DoctorService;
import com.nbu.medicalrecords.service.PatientService;
import com.nbu.medicalrecords.service.DiagnosisService;
import com.nbu.medicalrecords.data.entity.Appointment;
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

    @PostMapping
    public AppointmentDto createAppointment(@RequestBody CreateAppointmentDto createAppointmentDto) {
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
        Appointment appointment = appointmentService.getAppointmentById(id);
        appointment.setDate(dto.getDate());
        appointment.setTreatment(dto.getTreatment());
        appointment.setPrice(dto.getPrice());
        appointment.setDoctor(doctorService.getDoctorById(dto.getDoctorId()));
        appointment.setPatient(patientService.getPatientById(dto.getPatientId()));
        appointment.setDiagnosis(diagnosisService.getDiagnosisById(dto.getDiagnosisId()));

        return modelMapperConfig.modelMapper()
                .map(appointmentService.updateAppointment(id, appointment), AppointmentDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }
}
