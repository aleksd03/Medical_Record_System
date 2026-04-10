package com.nbu.medicalrecords.exception;

public class SickLeaveNotFoundException extends RuntimeException {
    public SickLeaveNotFoundException(Long id) {
        super("Sick leave with id " + id + " not found");
    }
}
