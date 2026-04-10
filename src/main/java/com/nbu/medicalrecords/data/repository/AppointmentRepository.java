package com.nbu.medicalrecords.data.repository;

import com.nbu.medicalrecords.data.entity.Appointment;
import com.nbu.medicalrecords.data.entity.Doctor;
import com.nbu.medicalrecords.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(Patient patient);

    List<Appointment> findByDoctor(Doctor doctor);

    List<Appointment> findByDateBetween(LocalDate start, LocalDate end);

    List<Appointment> findByDoctorAndDateBetween(Doctor doctor, LocalDate start, LocalDate end);

    @Query("SELECT a.doctor, COUNT(a) FROM Appointment a GROUP BY a.doctor")
    List<Object[]> countAppointmentsByDoctor();

    @Query("SELECT SUM(a.price) FROM Appointment a WHERE a.patient.healthInsured = false")
    BigDecimal getTotalPriceForUninsuredPatients();

    @Query("SELECT a.doctor, SUM(a.price) FROM Appointment a WHERE a.patient.healthInsured = false GROUP BY a.doctor")
    List<Object[]> getTotalPriceByDoctorForUninsuredPatients();
}
