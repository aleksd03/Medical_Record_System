package com.nbu.medicalrecords.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDiagnosisDto {
    private String name;
    private String description;
}
