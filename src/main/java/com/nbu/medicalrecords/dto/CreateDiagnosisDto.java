package com.nbu.medicalrecords.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDiagnosisDto {
    @NotBlank(message = "Името на диагнозата е задължително")
    @Size(min = 2, max = 50, message = "Името трябва да е между 2 и 50 символа")
    private String name;

    @Size(max = 255, message = "Описанието не може да е повече от 255 символа")
    private String description;
}
