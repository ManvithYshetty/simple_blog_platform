package com.blogplatform.simpleblogplatform.config;

import com.blogplatform.simpleblogplatform.model.User;
import com.blogplatform.simpleblogplatform.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * DataInitializer is a Spring component that runs at application startup
 * to ensure that essential initial data, like a default admin user,
 * exists in the database.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    // Inject the UserRepository and PasswordEncoder to interact with user data
    // and hash passwords, respectively.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs the DataInitializer with its required dependencies using
     * constructor injection.
     *
     * @param userRepository  The repository for user data access.
     * @param passwordEncoder The encoder for hashing passwords.
     */
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method is executed automatically by Spring Boot after the application
     * context is loaded.
     *
     * @param args incoming command line arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        // Check if an admin user already exists to avoid creating duplicates
        // on every application restart.
        if (!userRepository.findByUsername("admin").isPresent()) {
            
            // If no admin user is found, create a new one.
            User adminUser = new User();
            adminUser.setUsername("admin");
            
            // IMPORTANT: Never store plain-text passwords. Always encode them.
            // We are using the PasswordEncoder bean that is already configured.
            adminUser.setPassword(passwordEncoder.encode("password"));
            
            // Set the role to "ADMIN". This is the crucial part that grants
            // administrative privileges.
            adminUser.setRole("ADMIN");
            
            // Save the new admin user to the database.
            userRepository.save(adminUser);
            
            System.out.println("Created default admin user with username 'admin' and password 'password'");
        }
    }
}