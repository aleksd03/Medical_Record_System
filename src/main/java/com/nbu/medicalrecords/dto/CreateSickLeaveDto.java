package com.nbu.medicalrecords.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateSickLeaveDto {
    @NotNull(message = "Началната дата е задължителна")
    private LocalDate startDate;

    @Min(value = 1, message = "Броят дни трябва да е поне 1")
    private int numberOfDays;

    @NotNull(message = "Прегледът е задължителен")
    private Long appointmentId;
}
