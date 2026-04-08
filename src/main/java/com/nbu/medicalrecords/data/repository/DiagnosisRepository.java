package com.nbu.medicalrecords.data.repository;

import com.nbu.medicalrecords.data.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
}
