package com.nbu.medicalrecords.service.impl;

import com.nbu.medicalrecords.data.entity.Appointment;
import com.nbu.medicalrecords.data.entity.Doctor;
import com.nbu.medicalrecords.data.entity.Patient;
import com.nbu.medicalrecords.data.repository.AppointmentRepository;
import com.nbu.medicalrecords.data.repository.DiagnosisRepository;
import com.nbu.medicalrecords.data.repository.PatientRepository;
import com.nbu.medicalrecords.data.repository.SickLeaveRepository;
import com.nbu.medicalrecords.service.DoctorService;
import com.nbu.medicalrecords.service.PatientService;
import com.nbu.medicalrecords.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final AppointmentRepository appointmentRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final PatientRepository patientRepository;
    private final SickLeaveRepository sickLeaveRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Override
    public List<Patient> getPatientsByDiagnosis(Long diagnosisId) {
        return diagnosisRepository.findPatientsByDiagnosisId(diagnosisId);
    }

    @Override
    public String getMostCommonDiagnosis() {
        List<Object[]> results = diagnosisRepository.findMostCommonDiagnosis();
        if (results.isEmpty()) return "No data";
        Object[] first = results.get(0);
        return ((com.nbu.medicalrecords.data.entity.Diagnosis) first[0]).getName();
    }

    @Override
    public List<Patient> getPatientsByGeneralPractitioner(Long doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        return patientRepository.findByGeneralPractitioner(doctor);
    }

    @Override
    public BigDecimal getTotalPriceForUninsuredPatients() {
        BigDecimal total = appointmentRepository.getTotalPriceForUninsuredPatients();
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public Map<Doctor, BigDecimal> getTotalPriceByDoctorForUninsuredPatients() {
        return appointmentRepository.getTotalPriceByDoctorForUninsuredPatients()
                .stream()
                .collect(Collectors.toMap(
                        row -> (Doctor) row[0],
                        row -> (BigDecimal) row[1]
                ));
    }

    @Override
    public Map<Doctor, Long> countAppointmentsByDoctor() {
        return appointmentRepository.countAppointmentsByDoctor()
                .stream()
                .collect(Collectors.toMap(
                        row -> (Doctor) row[0],
                        row -> (Long) row[1]
                ));
    }

    @Override
    public Map<Doctor, Long> countPatientsPerGeneralPractitioner() {
        return patientRepository.countPatientsPerGeneralPractitioner()
                .stream()
                .collect(Collectors.toMap(
                        row -> (Doctor) row[0],
                        row -> (Long) row[1]
                ));
    }

    @Override
    public List<Appointment> getAppointmentHistoryForPatient(Long patientId) {
        Patient patient = patientService.getPatientById(patientId);
        return appointmentRepository.findByPatient(patient);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorAndPeriod(Long doctorId, LocalDate start, LocalDate end) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (start != null && end != null) {
            return appointmentRepository.findByDoctorAndDateBetween(doctor, start, end);
        }
        return appointmentRepository.findByDoctor(doctor);
    }

    @Override
    public String getMonthWithMostSickLeaves() {
        List<Object[]> results = sickLeaveRepository.findMonthWithMostSickLeaves();
        if (results.isEmpty()) return "No data";
        Object[] first = results.get(0);
        int month = ((Number) first[0]).intValue();
        return java.time.Month.of(month).name();
    }

    @Override
    public List<Doctor> getDoctorsWithMostSickLeaves() {
        return sickLeaveRepository.findDoctorsWithMostSickLeaves()
                .stream()
                .map(row -> (Doctor) row[0])
                .collect(Collectors.toList());
    }
}
