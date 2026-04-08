package com.nbu.medicalrecords.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "diagnoses")
@Getter
@Setter
public class Diagnosis extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    private String description;
}
