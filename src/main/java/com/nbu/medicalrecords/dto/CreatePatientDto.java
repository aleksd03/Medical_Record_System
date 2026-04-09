package com.nbu.medicalrecords.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePatientDto {
    private String firstName;
    private String lastName;
    private String egn;
}
