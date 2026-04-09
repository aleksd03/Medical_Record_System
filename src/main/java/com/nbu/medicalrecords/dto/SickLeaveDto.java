package com.nbu.medicalrecords.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SickLeaveDto {
    private Long id;
    private LocalDate startDate;
    private int numberOfDays;
    private AppointmentDto appointment;
}
