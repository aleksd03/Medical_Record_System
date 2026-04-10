package com.nbu.medicalrecords.data.repository;

import com.nbu.medicalrecords.data.entity.Doctor;
import com.nbu.medicalrecords.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByGeneralPractitioner(Doctor doctor);

    @Query("SELECT p.generalPractitioner, COUNT(p) FROM Patient p GROUP BY p.generalPractitioner")
    List<Object[]> countPatientsPerGeneralPractitioner();
}
