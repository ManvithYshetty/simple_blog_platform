package com.blogplatform.simpleblogplatform.repository;

import com.blogplatform.simpleblogplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The UserRepository interface is the data access layer for User entities.
 * It extends JpaRepository to get standard CRUD operations for free.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username. This is a custom query method that Spring Data JPA
     * will implement for us automatically based on the method name.
     * The name "findByUsername" tells JPA to generate a query that looks for a User
     * entity where the 'username' field matches the provided parameter.
     *
     * @param username The username to search for.
     * @return An Optional<User> which will contain the User if found, or be empty otherwise.
     *         Using Optional is a best practice to avoid NullPointerExceptions.
     */
    Optional<User> findByUsername(String username);
}