package com.nbu.medicalrecords.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDoctorDto {
    @NotBlank(message = "Идентификационният номер е задължителен")
    private String identificationNumber;

    @NotBlank(message = "Името е задължително")
    @Size(min = 2, max = 20, message = "Името трябва да е между 2 и 50 символа")
    private String firstName;

    @NotBlank(message = "Фамилията е задължителна")
    @Size(min = 2, max = 20, message = "Фамилията трябва да е между 2 и 50 символа")
    private String lastName;
}
