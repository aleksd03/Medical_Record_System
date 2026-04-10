package com.nbu.medicalrecords.service.impl;

import com.nbu.medicalrecords.data.entity.SickLeave;
import com.nbu.medicalrecords.data.repository.SickLeaveRepository;
import com.nbu.medicalrecords.exception.SickLeaveNotFoundException;
import com.nbu.medicalrecords.service.SickLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SickLeaveServiceImpl implements SickLeaveService {
    private final SickLeaveRepository sickLeaveRepository;

    @Override
    public List<SickLeave> getAllSickLeaves() {
        return sickLeaveRepository.findAll();
    }

    @Override
    public SickLeave getSickLeaveById(Long id) {
        return sickLeaveRepository.findById(id).orElseThrow(() -> new SickLeaveNotFoundException(id));
    }

    @Override
    public SickLeave createSickLeave(SickLeave sickLeave) {
        return sickLeaveRepository.save(sickLeave);
    }

    @Override
    public SickLeave updateSickLeave(Long id, SickLeave sickLeave) {
        SickLeave existing = getSickLeaveById(id);
        existing.setStartDate(sickLeave.getStartDate());
        existing.setNumberOfDays(sickLeave.getNumberOfDays());
        existing.setAppointment(sickLeave.getAppointment());
        return sickLeaveRepository.save(existing);
    }

    @Override
    public void deleteSickLeave(Long id) {
        sickLeaveRepository.deleteById(id);
    }
}
