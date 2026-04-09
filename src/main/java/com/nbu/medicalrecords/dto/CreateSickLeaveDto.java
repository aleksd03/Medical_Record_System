package com.nbu.medicalrecords.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateSickLeaveDto {
    private LocalDate startDate;
    private int numberOfDays;
    private Long appointmentId;
}
