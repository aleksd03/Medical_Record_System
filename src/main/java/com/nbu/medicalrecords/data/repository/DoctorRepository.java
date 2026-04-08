package com.nbu.medicalrecords.data.repository;

import com.nbu.medicalrecords.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
