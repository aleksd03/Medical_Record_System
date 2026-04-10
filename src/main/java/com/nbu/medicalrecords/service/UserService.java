package com.nbu.medicalrecords.service;

import com.nbu.medicalrecords.data.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getCurrentUser();
}
