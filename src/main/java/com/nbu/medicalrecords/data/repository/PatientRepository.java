package com.nbu.medicalrecords.data.repository;

import com.nbu.medicalrecords.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
