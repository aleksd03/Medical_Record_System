package com.nbu.medicalrecords.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreateAppointmentDto {
    private LocalDate date;
    private Long doctorId;
    private Long patientId;
    private Long diagnosisId;
    private String treatment;
    private BigDecimal price;
}
