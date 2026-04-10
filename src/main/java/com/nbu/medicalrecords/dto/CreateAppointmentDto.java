package com.nbu.medicalrecords.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreateAppointmentDto {
    @NotNull(message = "Датата е задължителна")
    private LocalDate date;

    @NotNull(message = "Лекарят е задължителен")
    private Long doctorId;

    @NotNull(message = "Пациентът е задължителен")
    private Long patientId;

    @NotNull(message = "Диагнозата е задължителна")
    private Long diagnosisId;

    private String treatment;

    @Positive(message = "Цената трябва да е положително число")
    private BigDecimal price;
}
