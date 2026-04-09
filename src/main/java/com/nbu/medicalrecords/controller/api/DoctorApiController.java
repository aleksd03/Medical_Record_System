package com.nbu.medicalrecords.controller.api;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.dto.CreateDoctorDto;
import com.nbu.medicalrecords.dto.DoctorDto;
import com.nbu.medicalrecords.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorApiController {
    private final DoctorService doctorService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public List<DoctorDto> getAllDoctors() {
        return modelMapperConfig.mapList(doctorService.getAllDoctors(), DoctorDto.class);
    }

    @GetMapping("/{id}")
    public DoctorDto getDoctorById(@PathVariable Long id) {
        return modelMapperConfig.modelMapper().map(doctorService.getDoctorById(id), DoctorDto.class);
    }

    @PostMapping
    public DoctorDto createDoctor(@RequestBody CreateDoctorDto createDoctorDto) {
        return modelMapperConfig.modelMapper().map(
                doctorService.createDoctor(modelMapperConfig.modelMapper()
                        .map(createDoctorDto, com.nbu.medicalrecords.data.entity.Doctor.class)),
                DoctorDto.class);
    }

    @PutMapping("/{id}")
    public DoctorDto updateDoctor(@PathVariable Long id, @RequestBody DoctorDto doctorDto) {
        return modelMapperConfig.modelMapper().map(
                doctorService.updateDoctor(id, modelMapperConfig.modelMapper()
                        .map(doctorDto, com.nbu.medicalrecords.data.entity.Doctor.class)),
                DoctorDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
}
