package com.nbu.medicalrecords.integration;

import com.nbu.medicalrecords.data.entity.Doctor;
import com.nbu.medicalrecords.data.repository.DoctorRepository;
import com.nbu.medicalrecords.service.DoctorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test", "test-local"})
public class DoctorIntegrationTest {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @AfterEach
    void tearDown() {
        doctorRepository.deleteAll();
    }

    @Test
    void createAndRetrieveDoctor() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("Ivan");
        doctor.setLastName("Kolev");
        doctor.setIdentificationNumber("IK999");
        doctor.setSpecialty("Cardiology");
        doctor.setGp(false);

        Doctor saved = doctorService.createDoctor(doctor);

        assertNotNull(saved.getId());
        assertEquals("Ivan", saved.getFirstName());

        Doctor found = doctorService.getDoctorById(saved.getId());
        assertEquals("IK999", found.getIdentificationNumber());
    }

    @Test
    void getAllDoctors_ShouldReturnAll() {
        Doctor doctor1 = new Doctor();
        doctor1.setFirstName("Ivan");
        doctor1.setLastName("Kolev");
        doctor1.setIdentificationNumber("IK001");
        doctor1.setSpecialty("Cardiology");
        doctor1.setGp(false);

        Doctor doctor2 = new Doctor();
        doctor2.setFirstName("Maria");
        doctor2.setLastName("Petrova");
        doctor2.setIdentificationNumber("MP001");
        doctor2.setSpecialty("Neurology");
        doctor2.setGp(true);

        doctorService.createDoctor(doctor1);
        doctorService.createDoctor(doctor2);

        assertEquals(2, doctorService.getAllDoctors().size());
    }

    @Test
    void deleteDoctor_ShouldRemoveFromDatabase() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("Ivan");
        doctor.setLastName("Kolev");
        doctor.setIdentificationNumber("IK002");
        doctor.setSpecialty("Cardiology");
        doctor.setGp(false);

        Doctor saved = doctorService.createDoctor(doctor);
        doctorService.deleteDoctor(saved.getId());

        assertEquals(0, doctorRepository.count());
    }
}
