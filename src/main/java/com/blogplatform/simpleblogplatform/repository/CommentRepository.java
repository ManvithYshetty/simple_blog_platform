package com.blogplatform.simpleblogplatform.repository;

import com.blogplatform.simpleblogplatform.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The CommentRepository interface is the data access layer for Comment entities.
 * By extending JpaRepository, it inherits a full suite of CRUD (Create, Read,
 * Update, Delete) and pagination methods for the Comment entity without any
 * implementation code from our side.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // At this stage, we don't need any custom query methods.
    // The inherited methods like save(), findById(), and deleteById() are all
    // we need to implement the initial comment functionality.
    // Spring Data JPA provides the implementation for us automatically.
}