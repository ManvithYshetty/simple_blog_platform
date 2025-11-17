package com.blogplatform.simpleblogplatform.service;

import com.blogplatform.simpleblogplatform.model.User;
import com.blogplatform.simpleblogplatform.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserService handles the business logic for user-related operations,
 * such as registration. It is distinct from CustomUserDetailsService, which is
 * specifically for Spring Security's authentication process.
 */
@Service
public class UserService {
  
    // Dependency on UserRepository for database operations.
    private final UserRepository userRepository;
    
    // Dependency on PasswordEncoder for hashing passwords.
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs the UserService with its required dependencies.
     * Spring will inject the beans for UserRepository and PasswordEncoder here.
     *
     * @param userRepository  The repository for user data access.
     * @param passwordEncoder The encoder for hashing passwords.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user in the system.
     *
     * @param user The User object containing the new user's details (e.g., username and plain-text password).
     * @return The saved User entity.
     * @throws IllegalStateException if a user with the same username already exists.
     */
    public User registerUser(User user) {
        // Business Rule 1: Check if a user with this username already exists.
        // It's crucial to prevent duplicate usernames.
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already exists: " + user.getUsername());
        }

        // Security Rule: Hash the user's plain-text password before saving.
        // We retrieve the plain-text password, encode it, and then set it back on the user object.
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // Business Rule 2: Assign a default role to the new user.
        // All registered users will start with the "USER" role.
        user.setRole("USER");

        // Persist the new user to the database.
        // The save method will return the persisted entity, now with an ID.
        return userRepository.save(user);
    }
}