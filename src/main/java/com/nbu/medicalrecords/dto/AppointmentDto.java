package com.nbu.medicalrecords.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class AppointmentDto {
    private Long id;
    private LocalDate date;
    private DoctorDto doctor;
    private PatientDto patient;
    private DiagnosisDto diagnosis;
    private String treatment;
    private BigDecimal price;
}
