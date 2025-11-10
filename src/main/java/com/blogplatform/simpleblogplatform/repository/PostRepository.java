package com.blogplatform.simpleblogplatform.repository;

import com.blogplatform.simpleblogplatform.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The PostRepository interface provides a way to perform CRUD operations on Post entities.
 * By extending JpaRepository, we get a lot of standard data access methods for free,
 * without needing to write any implementation code.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // That's it!
    // Spring Data JPA will automatically provide an implementation of this interface at runtime.
    // You can add custom query methods here later if needed.
}
