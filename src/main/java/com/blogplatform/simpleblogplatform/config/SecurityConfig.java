package com.blogplatform.simpleblogplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // NEW: Import HttpMethod for specifying request types
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Rule 1: Secure the Admin Dashboard (from the previous task)
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // --- NEW RULE ---
                // Rule 2: Secure the action of submitting a new comment.
                // We use HttpMethod.POST to specify that this rule only applies to form submissions.
                // The path "/posts/*/comments" uses a wildcard (*) to match any post ID.
                // We use hasAnyRole to allow both standard USERS and ADMINS to comment.
                .requestMatchers(HttpMethod.POST, "/posts/*/comments").hasAnyRole("USER", "ADMIN")

                // --- REFINED PUBLIC ACCESS RULE ---
                // Rule 3: Refine Public Access. We explicitly state that only GET requests are public.
                // This prevents users from trying to POST, PUT, or DELETE to these URLs without being authenticated.
                .requestMatchers(HttpMethod.GET, "/", "/posts", "/posts/**").permitAll()

                // Other public pages and resources
                .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
                
                // Rule 4: The Catch-All Rule
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()
                .logoutSuccessUrl("/")
            );

        return http.build();
    }
}