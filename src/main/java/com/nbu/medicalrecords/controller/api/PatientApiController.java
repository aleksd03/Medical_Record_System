package com.nbu.medicalrecords.controller.api;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.data.entity.Patient;
import com.nbu.medicalrecords.dto.CreatePatientDto;
import com.nbu.medicalrecords.dto.PatientDto;
import com.nbu.medicalrecords.service.DoctorService;
import com.nbu.medicalrecords.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientApiController {
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public List<PatientDto> getAllPatients() {
        return modelMapperConfig.mapList(patientService.getAllPatients(), PatientDto.class);
    }

    @GetMapping("/{id}")
    public PatientDto getPatientById(@PathVariable Long id) {
        return modelMapperConfig.modelMapper().map(patientService.getPatientById(id), PatientDto.class);
    }

    @PostMapping
    public PatientDto createPatient(@Valid @RequestBody CreatePatientDto createPatientDto) {
        Patient patient = new Patient();
        patient.setFirstName(createPatientDto.getFirstName());
        patient.setLastName(createPatientDto.getLastName());
        patient.setEgn(createPatientDto.getEgn());
        patient.setHealthInsured(createPatientDto.isHealthInsured());
        patient.setGeneralPractitioner(doctorService.getDoctorById(createPatientDto.getGeneralPractitionerId()));
        return modelMapperConfig.modelMapper().map(patientService.createPatient(patient), PatientDto.class);
    }

    @PutMapping("/{id}")
    public PatientDto updatePatient(@PathVariable Long id, @Valid @RequestBody CreatePatientDto createPatientDto) {
        Patient patient = new Patient();
        patient.setFirstName(createPatientDto.getFirstName());
        patient.setLastName(createPatientDto.getLastName());
        patient.setEgn(createPatientDto.getEgn());
        patient.setHealthInsured(createPatientDto.isHealthInsured());
        patient.setGeneralPractitioner(doctorService.getDoctorById(createPatientDto.getGeneralPractitionerId()));
        return modelMapperConfig.modelMapper().map(patientService.updatePatient(id, patient), PatientDto.class);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
}
