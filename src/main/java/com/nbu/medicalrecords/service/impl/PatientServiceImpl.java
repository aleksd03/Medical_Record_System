package com.nbu.medicalrecords.service.impl;

import com.nbu.medicalrecords.data.entity.Patient;
import com.nbu.medicalrecords.data.repository.PatientRepository;
import com.nbu.medicalrecords.exception.PatientNotFoundException;
import com.nbu.medicalrecords.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, Patient patient) {
        Patient existing = getPatientById(id);
        existing.setFirstName(patient.getFirstName());
        existing.setLastName(patient.getLastName());
        existing.setEgn(patient.getEgn());
        existing.setHealthInsured(patient.isHealthInsured());
        existing.setGeneralPractitioner(patient.getGeneralPractitioner());
        return patientRepository.save(existing);
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
