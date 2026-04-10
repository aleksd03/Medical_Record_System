package com.nbu.medicalrecords.controller.api;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.data.entity.Doctor;
import com.nbu.medicalrecords.dto.AppointmentDto;
import com.nbu.medicalrecords.dto.DoctorDto;
import com.nbu.medicalrecords.dto.PatientDto;
import com.nbu.medicalrecords.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsApiController {
    private final StatisticsService statisticsService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping("/patients-by-diagnosis/{diagnosisId}")
    public List<PatientDto> getPatientsByDiagnosis(@PathVariable Long diagnosisId) {
        return modelMapperConfig.mapList(statisticsService.getPatientsByDiagnosis(diagnosisId), PatientDto.class);
    }

    @GetMapping("/most-common-diagnosis")
    public String getMostCommonDiagnosis() {
        return statisticsService.getMostCommonDiagnosis();
    }

    @GetMapping("/patients-by-gp/{doctorId}")
    public List<PatientDto> getPatientsByGeneralPractitioner(@PathVariable Long doctorId) {
        return modelMapperConfig.mapList(statisticsService.getPatientsByGeneralPractitioner(doctorId), PatientDto.class);
    }

    @GetMapping("/total-price-uninsured")
    public BigDecimal getTotalPriceForUninsuredPatients() {
        return statisticsService.getTotalPriceForUninsuredPatients();
    }

    @GetMapping("/total-price-by-doctor-uninsured")
    public Map<String, BigDecimal> getTotalPriceByDoctorForUninsuredPatients() {
        return statisticsService.getTotalPriceByDoctorForUninsuredPatients()
                .entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getFirstName() + " " + e.getKey().getLastName(),
                        Map.Entry::getValue
                ));
    }

    @GetMapping("/appointments-count-by-doctor")
    public Map<String, Long> countAppointmentsByDoctor() {
        return statisticsService.countAppointmentsByDoctor()
                .entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getFirstName() + " " + e.getKey().getLastName(),
                        Map.Entry::getValue
                ));
    }

    @GetMapping("/patients-count-by-gp")
    public Map<String, Long> countPatientsPerGeneralPractitioner() {
        return statisticsService.countPatientsPerGeneralPractitioner()
                .entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getFirstName() + " " + e.getKey().getLastName(),
                        Map.Entry::getValue
                ));
    }

    @GetMapping("/appointment-history/{patientId}")
    public List<AppointmentDto> getAppointmentHistoryForPatient(@PathVariable Long patientId) {
        return modelMapperConfig.mapList(statisticsService.getAppointmentHistoryForPatient(patientId), AppointmentDto.class);
    }

    @GetMapping("/appointments-by-doctor/{doctorId}")
    public List<AppointmentDto> getAppointmentsByDoctorAndPeriod(
            @PathVariable Long doctorId,
            @RequestParam(required = false) LocalDate start,
            @RequestParam(required = false) LocalDate end) {
        return modelMapperConfig.mapList(
                statisticsService.getAppointmentsByDoctorAndPeriod(doctorId, start, end),
                AppointmentDto.class);
    }

    @GetMapping("/month-most-sick-leaves")
    public String getMonthWithMostSickLeaves() {
        return statisticsService.getMonthWithMostSickLeaves();
    }

    @GetMapping("/doctors-most-sick-leaves")
    public List<DoctorDto> getDoctorsWithMostSickLeaves() {
        return modelMapperConfig.mapList(statisticsService.getDoctorsWithMostSickLeaves(), DoctorDto.class);
    }
}
