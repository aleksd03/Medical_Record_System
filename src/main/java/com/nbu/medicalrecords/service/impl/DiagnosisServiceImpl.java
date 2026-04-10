package com.nbu.medicalrecords.service.impl;

import com.nbu.medicalrecords.data.entity.Diagnosis;
import com.nbu.medicalrecords.data.repository.DiagnosisRepository;
import com.nbu.medicalrecords.exception.DiagnosisNotFoundException;
import com.nbu.medicalrecords.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;

    @Override
    public List<Diagnosis> getAllDiagnoses() {
        return diagnosisRepository.findAll();
    }

    @Override
    public Diagnosis getDiagnosisById(Long id) {
        return diagnosisRepository.findById(id).orElseThrow(() -> new DiagnosisNotFoundException(id));
    }

    @Override
    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    @Override
    public Diagnosis updateDiagnosis(Long id, Diagnosis diagnosis) {
        Diagnosis existing = getDiagnosisById(id);
        existing.setName(diagnosis.getName());
        existing.setDescription(diagnosis.getDescription());
        return diagnosisRepository.save(existing);
    }

    @Override
    public void deleteDiagnosis(Long id) {
        diagnosisRepository.deleteById(id);
    }
}
