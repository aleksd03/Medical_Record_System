package com.nbu.medicalrecords.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDoctorDto {
    private String identificationNumber;
    private String firstName;
    private String lastName;
}
