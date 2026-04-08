package com.nbu.medicalrecords.service;

import com.nbu.medicalrecords.data.entity.SickLeave;

import java.util.List;

public interface SickLeaveService {
    List<SickLeave> getAllSickLeaves();
    SickLeave getSickLeaveById(Long id);
    SickLeave createSickLeave(SickLeave sickLeave);
    SickLeave updateSickLeave(Long id, SickLeave sickLeave);
    void deleteSickLeave(Long id);
}
