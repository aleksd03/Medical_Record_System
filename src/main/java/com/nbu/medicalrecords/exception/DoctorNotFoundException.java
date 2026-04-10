package com.nbu.medicalrecords.exception;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(Long id) {
        super("Doctor with id " + id + " not found");
    }
}
