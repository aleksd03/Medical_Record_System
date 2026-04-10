package com.nbu.medicalrecords.controller.api;

import com.nbu.medicalrecords.config.ModelMapperConfig;
import com.nbu.medicalrecords.dto.CreateSickLeaveDto;
import com.nbu.medicalrecords.dto.SickLeaveDto;
import com.nbu.medicalrecords.service.AppointmentService;
import com.nbu.medicalrecords.service.SickLeaveService;
import com.nbu.medicalrecords.data.entity.SickLeave;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sick-leaves")
@RequiredArgsConstructor
public class SickLeaveApiController {
    private final SickLeaveService sickLeaveService;
    private final AppointmentService appointmentService;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping
    public List<SickLeaveDto> getAllSickLeaves() {
        return modelMapperConfig.mapList(sickLeaveService.getAllSickLeaves(), SickLeaveDto.class);
    }

    @GetMapping("/{id}")
    public SickLeaveDto getSickLeaveById(@PathVariable Long id) {
        return modelMapperConfig.modelMapper()
                .map(sickLeaveService.getSickLeaveById(id), SickLeaveDto.class);
    }

    @PostMapping
    public SickLeaveDto createSickLeave(@Valid @RequestBody CreateSickLeaveDto createSickLeaveDto) {
        SickLeave sickLeave = new SickLeave();
        sickLeave.setStartDate(createSickLeaveDto.getStartDate());
        sickLeave.setNumberOfDays(createSickLeaveDto.getNumberOfDays());
        sickLeave.setAppointment(appointmentService.getAppointmentById(createSickLeaveDto.getAppointmentId()));

        return modelMapperConfig.modelMapper()
                .map(sickLeaveService.createSickLeave(sickLeave), SickLeaveDto.class);
    }

    @PutMapping("/{id}")
    public SickLeaveDto updateSickLeave(@PathVariable Long id, @Valid @RequestBody CreateSickLeaveDto dto) {
        SickLeave sickLeave = sickLeaveService.getSickLeaveById(id);
        sickLeave.setStartDate(dto.getStartDate());
        sickLeave.setNumberOfDays(dto.getNumberOfDays());
        sickLeave.setAppointment(appointmentService.getAppointmentById(dto.getAppointmentId()));

        return modelMapperConfig.modelMapper()
                .map(sickLeaveService.updateSickLeave(id, sickLeave), SickLeaveDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteSickLeave(@PathVariable Long id) {
        sickLeaveService.deleteSickLeave(id);
    }
}
