package com.nbu.medicalrecords.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "sick_leaves")
@Getter
@Setter
public class SickLeave extends BaseEntity {
    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private int numberOfDays;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;
}
