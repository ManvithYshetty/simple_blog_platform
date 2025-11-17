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
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            .authorizeHttpRequests(auth -> auth

                // MUST BE FIRST
                .requestMatchers("/h2-console/**").permitAll()

                // Public static files
                .requestMatchers("/css/**", "/js/**").permitAll()

                // Public pages
                .requestMatchers(HttpMethod.GET, "/", "/posts", "/posts/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/register", "/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()

                // Admin routes
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // Comment submission
                .requestMatchers(HttpMethod.POST, "/posts/*/comments")
                    .hasAnyRole("USER", "ADMIN")

                // ANYTHING ELSE must be after all matchers
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}