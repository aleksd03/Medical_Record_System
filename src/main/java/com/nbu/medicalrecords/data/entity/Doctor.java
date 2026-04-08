package com.nbu.medicalrecords.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctors")
@Getter
@Setter
public class Doctor extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String identificationNumber;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
}
