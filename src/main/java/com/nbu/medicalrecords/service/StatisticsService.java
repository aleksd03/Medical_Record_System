package com.nbu.medicalrecords.service;

import com.nbu.medicalrecords.data.entity.Appointment;
import com.nbu.medicalrecords.data.entity.Doctor;
import com.nbu.medicalrecords.data.entity.Patient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StatisticsService {
    List<Patient> getPatientsByDiagnosis(Long diagnosisId);
    String getMostCommonDiagnosis();
    List<Patient> getPatientsByGeneralPractitioner(Long doctorId);
    BigDecimal getTotalPriceForUninsuredPatients();
    Map<Doctor, BigDecimal> getTotalPriceByDoctorForUninsuredPatients();
    Map<Doctor, Long> countAppointmentsByDoctor();
    Map<Doctor, Long> countPatientsPerGeneralPractitioner();
    List<Appointment> getAppointmentHistoryForPatient(Long patientId);
    List<Appointment> getAppointmentsByDoctorAndPeriod(Long doctorId, LocalDate start, LocalDate end);
    String getMonthWithMostSickLeaves();
    List<Doctor> getDoctorsWithMostSickLeaves();
}