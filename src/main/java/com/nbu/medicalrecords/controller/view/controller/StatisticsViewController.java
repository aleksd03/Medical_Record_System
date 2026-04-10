package com.nbu.medicalrecords.controller.view.controller;

import com.nbu.medicalrecords.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsViewController {
    private final StatisticsService statisticsService;

    @GetMapping
    public String index() {
        return "statistics/statistics";
    }

    @GetMapping("/patients-by-diagnosis/{diagnosisId}")
    public String getPatientsByDiagnosis(@PathVariable Long diagnosisId, Model model) {
        model.addAttribute("patients", statisticsService.getPatientsByDiagnosis(diagnosisId));
        return "statistics/patients-by-diagnosis";
    }

    @GetMapping("/most-common-diagnosis")
    public String getMostCommonDiagnosis(Model model) {
        model.addAttribute("diagnosis", statisticsService.getMostCommonDiagnosis());
        return "statistics/most-common-diagnosis";
    }

    @GetMapping("/patients-by-gp/{doctorId}")
    public String getPatientsByGP(@PathVariable Long doctorId, Model model) {
        model.addAttribute("patients", statisticsService.getPatientsByGeneralPractitioner(doctorId));
        return "statistics/patients-by-gp";
    }

    @GetMapping("/total-price-uninsured")
    public String getTotalPriceUninsured(Model model) {
        model.addAttribute("total", statisticsService.getTotalPriceForUninsuredPatients());
        return "statistics/total-price-uninsured";
    }

    @GetMapping("/total-price-by-doctor")
    public String getTotalPriceByDoctor(Model model) {
        model.addAttribute("data", statisticsService.getTotalPriceByDoctorForUninsuredPatients());
        return "statistics/total-price-by-doctor";
    }

    @GetMapping("/appointments-count-by-doctor")
    public String getAppointmentsCountByDoctor(Model model) {
        model.addAttribute("data", statisticsService.countAppointmentsByDoctor());
        return "statistics/appointments-count-by-doctor";
    }

    @GetMapping("/patients-count-by-gp")
    public String getPatientsCountByGP(Model model) {
        model.addAttribute("data", statisticsService.countPatientsPerGeneralPractitioner());
        return "statistics/patients-count-by-gp";
    }

    @GetMapping("/appointment-history/{patientId}")
    public String getAppointmentHistory(@PathVariable Long patientId, Model model) {
        model.addAttribute("appointments", statisticsService.getAppointmentHistoryForPatient(patientId));
        return "statistics/appointment-history";
    }

    @GetMapping("/appointments-by-doctor/{doctorId}")
    public String getAppointmentsByDoctor(
            @PathVariable Long doctorId,
            @RequestParam(required = false) LocalDate start,
            @RequestParam(required = false) LocalDate end,
            Model model) {
        model.addAttribute("appointments", statisticsService.getAppointmentsByDoctorAndPeriod(doctorId, start, end));
        return "statistics/appointments-by-doctor";
    }

    @GetMapping("/month-most-sick-leaves")
    public String getMonthWithMostSickLeaves(Model model) {
        model.addAttribute("month", statisticsService.getMonthWithMostSickLeaves());
        return "statistics/month-most-sick-leaves";
    }

    @GetMapping("/doctors-most-sick-leaves")
    public String getDoctorsWithMostSickLeaves(Model model) {
        model.addAttribute("doctors", statisticsService.getDoctorsWithMostSickLeaves());
        return "statistics/doctors-most-sick-leaves";
    }
}
