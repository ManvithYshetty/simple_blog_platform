package com.blogplatform.simpleblogplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

                // H2 console
                .requestMatchers("/h2-console/**").permitAll()

                // Static files
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                // Public pages
                .requestMatchers("/", "/posts", "/posts/**").permitAll()
                .requestMatchers("/login", "/register").permitAll()

                // Comments: only logged in users
                .requestMatchers(HttpMethod.POST, "/posts/*/comments")
                    .authenticated()

                // Admin only
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // Everything else is PUBLIC (important!)
                .anyRequest().permitAll()
            )

            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}
