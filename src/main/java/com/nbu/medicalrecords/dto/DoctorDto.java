package com.nbu.medicalrecords.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDto {
    private Long id;
    private String identificationNumber;
    private String firstName;
    private String lastName;
}
