package com.nbu.medicalrecords.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiagnosisDto {
    private Long id;
    private String name;
    private String description;
}
