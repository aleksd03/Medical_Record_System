package com.nbu.medicalrecords.exception;

public class DiagnosisNotFoundException extends RuntimeException {
    public DiagnosisNotFoundException(Long id) {
        super("Diagnosis with id " + id + " not found");
    }
}
