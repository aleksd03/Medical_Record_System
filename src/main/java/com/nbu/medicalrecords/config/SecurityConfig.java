package com.nbu.medicalrecords.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        OidcUserService delegate = new OidcUserService();
        return userRequest -> {
            OidcUser oidcUser = delegate.loadUser(userRequest);
            Map<String, Object> claims = oidcUser.getClaims();
            Collection<GrantedAuthority> authorities = extractRoles(claims);
            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        };
    }

    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> extractRoles(Map<String, Object> claims) {
        Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");
        if (realmAccess == null) return List.of();
        List<String> roles = (List<String>) realmAccess.get("roles");
        if (roles == null) return List.of();
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter jwtAuthenticationConverter() {
        org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter jwtConverter =
                new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess == null) return List.of();
            List<String> roles = (List<String>) realmAccess.get("roles");
            if (roles == null) return List.of();
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });
        return jwtConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index").permitAll()
                        .requestMatchers("/api/appointments/my-appointments").hasAuthority("PATIENT")
                        .requestMatchers("/appointments/my-appointments").hasAuthority("PATIENT")
                        .requestMatchers(HttpMethod.POST, "/api/doctors/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/doctors/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/doctors/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.POST, "/api/patients/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/patients/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/patients/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.POST, "/api/diagnoses/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/diagnoses/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/diagnoses/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.POST, "/api/sick-leaves/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/sick-leaves/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/sick-leaves/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/appointments/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/appointments/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .requestMatchers("/api/**").hasAnyAuthority("ADMIN", "DOCTOR", "PATIENT")
                        .requestMatchers("/doctors/**", "/patients/**", "/diagnoses/**",
                                "/appointments/**", "/sick-leaves/**").hasAnyAuthority("ADMIN", "DOCTOR")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserService())
                        )
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
