package com.nbu.medicalrecords.data.repository;

import com.nbu.medicalrecords.data.entity.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SickLeaveRepository extends JpaRepository<SickLeave, Long> {
}
