package com.nbu.medicalrecords.data.repository;

import com.nbu.medicalrecords.data.entity.Diagnosis;
import com.nbu.medicalrecords.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
    @Query("SELECT a.diagnosis, COUNT(a) FROM Appointment a GROUP BY a.diagnosis ORDER BY COUNT(a) DESC")
    List<Object[]> findMostCommonDiagnosis();

    @Query("SELECT DISTINCT a.patient FROM Appointment a WHERE a.diagnosis.id = :diagnosisId")
    List<Patient> findPatientsByDiagnosisId(@Param("diagnosisId") Long diagnosisId);
}
