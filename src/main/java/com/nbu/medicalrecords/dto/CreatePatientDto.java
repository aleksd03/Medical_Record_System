package com.nbu.medicalrecords.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePatientDto {
    @NotBlank(message = "Името е задължително")
    @Size(min = 2, max = 20, message = "Името трябва да е между 2 и 20 символа")
    private String firstName;

    @NotBlank(message = "Фамилията е задължителна")
    @Size(min = 2, max = 20, message = "Фамилията трябва да е между 2 и 20 символа")
    private String lastName;

    @NotBlank(message = "ЕГН-то е задължително")
    @Pattern(regexp = "\\d{10}", message = "ЕГН-то трябва да съдържа точно 10 цифри")
    private String egn;
}
