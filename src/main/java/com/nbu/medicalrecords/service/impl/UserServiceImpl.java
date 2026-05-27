package com.nbu.medicalrecords.service.impl;

import com.nbu.medicalrecords.data.entity.User;
import com.nbu.medicalrecords.data.repository.UserRepository;
import com.nbu.medicalrecords.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof org.springframework.security.oauth2.core.oidc.user.OidcUser) {
            username = ((org.springframework.security.oauth2.core.oidc.user.OidcUser) principal).getPreferredUsername();
        } else {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        return (User) loadUserByUsername(username);
    }
}
