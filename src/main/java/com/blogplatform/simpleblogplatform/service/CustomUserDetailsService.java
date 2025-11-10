package com.blogplatform.simpleblogplatform.service;

import com.blogplatform.simpleblogplatform.model.User;
import com.blogplatform.simpleblogplatform.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * This service is responsible for loading user-specific data.
 * It is used by the Spring Security framework for authentication and authorization.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Step 1: Fetch our custom User entity from the database. This remains the same.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + username));

        // --- THIS IS THE LINE TO UPDATE ---
        // OLD (example): List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // NEW: Dynamically create the GrantedAuthority from the user's role field.
        // We fetch the role string from our User entity, convert it to uppercase,
        // and prefix it with "ROLE_" to adhere to Spring Security conventions.
        // This makes our authorization mechanism dynamic and data-driven.
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));

        // Step 3: Create and return a Spring Security UserDetails object. This remains the same.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }
}