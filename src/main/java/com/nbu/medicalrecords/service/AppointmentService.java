package com.nbu.medicalrecords.service;

import com.nbu.medicalrecords.data.entity.Appointment;
import com.nbu.medicalrecords.data.entity.Doctor;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(Long id);
    Appointment createAppointment(Appointment appointment);
    Appointment updateAppointment(Long id, Appointment appointment);
    void deleteAppointment(Long id);
    List<Appointment> getAppointmentsByDoctor(Doctor doctor);
}
