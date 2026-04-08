package com.nbu.medicalrecords.data.repository;

import com.nbu.medicalrecords.data.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
