package com.nbu.medicalrecords.data.repository;

import com.nbu.medicalrecords.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
